package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

public class Carcass extends PathfinderMob {

    private static final EntityDataAccessor<Boolean> IS_REANIMATED = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_IMMORTAL = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> REANIMATION_COOLDOWN = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> REANIMATION_FLAG = SynchedEntityData.defineId(Carcass.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDimensions DEACTIVE_DIMENSIONS = new EntityDimensions(2.2F, 1.0F, true);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState moveAnimationState = new AnimationState();
    public final AnimationState reanimateAnimationState = new AnimationState();
    public final AnimationState deactivateAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private int deactivationAnimationTimer;
    private int reanimationAnimationTimer;

    public Carcass(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.fixupDimensions();
        this.reanimationAnimationTimer = 22;
        this.deactivationAnimationTimer = 25;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getReanimationFlag()) {
            this.reanimateCarcass();
        }
        if (this.getIsReanimated() && this.getHealth() <= 0) {
            this.deactivateCarcass();
        }

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
            else if (!this.getIsReanimated() && !this.getReanimationFlag() && deactivationAnimationTimer == 25) {
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

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CarcassMeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new CarcassMoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.addGoal(7, new CarcassLookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new CarcassRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new CarcassRandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (livingEntity) -> livingEntity instanceof Enemy && !(livingEntity instanceof Creeper)));
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
    public EntityDimensions getDimensions(Pose pPose) {
        return !getIsReanimated() ? DEACTIVE_DIMENSIONS : super.getDimensions(pPose);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (!this.getIsReanimated()) {
            if (damageSource.isCreativePlayer() || damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.discard();
                return true;
            }
            else if (damageSource.getDirectEntity() instanceof Projectile projectile && projectile.isOnFire()) {
                this.setReanimationFlag(true);
            }
            else return false;
        }
        if (this.getIsImmortal()) {
            return false;
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
        if (pId == 40) this.attackAnimationState.start(this.tickCount);
        else super.handleEntityEvent(pId);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.deactivateAnimationState.startIfStopped(this.tickCount);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide && this.getIsImmortal()) {
            this.level().addParticle(JNEParticleTypes.TREACHEROUS_FLAME.get(), this.getRandomX(0.7), this.getRandomY() - 0.25, this.getRandomZ(0.7), 0.0, 0.07, 0.0);
        }
        super.aiStep();
    }

    @Override
    public boolean isEffectiveAi() {
        return super.isEffectiveAi();
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
            this.setIsReanimated(true);
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
            this.setIsReanimated(false);
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
        if (stack.is(Items.FLINT_AND_STEEL) && !this.getIsReanimated()) {
            this.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.setReanimationFlag(true);
            if (player instanceof ServerPlayer serverPlayer) {
                JNECriteriaTriggers.REVIVE_CARCASS.trigger(serverPlayer);
            }
            return InteractionResult.SUCCESS;
        }
        else if (stack.is(Items.FIRE_CHARGE) && !this.getIsReanimated()) {
            this.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
            this.setReanimationFlag(true);
            if (player instanceof ServerPlayer serverPlayer) {
                JNECriteriaTriggers.REVIVE_CARCASS.trigger(serverPlayer);
            }
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        if (this.getIsReanimated()) {
            if (stack.is(JNEItems.TREACHEROUS_FLAME.get()) && !this.getIsImmortal()) {
                this.level().playSound(null, player.getX(), player.getY(), player.getZ(), JNESoundEvents.BRAZIER_CHEST_LIT.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                this.setIsImmortal(true);
                if (this.level().isClientSide && !NetherExpClient.CARCASS_TUTORIAL_SEEN) {
                    player.displayClientMessage(Component.translatable("entity.netherexp.carcass.flame_tutorial").withStyle(ChatFormatting.DARK_GRAY), true);
                    NetherExpClient.CARCASS_TUTORIAL_SEEN = true;
                }
                if (player instanceof ServerPlayer serverPlayer) {
                    JNECriteriaTriggers.IMMORTAL_CARCASS.trigger(serverPlayer);
                }
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            else if (stack.isEmpty() && this.getIsImmortal()) {
                player.level().playSound(null, player.getOnPos(), JNESoundEvents.TREACHEROUS_CANDLE_SPAWN.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                this.setIsImmortal(false);
                player.setItemInHand(hand, JNEItems.TREACHEROUS_FLAME.get().getDefaultInstance());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_REANIMATED, false);
        this.entityData.define(IS_IMMORTAL, false);
        this.entityData.define(REANIMATION_COOLDOWN, 0);
        this.entityData.define(REANIMATION_FLAG, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsReanimated", this.getIsReanimated());
        nbt.putBoolean("IsImmortal", this.getIsImmortal());
        nbt.putInt("ReanimationCooldown", this.getReanimationCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setIsReanimated(nbt.getBoolean("IsReanimated"));
        this.setIsImmortal(nbt.getBoolean("IsImmortal"));
        this.setReanimationCooldown(nbt.getInt("ReanimationCooldown"));
    }

    public boolean getIsReanimated() {
        return this.entityData.get(IS_REANIMATED);
    }

    public void setIsReanimated(boolean reanimated) {
        this.entityData.set(IS_REANIMATED, reanimated);
    }

    public boolean getIsImmortal() {
        return this.entityData.get(IS_IMMORTAL);
    }

    public void setIsImmortal(boolean immortal) {
        this.entityData.set(IS_IMMORTAL, immortal);
    }

    public int getReanimationCooldown() {
        return this.entityData.get(REANIMATION_COOLDOWN);
    }

    public void setReanimationCooldown(int cooldown) {
        this.entityData.set(REANIMATION_COOLDOWN, cooldown);
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
