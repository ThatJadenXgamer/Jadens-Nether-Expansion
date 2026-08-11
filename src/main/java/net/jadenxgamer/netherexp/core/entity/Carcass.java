package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//todo 1.20.1 garbage
public class Carcass extends PathfinderMob {

    private static final EntityDataAccessor<Boolean> IS_REANIMATED = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> REANIMATION_COOLDOWN = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> IMMORTAL_COOLDOWN = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> REANIMATION_FLAG = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions DEACTIVE_DIMENSIONS = EntityDimensions.scalable(2.2F, 1.0F);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
    public final AnimationState reanimateAnimationState = new AnimationState();
    public final AnimationState deactivateAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private int deactivationAnimationTimer;
    private int reanimationAnimationTimer;

    public Carcass(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.refreshDimensions();
        this.reanimationAnimationTimer = 22;
        this.deactivationAnimationTimer = 25;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushEntities() {
        if (!isReanimated()) super.pushEntities();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getReanimationFlag()) {
            this.reanimateCarcass();
        }
        if (this.isReanimated() && this.getHealth() <= 0) {
            this.deactivateCarcass();
        }

        if (this.level().isClientSide()) {
            if (this.isReanimated()) {
                if (this.isMoving()) {
                    idleAnimationState.stop();
                    moveAnimationState.startIfStopped(this.tickCount);
                } else {
                    moveAnimationState.stop();
                    idleAnimationState.startIfStopped(this.tickCount);
                }
            } else if (!this.isReanimated() && !this.getReanimationFlag() && deactivationAnimationTimer == 25) {
                this.playSound(JNESoundEvents.ENTITY_CARCASS_DEATH.get(), 1.0f, 1.0f);
                deactivateAnimationState.startIfStopped(this.tickCount);
                idleAnimationState.stop();
                moveAnimationState.stop();
            }
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 0.21000000417232513)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_SPEED, 1.67);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CarcassMeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new CarcassMoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.addGoal(7, new CarcassLookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new CarcassRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new CarcassRandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false,
                (livingEntity) -> livingEntity instanceof Enemy && !(livingEntity instanceof Creeper) && !(livingEntity instanceof Stampede)));
    }

    @Override
    public boolean isDeadOrDying() {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isReanimated() ? super.getDefaultDimensions(pose) : DEACTIVE_DIMENSIONS;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (!this.isReanimated()) {
            if (damageSource.isCreativePlayer() || damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.discard();
                return true;
            } else if (damageSource.getDirectEntity() instanceof Projectile projectile && projectile.isOnFire()) {
                this.setReanimationFlag(true);
            } else {
                return false;
            }
        }
        return super.hurt(damageSource, f);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 40);
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 40) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.deactivateAnimationState.startIfStopped(this.tickCount);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    private void reanimateCarcass() {
        if (this.reanimationAnimationTimer == 22) {
            reanimateAnimationState.startIfStopped(this.tickCount);
            deactivateAnimationState.stop();
            this.playSound(JNESoundEvents.ENTITY_CARCASS_REANIMATE.get(), 1.0f, 1.0f);
        }
        if (this.reanimationAnimationTimer > 0) {
            --this.reanimationAnimationTimer;
        }
        if (this.reanimationAnimationTimer <= 0) {
            reanimateAnimationState.stop();
            this.setReanimated(true);
            this.setReanimationFlag(false);
            this.refreshDimensions();
            this.reanimationAnimationTimer = 22;
        }
    }

    private void deactivateCarcass() {
        idleAnimationState.stop();
        moveAnimationState.stop();
        reanimateAnimationState.stop();
        if (this.deactivationAnimationTimer == 25) {
            this.playSound(JNESoundEvents.ENTITY_CARCASS_DEATH.get(), 1.0f, 1.0f);
        }
        if (this.deactivationAnimationTimer > 0) {
            --this.deactivationAnimationTimer;
        }
        if (this.deactivationAnimationTimer <= 0) {
            this.setReanimated(false);
            this.setHealth(this.getMaxHealth());
            this.setReanimationCooldown(36000);
            this.deactivationAnimationTimer = 25;
            this.refreshDimensions();
            idleAnimationState.stop();
            moveAnimationState.stop();
        }
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.FLINT_AND_STEEL) && !this.isReanimated()) {
            this.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.setReanimationFlag(true);
            if (player instanceof ServerPlayer serverPlayer) {
                JNECriteriaTriggers.REVIVE_CARCASS.get().trigger(serverPlayer);
            }
            return InteractionResult.SUCCESS;
        } else if (stack.is(Items.FIRE_CHARGE) && !this.isReanimated()) {
            this.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.setReanimationFlag(true);
            if (player instanceof ServerPlayer serverPlayer) {
                JNECriteriaTriggers.REVIVE_CARCASS.get().trigger(serverPlayer);
            }
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_REANIMATED, false);
        builder.define(REANIMATION_COOLDOWN, 0);
        builder.define(IMMORTAL_COOLDOWN, 0);
        builder.define(REANIMATION_FLAG, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsReanimated", this.isReanimated());
        nbt.putInt("ReanimationCooldown", this.getReanimationCooldown());
        nbt.putInt("ImmortalCooldown", this.getImmortalCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setReanimated(nbt.getBoolean("IsReanimated"));
        this.setReanimationCooldown(nbt.getInt("ReanimationCooldown"));
        this.setImmortalCooldown(nbt.getInt("ImmortalCooldown"));
    }

    public boolean isReanimated() {
        return this.entityData.get(IS_REANIMATED);
    }

    public void setReanimated(boolean reanimated) {
        this.entityData.set(IS_REANIMATED, reanimated);
    }

    public int getReanimationCooldown() {
        return this.entityData.get(REANIMATION_COOLDOWN);
    }

    public void setReanimationCooldown(int cooldown) {
        this.entityData.set(REANIMATION_COOLDOWN, cooldown);
    }

    public int getImmortalCooldown() {
        return this.entityData.get(IMMORTAL_COOLDOWN);
    }

    public void setImmortalCooldown(int cooldown) {
        this.entityData.set(IMMORTAL_COOLDOWN, cooldown);
    }

    public boolean getReanimationFlag() {
        return this.entityData.get(REANIMATION_FLAG);
    }

    public void setReanimationFlag(boolean flag) {
        this.entityData.set(REANIMATION_FLAG, flag);
    }

    ////////
    // AI //
    ////////

    private static class CarcassRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
        private final Carcass carcass;

        public CarcassRandomStrollGoal(Carcass carcass, double d) {
            super(carcass, d);
            this.carcass = carcass;
        }

        @Override
        public boolean canUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    private static class CarcassLookAtPlayerGoal extends LookAtPlayerGoal {
        private final Carcass carcass;

        public CarcassLookAtPlayerGoal(Carcass carcass, Class<? extends LivingEntity> entity, float f) {
            super(carcass, entity, f);
            this.carcass = carcass;
        }

        @Override
        public boolean canUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    private static class CarcassRandomLookAroundGoal extends RandomLookAroundGoal {
        private final Carcass carcass;

        public CarcassRandomLookAroundGoal(Carcass carcass) {
            super(carcass);
            this.carcass = carcass;
        }

        @Override
        public boolean canUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    private static class CarcassMoveTowardsTargetGoal extends MoveTowardsTargetGoal {
        private final Carcass carcass;

        public CarcassMoveTowardsTargetGoal(Carcass carcass, double d, float f) {
            super(carcass, d, f);
            this.carcass = carcass;
        }

        @Override
        public boolean canUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    private static class CarcassMeleeAttackGoal extends MeleeAttackGoal {
        private final Carcass carcass;

        public CarcassMeleeAttackGoal(Carcass carcass, double d, boolean bl) {
            super(carcass, d, bl);
            this.carcass = carcass;
        }

        @Override
        public boolean canUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.isReanimated()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return JNESoundEvents.ENTITY_CARCASS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }
}