package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class Stampede extends PossessedMob implements NeutralMob, Saddleable, PlayerRideableJumping {
    private static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.hasPickUpDelay() && item.isAlive() && item.getItem().is(JNETags.Items.STAMPEDE_EDIBLE);
    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_PATRICK = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
    private int angerTime;
    protected float playerJumpPendingScale;
    private UUID angryAt;
    protected boolean isJumping;

    protected Stampede(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level, NetherExp.minecraftPath("strider"));
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        this.xpReward = 15;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isAlive() && this.isMoving()) {
            trampleEntities();
        }
    }

    private void trampleEntities() {
        AABB boundingBox = this.getBoundingBox();
        double height = boundingBox.getYsize();
        List<Entity> targetEntities = this.level().getEntities(this, boundingBox, EntitySelector.NO_CREATIVE_OR_SPECTATOR);

        for (Entity entity : targetEntities) {
            if (!(entity instanceof LivingEntity) || entity == this || this.getPassengers().contains(entity)) continue;
            LivingEntity target = (LivingEntity) entity;

            double targetHeight = target.getBoundingBox().getYsize();
            if (targetHeight < height) {
                Vec3 velocity = this.getDeltaMovement();
                double speed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
                float damage = (float) (this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (0.5 + speed * 2));
                target.hurt(this.damageSources().mobAttack(this), damage);
                if (speed > 0.1) {
                    double knockbackX = velocity.x * this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                    double knockbackZ = velocity.z * this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                    target.push(knockbackX, 0.3, knockbackZ);
                }

                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.RAVAGER_STEP, this.getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
            }
        }
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) this.spawnAtLocation(Items.SADDLE);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.level().isClientSide) {
            if (stack.is(JNETags.Items.STAMPEDE_EDIBLE) && this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, stack.copyWithCount(1));
                if (!player.getAbilities().instabuild) stack.shrink(1);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else if (this.isSaddled()) {
                if (stack.canPerformAction(ItemAbilities.SHEARS_REMOVE_ARMOR)) {
                    setSaddled(false);
                    player.level().playSound(null, player.getOnPos(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0f, 1.0f);
                    if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    this.spawnAtLocation(Items.SADDLE);
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                } else if (!this.isVehicle() && !player.isSecondaryUseActive()) {
                    player.startRiding(this);
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }
            } else if (isSaddleable() && !this.isSaddled() && stack.is(Items.SADDLE)) {
                this.equipSaddle(stack, SoundSource.NEUTRAL);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
        float f = Math.min(0.25F, this.walkAnimation.speed());
        float g = this.walkAnimation.position();
        float h = 0.12F * Mth.cos(g * 1.5F) * 2.0F * f;
        return super.getPassengerAttachmentPoint(entity, dimensions, partialTick).add(0.0, (h * partialTick), 0.0);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isTamed();
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !this.isTamed() && !this.hasCustomName();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (this.random.nextDouble() < STAMPEDE_STRIDITE_SHEDDING_CHANCE.get()) this.dropStridite();
        return super.doHurtTarget(target);
    }

    private void dropStridite() {
        int count = Mth.nextInt(random, MIN_STAMPEDE_STRIDITE_DROPS.get(), MAX_STAMPEDE_STRIDITE_DROPS.get());
        this.spawnAtLocation(new ItemStack(JNEItems.STRIDITE.get(), count));
    }

    //////////
    // DATA //
    //////////

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SADDLED, false);
        builder.define(TAMED, false);
        builder.define(ANGRY, false);
        builder.define(EATING, false);
        builder.define(IS_PATRICK, false);
        builder.define(HUNGER, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Saddled", this.entityData.get(SADDLED));
        nbt.putBoolean("Tamed", this.entityData.get(TAMED));
        nbt.putBoolean("Angry", this.entityData.get(ANGRY));
        nbt.putBoolean("IsPatrick", this.entityData.get(IS_PATRICK));
        nbt.putInt("Hunger", this.entityData.get(HUNGER));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(SADDLED, nbt.getBoolean("Saddled"));
        this.entityData.set(TAMED, nbt.getBoolean("Tamed"));
    }

    @Override
    protected double apparitionUnleashingOdds() {
        return STAMPEDE_UNLEASHING_ODDS.get();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    ////////////
    // RIDING //
    ////////////

    @Override
    public void onPlayerJump(int jumpPower) {
        if (!this.isSaddled()) return;
        if (jumpPower < 0) jumpPower = 0;
        if (jumpPower >= 90) {
            this.playerJumpPendingScale = 1.0F;
        } else this.playerJumpPendingScale = 0.4F + 0.4F * (float) jumpPower / 90.0F;
    }

    @Override
    protected void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        Vec2 vec2 = this.getRiddenRotation(player);
        this.setRot(vec2.y, vec2.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        if (this.isControlledByLocalInstance()) {

            if (this.onGround()) {
                this.setIsJumping(false);
                if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
                    this.executeRidersJump(this.playerJumpPendingScale, travelVector);
                }

                this.playerJumpPendingScale = 0.0F;
            }
        }
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        float x = player.xxa * 0.5F;
        float z = player.zza;
        if (z <= 0.0F) z *= 0.25F;

        return new Vec3(x, 0.0, z);
    }

    protected void executeRidersJump(float playerJumpPendingScale, Vec3 travelVector) {
        double jumpPower = this.getJumpPower(playerJumpPendingScale);
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, jumpPower, vec3.z);
        this.setIsJumping(true);
        this.hasImpulse = true;
        CommonHooks.onLivingJump(this);
        if (travelVector.z > 0.0) {
            float f = Mth.sin(this.getYRot() * 0.017453292F);
            float f1 = Mth.cos(this.getYRot() * 0.017453292F);
            this.setDeltaMovement(this.getDeltaMovement().add((-0.4F * f * playerJumpPendingScale), 0.0, (0.4F * f1 * playerJumpPendingScale)));
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.02F;
    }

    ///////////////////////
    // GETTERS & SETTERS //
    ///////////////////////

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.angryAt;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(this.random));
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && isTamed();
    }

    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    public boolean isTamed() {
        return this.entityData.get(TAMED);
    }

    private void setIsTamed(boolean flag) {
        this.entityData.set(TAMED, flag);
    }

    protected Vec2 getRiddenRotation(LivingEntity entity) {
        return new Vec2(entity.getXRot() * 0.5F, entity.getYRot());
    }

    @Override
    public boolean canJump() {
        return this.isSaddled();
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public void setIsJumping(boolean jumping) {
        this.isJumping = jumping;
    }

    @Override
    public void handleStartJump(int jumpPower) {
        this.playJumpSound();
    }

    @Override
    public void handleStopJump() {

    }

    @Override
    public void equipSaddle(ItemStack stack, @Nullable SoundSource soundSource) {
        this.setSaddled(true);
    }

    ////////////
    // SOUNDS //
    ////////////

    protected void playJumpSound() {
        this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
    }
}
