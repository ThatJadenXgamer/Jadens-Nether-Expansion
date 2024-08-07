package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class EctoSlab extends Slime {
    private int changeType = 1;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState digAnimationState = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();

    private static final EntityDimensions UNDERGROUND_DIMENSIONS = new EntityDimensions(2.04F, 0.1F, true);

    private static final EntityDataAccessor<Boolean> IS_UNDERGROUND = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACK = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.BOOLEAN);

    public static final Predicate<LivingEntity> ECTO_SLAB_CAN_DAMAGE = (arg) -> arg.getType().is(JNETags.EntityTypes.ECTO_SLAB_POUNCE_DAMAGES);

    public EctoSlab(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);
        this.fixupDimensions();
        this.setMaxUpStep(2.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.moveControl = new EctoSlabMoveControl(this);
    }

    @Override
    public void tick() {
        super.tick();
        this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(0.3 * this.getSize()), this.getY(), this.getRandomZ(0.3 * this.getSize()), 0.0, 0.0, 0.0);

        if (this.level().isClientSide) {
            if (this.getIsUnderground()) {
                idleAnimationState.stop();
                digAnimationState.startIfStopped(this.tickCount);
            }
            else {
                idleAnimationState.startIfStopped(this.tickCount);
                digAnimationState.stop();
            }
            if (this.getAttack()) {
                attackAnimation.startIfStopped(this.tickCount);
            }
            else {
                attackAnimation.stop();
            }
        }
    }

    @Override
    public void aiStep() {
        if (this.isInWaterOrRain()) {
            if (getChangeType() == 0) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ENTITY_APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                this.discard();
            }
            else this.doExorcism();
        }
        refreshDimensions();
        super.aiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.4);
    }

    public static boolean checkEctoSlabSpawnRules(EntityType<MagmaCube> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed(this) && !levelReader.containsAnyLiquid(this.getBoundingBox());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EctoSlabFloatGoal(this));
        this.goalSelector.addGoal(2, new EctoSlabAttackGoal(this));
        this.goalSelector.addGoal(3, new EctoSlabRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new EctoSlabMoveGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    /////////
    // NBT //
    /////////

    public int getChangeType() {
        return this.changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsUnderground", this.getIsUnderground());
        nbt.putInt("ChangeType", this.getChangeType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setIsUnderground(nbt.getBoolean("IsUnderground"));
        this.setChangeType(nbt.getInt("ChangeType"));
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        return this.isRemoved() || this.isInvulnerable() || this.getIsUnderground() && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !damageSource.isCreativePlayer() || damageSource.is(DamageTypeTags.IS_FIRE) && this.fireImmune() || damageSource.is(DamageTypeTags.IS_FALL) && this.getType().is(EntityTypeTags.FALL_DAMAGE_IMMUNE);
    }

    @Override
    public boolean ignoreExplosion() {
        return this.getIsUnderground();
    }

    @Override
    public boolean isPushable() {
        return !this.getIsUnderground() && super.isPushable();
    }

    @Override
    protected void pushEntities() {
        if (!this.getIsUnderground()) {
            super.pushEntities();
        }
    }

    public void push(Entity entity) {
        super.push(entity);
        if (entity instanceof Piglin && this.isDealsDamage()) {
            this.dealDamage((LivingEntity)entity);
        }
    }


    @Override
    public void refreshDimensions() {
        super.refreshDimensions();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose pose) {
        return getIsUnderground() ? UNDERGROUND_DIMENSIONS : super.getDimensions(pose);
    }

    @Override
    public void setSize(int i, boolean bl) {
        super.setSize(i, bl);
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(i * 3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    protected @NotNull ParticleOptions getParticleType() {
        return getIsUnderground() ? new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()) : ParticleTypes.SOUL_FIRE_FLAME;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }

    @Override
    protected void decreaseSquish() {
        this.targetSquish *= 0.9F;
    }

    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        float f = (float)this.getSize() * 0.1F;
        this.setDeltaMovement(vec3.x, this.getJumpPower() + f, vec3.z);
        this.hasImpulse = true;
    }

    @Override
    protected void jumpInLiquid(TagKey<Fluid> tagKey) {
        if (tagKey == FluidTags.LAVA || tagKey == JNETags.Fluids.ECTOPLASM) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, 0.22F + (float)this.getSize() * 0.05F, vec3.z);
            this.hasImpulse = true;
        } else {
            super.jumpInLiquid(tagKey);
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (!this.getIsUnderground()) {
            super.playerTouch(player);
        }
    }

    @Override
    protected boolean isDealsDamage() {
        return this.isEffectiveAi();
    }

    @Override
    protected float getAttackDamage() {
        return super.getAttackDamage() + 2.0F;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isTiny() ? SoundEvents.MAGMA_CUBE_HURT_SMALL : SoundEvents.MAGMA_CUBE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isTiny() ? SoundEvents.MAGMA_CUBE_DEATH_SMALL : SoundEvents.MAGMA_CUBE_DEATH;
    }

    @Override
    protected @NotNull SoundEvent getSquishSound() {
        return this.isTiny() ? SoundEvents.MAGMA_CUBE_SQUISH_SMALL : SoundEvents.MAGMA_CUBE_SQUISH;
    }

    @Override
    protected @NotNull SoundEvent getJumpSound() {
        return SoundEvents.MAGMA_CUBE_JUMP;
    }

    float getSoundPitch() {
        float f = this.isTiny() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }

    public boolean getIsUnderground() {
        return this.getEntityData().get(IS_UNDERGROUND);
    }

    private void setIsUnderground(boolean bl) {
        this.getEntityData().set(IS_UNDERGROUND, bl);
    }

    public boolean getAttack() {
        return this.getEntityData().get(ATTACK);
    }

    private void setAttack(boolean bl) {
        this.getEntityData().set(ATTACK, bl);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_UNDERGROUND, false);
        this.getEntityData().define(ATTACK, false);
    }

    private void damageLivingEntities(List<LivingEntity> allEntities) {
        for (LivingEntity entity : allEntities) {
            if (entity instanceof LivingEntity) {
                entity.hurt(this.damageSources().mobAttack(this), this.getAttackDamage() * 2);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getDirectEntity() instanceof ThrownPotion thrownPotion && hurtWithCleanWater(thrownPotion)) {
            if (getChangeType() > 0) {
                doExorcism();
            }
            if (thrownPotion.getOwner() instanceof Player player) {
                JNECriteriaTriggers.EXORCISM.trigger((ServerPlayer) player);
            }
        }
        return super.hurt(damageSource, f);
    }

    private void doExorcism() {
        MagmaCube magmaCube = this.convertTo(EntityType.MAGMA_CUBE, false);
        if (magmaCube != null && this.level() instanceof ServerLevel serverLevel) {
            magmaCube.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(magmaCube.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, false), null);
            magmaCube.setSize(getSize(), true);
            magmaCube.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            magmaCube.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
            magmaCube.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            if (this.hasCustomName()) {
                magmaCube.setCustomName(magmaCube.getCustomName());
            }
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ENTITY_APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        for(int i = 0; i < 10; i++) {
            this.level().addParticle(JNEParticleTypes.WISP.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
        }
    }

    private boolean hurtWithCleanWater(ThrownPotion thrownPotion) {
        ItemStack itemStack = thrownPotion.getItem();
        Potion potion = PotionUtils.getPotion(itemStack);
        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemStack);
        return potion == Potions.WATER && list.isEmpty();
    }

    ////////
    // AI //
    ////////

    private static class EctoSlabMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final EctoSlab ectoSlab;
        private boolean isAggressive;

        public EctoSlabMoveControl(EctoSlab ectoSlab) {
            super(ectoSlab);
            this.ectoSlab = ectoSlab;
            this.yRot = 180.0F * ectoSlab.getYRot() / 3.1415927F;
        }

        public void setDirection(float f, boolean bl) {
            this.yRot = f;
            this.isAggressive = bl;
        }

        public void setWantedMovement(double d) {
            this.speedModifier = d;
            this.operation = Operation.MOVE_TO;
        }

        public void tick() {
            float n;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (ectoSlab.getIsUnderground()) {
                if (this.operation == Operation.MOVE_TO) {
                    double x = this.wantedX - this.mob.getX();
                    double z = this.wantedZ - this.mob.getZ();
                    double y = this.wantedY - this.mob.getY();
                    double p = x * x + y * y + z * z;
                    if (p < 2.500000277905201E-7) {
                        this.mob.setZza(0.0F);
                        return;
                    }

                    n = (float)(Mth.atan2(z, x) * 57.2957763671875) - 90.0F;
                    this.mob.setYRot(this.rotlerp(this.mob.getYRot(), n, 90.0F));
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
                else {
                    this.mob.setZza(0.0F);
                }
            }
            else {
                if (this.operation != Operation.MOVE_TO) {
                    this.mob.setZza(0.0F);
                }
                else {
                    this.operation = Operation.WAIT;
                    if (this.mob.onGround()) {
                        this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                        if (this.jumpDelay-- <= 0) {
                            this.jumpDelay = this.ectoSlab.getJumpDelay();
                            if (this.isAggressive) {
                                this.jumpDelay /= 3;
                            }

                            this.ectoSlab.getJumpControl().jump();
                            if (this.ectoSlab.doPlayJumpSound()) {
                                this.ectoSlab.playSound(this.ectoSlab.getJumpSound(), this.ectoSlab.getSoundVolume(), this.ectoSlab.getSoundPitch());
                            }
                        } else {
                            this.ectoSlab.xxa = 0.0F;
                            this.ectoSlab.zza = 0.0F;
                            this.mob.setSpeed(0.0F);
                        }
                    } else {
                        this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    }
                }
            }
        }
    }

    static class EctoSlabFloatGoal extends Goal {
        private final EctoSlab ectoSlab;

        public EctoSlabFloatGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
            ectoSlab.getNavigation().setCanFloat(true);
        }

        public boolean canUse() {
            return (this.ectoSlab.isInWater() || this.ectoSlab.isInLava()) && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.ectoSlab.getRandom().nextFloat() < 0.8F) {
                this.ectoSlab.getJumpControl().jump();
            }

            MoveControl var2 = this.ectoSlab.getMoveControl();
            if (var2 instanceof EctoSlabMoveControl slimeMoveControl) {
                slimeMoveControl.setWantedMovement(1.2);
            }

        }
    }

    static class EctoSlabAttackGoal extends Goal {
        private final EctoSlab ectoSlab;
        private int undergroundTime;

        public EctoSlabAttackGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingEntity = this.ectoSlab.getTarget();
            if (livingEntity == null) {
                return false;
            } else {
                return this.ectoSlab.canAttack(livingEntity) && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
            }
        }

        public void start() {
            this.undergroundTime = reducedTickDelay(180 + ectoSlab.random.nextInt(50));
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
            ectoSlab.setAttack(false);
        }

        public boolean canContinueToUse() {
            LivingEntity livingEntity = this.ectoSlab.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!this.ectoSlab.canAttack(livingEntity)) {
                return false;
            } else {
                return ectoSlab.isTiny() ? this.undergroundTime > -240 : this.undergroundTime > -140;
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            --undergroundTime;
            if (undergroundTime > 0) {
                ectoSlab.setIsUnderground(true);
                ectoSlab.refreshDimensions();
            }
            else {
                ectoSlab.setIsUnderground(false);
                ectoSlab.refreshDimensions();
                ectoSlab.setAttack(true);
                ectoSlab.damageLivingEntities(ectoSlab.level().getEntitiesOfClass(LivingEntity.class, this.ectoSlab.getBoundingBox(), ECTO_SLAB_CAN_DAMAGE));
                for (int i = 0; i < 10; ++i) {
                    ectoSlab.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, ectoSlab.getBlockStateOn()), ectoSlab.getRandomX(0.3 * ectoSlab.getSize()), ectoSlab.getY(), ectoSlab.getRandomZ(0.3 * ectoSlab.getSize()), 0.0, 0.5, 0.0);
                }
            }
            LivingEntity livingEntity = this.ectoSlab.getTarget();
            if (livingEntity != null) {
                this.ectoSlab.lookAt(livingEntity, 10.0F, 10.0F);
            }

            MoveControl var3 = this.ectoSlab.getMoveControl();
            if (var3 instanceof EctoSlabMoveControl ectoSlabMoveControl) {
                ectoSlabMoveControl.setDirection(this.ectoSlab.getYRot(), this.ectoSlab.isDealsDamage());
                assert livingEntity != null;
                if (undergroundTime > 20) {
                    ectoSlabMoveControl.setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                }
            }
        }
    }

    private static class EctoSlabRandomDirectionGoal extends Goal {
        private final EctoSlab ectoSlab;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public EctoSlabRandomDirectionGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            return this.ectoSlab.getTarget() == null && (this.ectoSlab.onGround() || this.ectoSlab.isInWater() || this.ectoSlab.isInLava() || this.ectoSlab.hasEffect(MobEffects.LEVITATION)) && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.ectoSlab.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.ectoSlab.getRandom().nextInt(360);
            }

            MoveControl var2 = this.ectoSlab.getMoveControl();
            if (var2 instanceof EctoSlabMoveControl ectoSlabMoveControl) {
                ectoSlabMoveControl.setDirection(this.chosenDegrees, false);
            }

        }
    }

    private static class EctoSlabMoveGoal extends Goal {
        private final EctoSlab ectoSlab;

        public EctoSlabMoveGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        public boolean canUse() {
            return !this.ectoSlab.isPassenger();
        }

        public void tick() {
            MoveControl var2 = this.ectoSlab.getMoveControl();
            if (var2 instanceof EctoSlabMoveControl ectoSlabMoveControl) {
                ectoSlabMoveControl.setWantedMovement(1.0);
            }
        }
    }
}
