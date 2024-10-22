package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Stampede extends Monster implements NeutralMob, ItemSteerable, Saddleable, PlayerRideable {
    private static final Ingredient FOOD_ITEMS = Ingredient.of(JNETags.Items.STAMPEDE_EDIBLE);
    static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (item) -> !item.hasPickUpDelay() && item.isAlive() && item.getItem().is(JNETags.Items.STAMPEDE_EDIBLE);

    private static final EntityDataAccessor<Integer> BOOST_TIME = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SADDLE_ID = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);

    public final AnimationState chewAnimationState = new AnimationState();
    public final AnimationState grinAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private int eating = 0;
    private int angerTime;
    private UUID angryAt;
    private final ItemBasedSteering steering;

    public Stampede(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.5f);
        this.blocksBuilding = true;
        this.steering = new ItemBasedSteering(this.entityData, BOOST_TIME, SADDLE_ID);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.setCanPickUpLoot(true);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        if (this.getAngry()) {
            grinAnimationState.startIfStopped(this.tickCount);
        } else {
            grinAnimationState.stop();
        }
        if (this.getEating()) {
            chewAnimationState.startIfStopped(this.tickCount);
        } else {
            chewAnimationState.stop();
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.33)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            this.spawnAtLocation(Items.SADDLE);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.2, 32.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new Stampede.PickupItemGoal());
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WitherSkeleton.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Stray.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Vessel.class, true));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    public void aiStep() {
        if (this.isAlive() && this.isEffectiveAi()) {
            ItemStack item = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!item.isEmpty()) {
                ++this.eating;
                this.playEatingAnimation();
                this.setAngry(true);
                this.setEating(true);
                if (this.eating > 100) {
                    if (item.is(JNETags.Items.STAMPEDE_FAVORITES)) {
                        if (random.nextInt(12) == 0) {
                            this.setIsTamed(true);
                            for(int i = 0; i < 12; ++i) {
                                this.level().addParticle(ParticleTypes.HEART, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 0.0, 0.0, 0.0);
                            }
                        } else {
                            for(int i = 0; i < 12; ++i) {
                                this.level().addParticle(ParticleTypes.SMOKE, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 0.0, 0.0, 0.0);
                            }
                        }
                    }
                    this.setItemSlot(EquipmentSlot.MAINHAND, Items.AIR.getDefaultInstance());
                    this.heal(15);
                    this.eating = 0;
                    this.setAngry(false);
                    this.setEating(false);
                }
            }
            else this.setAngry(this.getTarget() != null);
        }
        if (this.level().getDifficulty() != Difficulty.PEACEFUL && isMoving()) {
            damageLivingEntities(this.level().getEntities(this, this.getBoundingBox(), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
        }

        super.aiStep();
    }

    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isSaddled() && stack.isEmpty() && player.isShiftKeyDown()) {
            this.steering.setSaddle(false);
            player.level().playSound(null, player.getOnPos(), SoundEvents.STRIDER_SADDLE, SoundSource.PLAYERS, 1.0f, 1.0f);
            player.setItemInHand(hand, Items.SADDLE.getDefaultInstance());
            return InteractionResult.SUCCESS;
        }
        else if (this.isSaddled() && !this.isVehicle() && !player.isSecondaryUseActive()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else if (this.getIsTamed() && stack.is(Items.SADDLE)) {
            this.steering.setSaddle(true);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private void playEatingAnimation() {
        if (this.eating % 5 == 0) {
            this.playSound(SoundEvents.GENERIC_EAT, 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            for (int i = 0; i < 6; ++i) {
                Vec3 vec3 = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double) this.random.nextFloat() - 0.5) * 0.1);
                vec3 = vec3.xRot(-this.getXRot() * 0.017453292F);
                vec3 = vec3.yRot(-this.getYRot() * 0.017453292F);
                double d = (double) (-this.random.nextFloat()) * 0.6 - 0.3;
                Vec3 vec32 = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.8, d, 1.0 + ((double) this.random.nextFloat() - 0.5) * 0.4);
                vec32 = vec32.yRot(-this.yBodyRot * 0.017453292F);
                vec32 = vec32.add(this.getX(), this.getEyeY() + 1.0, this.getZ());
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, Items.BONE.getDefaultInstance()), vec32.x, vec32.y, vec32.z, vec3.x, vec3.y + 0.05, vec3.z);
            }
        }
    }

    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && !livingEntity.isPassenger()) {
                entity.hurt(this.damageSources().source(JNEDamageSources.STAMPEDE_CRUSH, this), 10);
            }
        }
    }

    public double getPassengersRidingOffset() {
        float f = Math.min(0.25F, this.walkAnimation.speed());
        float g = this.walkAnimation.position();
        return (double)this.getBbHeight() - 0.19 + (double)(0.12F * Mth.cos(g * 1.5F) * 2.0F * f);
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        ItemStack holdingStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return holdingStack.isEmpty() && stack.is(JNETags.Items.STAMPEDE_EDIBLE);
    }

    @Override
    public void setLastHurtMob(Entity target) {
        super.setLastHurtMob(target);
        int i = random.nextInt(5);
        if (i == 0) {
            this.dropStridite();
        }
    }

    private void dropStridite() {
        int a = random.nextInt(3) + 1;
        for (int d = 0; d < a; ++d) {
            this.spawnAtLocation(JNEItems.STRIDITE.get());
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ANGRY, false);
        this.getEntityData().define(EATING, false);
        this.entityData.define(BOOST_TIME, 0);
        this.entityData.define(SADDLE_ID, false);
        this.entityData.define(TAMED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Tamed", this.getIsTamed());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setIsTamed(nbt.getBoolean("Tamed"));
    }

    public boolean getIsTamed() {
        return this.entityData.get(TAMED);
    }

    public void setIsTamed(boolean flag) {
        this.entityData.set(TAMED, flag);
    }

    public boolean getAngry() {
        return getEntityData().get(ANGRY);
    }

    public void setAngry(boolean angry) {
        getEntityData().set(ANGRY, angry);
    }

    public boolean getEating() {
        return getEntityData().get(EATING);
    }

    public void setEating(boolean eating) {
        getEntityData().set(EATING, eating);
    }

    ////////
    // AI //
    ////////

    class PickupItemGoal extends Goal {
        public PickupItemGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!Stampede.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (Stampede.this.getTarget() == null) {
                if (Stampede.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = getItems();
                    return !list.isEmpty() && Stampede.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            List<ItemEntity> list = getItems();
            ItemStack itemStack = Stampede.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemStack.isEmpty() && !list.isEmpty()) {
                Stampede.this.getNavigation().moveTo(list.get(0), 1.2000000476837158);
            }
        }

        @Override
        public void start() {
            List<ItemEntity> list = getItems();
            if (!list.isEmpty()) {
                Stampede.this.getNavigation().moveTo(list.get(0), 1.2000000476837158);
            }
        }

        private List<ItemEntity> getItems() {
            return Stampede.this.level().getEntitiesOfClass(ItemEntity.class, Stampede.this.getBoundingBox().inflate(8.0, 8.0, 8.0), Stampede.PICKABLE_DROP_FILTER);
        }
    }

    ///////////
    // ANGER //
    ///////////

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

    ////////////
    // SADDLE //
    ////////////

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && this.getIsTamed();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        this.steering.setSaddle(true);
        if (soundSource != null) {
            this.level().playSound(null, this, SoundEvents.STRIDER_SADDLE, soundSource, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddled() {
        return this.steering.hasSaddle();
    }

    ////////////
    // SOUNDS //
    ////////////

    //TODO: all temporary sounds
    protected void playStepSound(BlockPos arg, BlockState arg2) {
        this.playSound(SoundEvents.STRIDER_STEP, 1.0F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.STRIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource arg) {
        return SoundEvents.STRIDER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.STRIDER_DEATH;
    }
}
