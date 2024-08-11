package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;

public class Banshee extends Monster {
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

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getEntity() instanceof LivingEntity) {
            teleport();
        }
        return super.hurt(damageSource, f);
    }

    protected boolean teleport() {
        if (!this.level().isClientSide() && this.isAlive()) {
            double x = this.getX() + (this.random.nextDouble() - 0.5) * 12.0;
            double y = this.getY() + (double)(this.random.nextInt(10) - 8);
            double z = this.getZ() + (this.random.nextDouble() - 0.5) * 12.0;
            return this.teleport(x, y, z);
        } else {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean teleport(double x, double y, double z) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, y, z);

        while(mutablePos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(mutablePos).blocksMotion()) {
            mutablePos.move(Direction.DOWN);
        }

        BlockState state = this.level().getBlockState(mutablePos);
        boolean isWater = state.getFluidState().is(FluidTags.WATER);
        if (!isWater) {
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

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
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

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        fallDistance = 0;
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
