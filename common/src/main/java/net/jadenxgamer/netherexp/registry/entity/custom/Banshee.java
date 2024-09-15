package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Banshee extends Monster {
    private int changeType = 1;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();

    public Banshee(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.xpReward = 10;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            if (isMoving()) {
                walkAnimationState.startIfStopped(this.tickCount);
                idleAnimationState.stop();
            }
            else {
                idleAnimationState.startIfStopped(this.tickCount);
                walkAnimationState.stop();
            }
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
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
        super.aiStep();
    }

    @Override
    protected void customServerAiStep() {
        LivingEntity target = this.getTarget();
        if (target != null && this.canAttack(target)) {
            this.setNoGravity(true);
            if (this.random.nextInt(40) == 0) {
                teleport();
            }
        } else {
            this.setNoGravity(false);
        }

        super.customServerAiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26)
                .add(Attributes.FOLLOW_RANGE, 26.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BansheeAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
    }

    private void doExorcism() {
        Blaze blaze = this.convertTo(EntityType.BLAZE, false);
        if (blaze != null && this.level() instanceof ServerLevel serverLevel) {
            blaze.finalizeSpawn(serverLevel, this.level().getCurrentDifficultyAt(blaze.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, false), null);
            blaze.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            blaze.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
            blaze.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            if (this.hasCustomName()) {
                blaze.setCustomName(blaze.getCustomName());
            }
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.ENTITY_APPARITION_DEATH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        for(int i = 0; i < 10; i++) {
            this.level().addParticle(JNEParticleTypes.WISP.get(), this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getDirectEntity() instanceof ThrownPotion thrownPotion && hurtWithCleanWater(thrownPotion) && getChangeType() > 0) {
            doExorcism();
            if (thrownPotion.getOwner() instanceof Player player) {
                JNECriteriaTriggers.EXORCISM.trigger((ServerPlayer) player);
            }
        }
        else if (damageSource.getEntity() instanceof LivingEntity) {
            teleport();
        }
        return super.hurt(damageSource, f);
    }

    private boolean hurtWithCleanWater(ThrownPotion thrownPotion) {
        ItemStack itemStack = thrownPotion.getItem();
        Potion potion = PotionUtils.getPotion(itemStack);
        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemStack);
        return potion == Potions.WATER && list.isEmpty();
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        int escapingOdds = this.random.nextInt(this.level().getDifficulty() == Difficulty.HARD ? 3 : 5);
        if (this.level().getDifficulty() != Difficulty.EASY && escapingOdds == 0) {
            Apparition apparition = JNEEntityType.APPARITION.get().create(this.level());
            if (apparition != null) {
                apparition.setPos(this.getX(), this.getY(), this.getZ());
                apparition.setCooldown(1200);
                if (this.getTarget() != null) {
                    apparition.setTarget(this.getTarget());
                }
            }
            this.level().addFreshEntity(apparition);
        }
    }

    protected boolean teleport() {
        if (!this.level().isClientSide() && this.isAlive()) {
            double x = this.getX() + (this.random.nextDouble() - 0.5) * 12.0;
            double z = this.getZ() + (this.random.nextDouble() - 0.5) * 12.0;
            return this.teleport(x, this.getY(), z);
        } else {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean teleport(double x, double y, double z) {
        BlockPos targetPos = new BlockPos((int) x, (int) y - 1, (int) z);

        BlockState state = this.level().getBlockState(targetPos);
        boolean isSolidGround = state.blocksMotion();
        boolean isLava = state.getFluidState().is(FluidTags.LAVA);

        if (isSolidGround && !isLava) {
            boolean teleport = this.randomTeleport(x, y, z, true);
            if (teleport) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.05, 0.0));

                if (!this.isSilent()) {
                    this.level().playSound(null, this.xo, this.yo, this.zo, JNESoundEvents.ENTITY_BANSHEE_TELEPORT.get(), this.getSoundSource(), 1.0F, 1.0F);
                    this.playSound(JNESoundEvents.ENTITY_BANSHEE_TELEPORT.get(), 1.0F, 1.0F);
                }
            }

            return teleport;
        } else {
            return false;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        fallDistance = 0;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return JNESoundEvents.ENTITY_BANSHEE_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JNESoundEvents.ENTITY_BANSHEE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return JNESoundEvents.ENTITY_BANSHEE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(JNESoundEvents.ENTITY_APPARITION_FLY.get(), 0.15F, 1.0F);
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
        nbt.putInt("ChangeType", this.getChangeType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setChangeType(nbt.getInt("ChangeType"));
    }

    static class BansheeAttackGoal extends Goal {
        private int attackTime;
        private final Banshee banshee;

        public BansheeAttackGoal(Banshee banshee) {
            this.banshee = banshee;
            this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity livingEntity = this.banshee.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        public void start() {
            this.attackTime = 20;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            --this.attackTime;
            LivingEntity target = this.banshee.getTarget();
            if (target != null) {
                this.banshee.getLookControl().setLookAt(target, 10.0f, 10.0f);
                double distance = this.banshee.distanceToSqr(target);
                if (distance < 5.0 && banshee.random.nextInt(30) == 0) {
                    this.banshee.teleport();
                }
                else if (distance < 300.0) {
                    if (this.attackTime == 20) {
                        this.banshee.level().addFreshEntity(new WillOWisp(this.banshee, this.banshee.level(), target, banshee.getX(), banshee.getY() + 0.5, banshee.getZ(), 0.2f, 6));
                        this.banshee.playSound(SoundEvents.SHULKER_SHOOT, 2.0F, (this.banshee.random.nextFloat() - this.banshee.random.nextFloat()) * 0.2F + 1.0F);
                    }
                    if (this.attackTime <= 0) {
                        this.attackTime = 40 + this.banshee.random.nextInt(30);
                        this.banshee.teleport();
                    }
                }
                super.tick();
            }
        }
    }
}
