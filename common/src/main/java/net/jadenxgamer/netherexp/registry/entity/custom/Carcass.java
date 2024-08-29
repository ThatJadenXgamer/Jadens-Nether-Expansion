package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Carcass extends PathfinderMob {

    private static final EntityDataAccessor<Boolean> IS_REANIMATED = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> REANIMATION_COOLDOWN = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
    public final AnimationState reanimateAnimationState = new AnimationState();
    public final AnimationState deactivateAnimationState = new AnimationState();

    private int deactivationAnimationTimer;
    private int reanimationAnimationTimer;
    private boolean reanimationFlag = false;

    public Carcass(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.reanimationAnimationTimer = 22;
        this.deactivationAnimationTimer = 25;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            if (this.getIsReanimated()) {
                if (this.isMoving()) {
                    idleAnimationState.stop();
                    moveAnimationState.startIfStopped(this.tickCount);
                }
                else {
                    moveAnimationState.stop();
                    idleAnimationState.startIfStopped(this.tickCount);
                }
            }
            else if (!this.getIsReanimated() && !this.reanimationFlag && deactivationAnimationTimer == 25) {
                deactivateAnimationState.startIfStopped(this.tickCount);
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
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CarcassMeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new CarcassMoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.addGoal(7, new CarcassLookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new CarcassRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new CarcassRandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (livingEntity) -> livingEntity instanceof Enemy && !(livingEntity instanceof Creeper)));
    }

    @Override
    public boolean isDeadOrDying() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (!this.getIsReanimated()) {
            if (damageSource.isCreativePlayer() || damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.discard();
                return true;
            }
            else return false;
        }
        return super.hurt(damageSource, f);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.deactivateAnimationState.startIfStopped(this.tickCount);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        int cooldown = this.getReanimationCooldown();
        if (this.reanimationFlag) {
            this.reanimateCarcass();
        }
        if (this.getIsReanimated() && this.getHealth() <= 0) {
            this.deactivateCarcass();
        }
        if (cooldown > 0 && !this.getIsReanimated()) {
            this.setReanimationCooldown(--cooldown);
        }
    }

    @Override
    public boolean isEffectiveAi() {
        return super.isEffectiveAi();
    }

    private void reanimateCarcass() {
        if (this.reanimationAnimationTimer == 22) {
            reanimateAnimationState.startIfStopped(this.tickCount);
            deactivateAnimationState.stop();
        }
        if (this.reanimationAnimationTimer > 0) {
            --this.reanimationAnimationTimer;
        }
        if (this.reanimationAnimationTimer <= 0) {
            reanimateAnimationState.stop();
            this.setIsReanimated(true);
            this.reanimationFlag = false;
            this.reanimationAnimationTimer = 22;
        }
    }

    private void deactivateCarcass() {
        if (this.deactivationAnimationTimer == 25) {
            idleAnimationState.stop();
            moveAnimationState.stop();
        }
        if (this.deactivationAnimationTimer > 0) {
            --this.deactivationAnimationTimer;
        }
        if (this.deactivationAnimationTimer <= 0) {
            this.setIsReanimated(false);
            this.setHealth(this.getMaxHealth());
            this.setReanimationCooldown(36000);
            this.deactivationAnimationTimer = 25;
        }
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.FLINT_AND_STEEL) && !this.getIsReanimated()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getX(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.reanimationFlag = true;
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_REANIMATED, false);
        this.entityData.define(REANIMATION_COOLDOWN, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsReanimated", this.getIsReanimated());
        nbt.putInt("ReanimationCooldown", this.getReanimationCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setIsReanimated(nbt.getBoolean("IsReanimated"));
        this.setReanimationCooldown(nbt.getInt("ReanimationCooldown"));
    }

    public boolean getIsReanimated() {
        return this.entityData.get(IS_REANIMATED);
    }

    public void setIsReanimated(boolean reanimated) {
        this.entityData.set(IS_REANIMATED, reanimated);
    }

    public int getReanimationCooldown() {
        return this.entityData.get(REANIMATION_COOLDOWN);
    }

    public void setReanimationCooldown(int cooldown) {
        this.entityData.set(REANIMATION_COOLDOWN, cooldown);
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
            if (!this.carcass.getIsReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.getIsReanimated()) {
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
            if (!this.carcass.getIsReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.getIsReanimated()) {
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
            if (!this.carcass.getIsReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.getIsReanimated()) {
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
            if (!this.carcass.getIsReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.getIsReanimated()) {
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
            if (!this.carcass.getIsReanimated()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.carcass.getIsReanimated()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }
}
