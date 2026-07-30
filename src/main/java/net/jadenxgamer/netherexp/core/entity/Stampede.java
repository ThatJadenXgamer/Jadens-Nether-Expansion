package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.AdvancementGranter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance.ScreenshakePositionData;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class Stampede extends PossessedMob implements NeutralMob, Saddleable, PlayerRideableJumping {

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState grinAnimation = new AnimationState();
    public final AnimationState chewAnimation = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int angerTime = 0;
    @Nullable private UUID angryAt = null;
    public float playerJumpPendingScale = 0.0f;
    public boolean isStampedeJumping = false;

    private static final EntityDataAccessor<Boolean> SADDLED = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> EATING_TIME = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_PATRICK = SynchedEntityData.defineId(Stampede.class, EntityDataSerializers.BOOLEAN);
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);

    private static final Predicate<ItemEntity> PREDICATE_DROP_FILTER = item -> !item.hasPickUpDelay() && item.isAlive() && item.getItem().is(JNETags.Items.STAMPEDE_EDIBLE);

    public Stampede(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level, NetherExp.minecraftPath("strider"));
        this.setPathfindingMalus(PathType.WATER, -1.0f);
        this.setPathfindingMalus(PathType.LAVA, 8.0f);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0f);
        this.setCanPickUpLoot(true);
        this.xpReward = 28;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.2, 32.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new PickupItemGoal());
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) this.setupAnimationStates();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isMoving()) this.trampleEntities();
        if (this.level().isClientSide()) return;

        this.setAngry(this.getTarget() != null || this.getHunger() <= 0);
        this.handleEating();
    }

    private void handleEating() {
        ItemStack itemInMouth = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemInMouth.isEmpty()) return;
        if (this.getEatingTime() == 0) {
            float healAmount = 10.0f;
            int hungerSatiation = 3;
            if (itemInMouth.is(JNETags.Items.STAMPEDE_FAVORITES)) {
                var particle = ParticleTypes.LARGE_SMOKE;
                if (this.random.nextInt(10) == 0) {
                    this.setTamed();
                    AdvancementGranter.grantPlayersInRadius(level(), blockPosition(), 8.0, JNECriteriaTriggers.TAME_STAMPEDE);
                    particle = ParticleTypes.HEART;
                    healAmount = 20.0f;
                    hungerSatiation = 5;
                }
                for (int i = 0; i < 11; i++) ((ServerLevel) this.level()).sendParticles(particle,
                        this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5),
                        1, 0.0, 0.0, 0.0, 0.0);
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            if (this.getHealth() < this.getMaxHealth()) this.heal(healAmount);
            this.setHunger(this.getHunger() + hungerSatiation);
            if (this.getHunger() > 5) {
                this.stopBeingAngry();
                this.setLastHurtByPlayer(null);
                this.setAngry(false);
            }

        } else this.setEatingTime(this.getEatingTime() - 1);
    }

    private void trampleEntities() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL) return;

        var boundingBox = this.getBoundingBox();
        var height = boundingBox.getYsize();
        this.level().getEntities(this, boundingBox, EntitySelector.NO_CREATIVE_OR_SPECTATOR)
                .stream()
                .filter(LivingEntity.class::isInstance)
                .map(LivingEntity.class::cast)
                .filter(victim -> victim != this && !this.getPassengers().contains(victim))
                .forEach(victim -> {
                    if (victim.invulnerableTime > 5) {
                        return;
                    }
                    if (victim.getBoundingBox().getYsize() < height) {
                        Vec3 vel = this.getDeltaMovement();
                        double speed = Math.sqrt(vel.x * vel.x + vel.z * vel.z);
                        float damage = (float) (this.getAttributeValue(Attributes.ATTACK_DAMAGE) * (0.5 + speed * 2.0));
                        victim.hurt(this.damageSources().mobAttack(this), damage);
                        if (speed > 0.1) {
                            double knockback = this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                            victim.push(vel.x * knockback, 0.25, vel.z * knockback);
                        }
                        this.level().playSound(null, this.blockPosition(), SoundEvents.RAVAGER_STEP, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
                    }
                });
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.level().isClientSide() && hand.equals(InteractionHand.MAIN_HAND)) {
            if (stack.is(JNETags.Items.STAMPEDE_EDIBLE) && this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, stack.copyWithCount(1));
                stack.shrink(1);
                this.setEatingTime(60);
                return InteractionResult.SUCCESS;
            } else if (this.isSaddled()) {
                if (stack.canPerformAction(ItemAbilities.SHEARS_REMOVE_ARMOR)) {
                    this.setSaddled(false);
                    this.level().playSound(null, player.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0f, 1.0f);
                    if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
                    this.spawnAtLocation(Items.SADDLE);
                    return InteractionResult.SUCCESS;
                } else if (!this.isVehicle() && !player.isSecondaryUseActive()) {
                    player.startRiding(this);
                    return InteractionResult.SUCCESS;
                } else return super.mobInteract(player, hand);
            } else if (this.isSaddleable() && !this.isSaddled() && stack.is(Items.SADDLE)) {
                this.equipSaddle(stack.split(1), SoundSource.NEUTRAL);
                this.level().gameEvent(this, GameEvent.EQUIP, this.position());
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTick) {
        float f = this.walkAnimation.position();
        float g = 0.12f * Mth.cos(f * 1.5f) * 2.0f * Math.min(0.25f, this.walkAnimation.speed());
        return super.getPassengerAttachmentPoint(entity, dimensions, partialTick).add(0.0, (g * partialTick), 0.0);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (this.random.nextDouble() < JNEConfigs.STAMPEDE_STRIDITE_SHEDDING_CHANCE.get()) this.dropStridite();
        return super.doHurtTarget(target);
    }

    private void dropStridite() {
        int count = Mth.randomBetweenInclusive(this.random, JNEConfigs.MIN_STAMPEDE_STRIDITE_DROPS.get(), JNEConfigs.MAX_STAMPEDE_STRIDITE_DROPS.get());
        this.spawnAtLocation(new ItemStack(JNEItems.STRIDITE.get(), count));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isTamed();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isTamed() && !this.hasCustomName();
    }

    @Override
    public boolean canTakeItem(ItemStack stack) {
        return this.getMainHandItem().isEmpty() && stack.is(JNETags.Items.STAMPEDE_EDIBLE);
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        return this.getMainHandItem().isEmpty();
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        return stack.is(JNETags.Items.STAMPEDE_EDIBLE);
    }

    @Override
    public void onItemPickup(ItemEntity itemEntity) {
        this.setEatingTime(60);
    }

    @Override
    public void dropEquipment() {
        super.dropEquipment();
        if (this.isSaddled()) {
            this.spawnAtLocation(Items.SADDLE);
        }
    }

    // RIDING //

    @Override
    public void onPlayerJump(int jumpPower) {
        if (this.isSaddled()) {
            int p = Math.max(0, jumpPower);
            this.playerJumpPendingScale = (p >= 90) ? 1.0f : 0.4f + 0.4f * (float) p / 90.0f;
        }
    }

    @Override
    public void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        Vec2 vec2 = this.getRiddenRotation(player);
        this.setRot(vec2.y, vec2.x);
        this.yHeadRot = this.getYRot();
        this.yBodyRot = this.yHeadRot;
        this.yRotO = this.yBodyRot;
        if (this.isControlledByLocalInstance() && this.onGround()) {
            this.isStampedeJumping = false;
            if (this.playerJumpPendingScale > 0.0f) this.executeRidersJump(this.playerJumpPendingScale, travelVector);
            this.playerJumpPendingScale = 0.0f;
        }
    }

    @Override
    public Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        float x = player.xxa * 0.5f;
        float z = player.zza;
        if (z <= 0.0f) {
            z *= 0.25f;
        }
        return new Vec3(x, 0.0, z);
    }

    public void executeRidersJump(float playerJumpPendingScale, Vec3 travelVector) {
        double jumpPower = this.getJumpPower(playerJumpPendingScale);
        Vec3 vel = this.getDeltaMovement();
        this.setDeltaMovement(vel.x, jumpPower, vel.z);
        this.isStampedeJumping = true;
        this.hasImpulse = true;
        CommonHooks.onLivingJump(this);
        if (travelVector.z > 0.0) {
            float f = Mth.sin(this.getYRot() * 0.017453292f);
            double scale = -0.4f * f * playerJumpPendingScale;
            this.setDeltaMovement(this.getDeltaMovement().add(scale, 0.0, scale));
        }
    }

    // DATA //

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SADDLED, false);
        builder.define(TAMED, false);
        builder.define(ANGRY, false);
        builder.define(EATING_TIME, 0);
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
        this.entityData.set(ANGRY, nbt.getBoolean("Angry"));
        this.entityData.set(IS_PATRICK, nbt.getBoolean("IsPatrick"));
        this.entityData.set(HUNGER, nbt.getInt("Hunger"));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void doPush(Entity entity) {}

    public boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.02f;
    }

    @Override
    public boolean isSaddled() {
        return this.entityData.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(SADDLED, saddled);
    }

    public Vec2 getRiddenRotation(LivingEntity entity) {
        return new Vec2(entity.getXRot() * 0.5f, entity.getYRot());
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && this.isTamed();
    }

    @Override
    public void equipSaddle(ItemStack stack, SoundSource soundSource) {
        this.setSaddled(true);
    }

    @Override
    public boolean canJump() {
        return this.isSaddled();
    }

    @Override
    public void handleStartJump(int jumpPower) {
        this.playJumpSound();
    }

    @Override
    public void handleStopJump() {}

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
    public int apparitionPersonality() {
        return 2;
    }

    @Override
    public double apparitionUnleashingOdds() {
        return JNEConfigs.STAMPEDE_UNLEASHING_ODDS.get();
    }

    @Override
    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    private void setAngry(boolean angry) {
        if (angry && !this.entityData.get(ANGRY)) this.playSound(JNESoundEvents.STAMPEDE_AGITATED.get(), 2.0f, 1.0f);
        this.entityData.set(ANGRY, angry);
    }

    private boolean isTamed() {
        return this.entityData.get(TAMED);
    }

    private void setTamed() {
        this.entityData.set(TAMED, true);
    }

    private int getHunger() {
        return this.entityData.get(HUNGER);
    }

    private void setHunger(int hunger) {
        this.entityData.set(HUNGER, Math.clamp(hunger, 0, 20));
    }

    private int getEatingTime() {
        return this.entityData.get(EATING_TIME);
    }

    private void setEatingTime(int eatingTime) {
        this.entityData.set(EATING_TIME, Math.max(0, eatingTime));
    }

    // ANIMATIONS //

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimation.startIfStopped(this.tickCount);
            this.idleAnimationTimeout = 40;
        } else this.idleAnimationTimeout--;

        if (this.getEatingTime() > 0) {
            this.chewAnimation.startIfStopped(this.tickCount);
            this.playEatingAnimation();
        } else this.chewAnimation.stop();

        if (this.isAngry() && this.getEatingTime() <= 0) this.grinAnimation.startIfStopped(this.tickCount);
        else this.grinAnimation.stop();
    }

    private void playEatingAnimation() {
        if (this.getEatingTime() % 5 != 0) return;
        var random = this.random;
        Player player = Minecraft.getInstance().player;
        if (player != null) this.level().playLocalSound(player, JNESoundEvents.STAMPEDE_EAT.get(), this.getSoundSource(),
                0.5f + 0.5f * random.nextInt(2), (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);

        float degToRad = 0.017453292f;
        float pitchRad = -this.getXRot() * degToRad;
        float yawRad = -this.getYRot() * degToRad;
        float bodyYawRad = -this.yBodyRot * degToRad;
        Vec3 basePos = new Vec3(this.getX(), this.getEyeY() + 1.0, this.getZ());

        for (int i = 0; i < 6; i++) {
            Vec3 velocity = new Vec3((random.nextDouble() - 0.5) * 0.1, random.nextDouble() * 0.1 + 0.1, (random.nextDouble() - 0.5) * 0.1).xRot(pitchRad).yRot(yawRad);
            Vec3 positionOffset = new Vec3((random.nextDouble() - 0.5) * 0.8, -random.nextDouble() * 0.6 - 0.3, 1.0 + (random.nextDouble() - 0.5) * 0.4).yRot(bodyYawRad);
            Vec3 spawnPos = basePos.add(positionOffset);

            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, Items.BONE_BLOCK.getDefaultInstance()), spawnPos.x, spawnPos.y, spawnPos.z, velocity.x, velocity.y + 0.05, velocity.z);
        }
    }

    @Override
    public void updateWalkAnimation(float partialTick) {
        this.walkAnimation.update(this.getPose() == Pose.STANDING ? Math.min(partialTick * 6.0f, 1.0f) : 0.0f, 0.2f);
    }

    // SOUNDS //

    @Override
    public SoundEvent getAmbientSound() {
        return JNESoundEvents.STAMPEDE_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return JNESoundEvents.STAMPEDE_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return JNESoundEvents.STAMPEDE_DEATH.get();
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(JNESoundEvents.STAMPEDE_STEP.get(), 0.85f, Mth.randomBetween(this.level().random, 1.0f, 2.0f));
        ScreenshakeHandler.addScreenshake(
                new ScreenshakeInstance(10, 0.5f, 0f, 0f,
                        Easing.LINEAR, Easing.LINEAR, 1.0f,
                        Optional.of(new ScreenshakePositionData(this.position(), 16.0f, Easing.LINEAR))
                )
        );
    }

    public void playJumpSound() {
        this.playSound(SoundEvents.HORSE_JUMP, 0.4f, 1.0f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.STEP_HEIGHT, 2.5);
    }

    // AI //

    public class PickupItemGoal extends Goal {

        @Nullable
        private ItemEntity targetItem = null;

        public PickupItemGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.nearbyGroundItems().isEmpty() &&
                    !Stampede.this.isVehicle() &&
                    Stampede.this.getTarget() == null &&
                    Stampede.this.random.nextInt(10) == 0 &&
                    Stampede.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
        }

        @Override
        public void start() {
            this.targetItem = this.nearbyGroundItems().getFirst();
        }

        @Override
        public boolean canContinueToUse() {
            return this.targetItem != null && this.targetItem.isAlive() && super.canContinueToUse();
        }

        @Override
        public void tick() {
            if (this.targetItem == null) return;
            Stampede.this.getNavigation().moveTo(this.targetItem, 1.2000000476837158);
        }

        private java.util.List<ItemEntity> nearbyGroundItems() {
            return Stampede.this.level().getEntitiesOfClass(ItemEntity.class, Stampede.this.getBoundingBox().inflate(8.0, 8.0, 8.0), PREDICATE_DROP_FILTER);
        }
    }
}