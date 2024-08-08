package net.jadenxgamer.netherexp.registry.entity.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
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

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23000000417232513)
                .add(Attributes.FOLLOW_RANGE, 16.0);
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

    static class BansheeAttackGoal extends Goal {
        private int attackTime;
        private final Banshee banshee;

        public BansheeAttackGoal(Banshee banshee) {
            this.banshee = banshee;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingEntity = this.banshee.getTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                return this.banshee.level().getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return false;
            }
        }

        public void start() {
            this.attackTime = 20;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.banshee.level().getDifficulty() != Difficulty.PEACEFUL) {
                --this.attackTime;
                LivingEntity target = this.banshee.getTarget();
                if (target != null) {
                    this.banshee.getLookControl().setLookAt(target, 180.0F, 180.0F);
                    double d = this.banshee.distanceToSqr(target);
                    if (d < 400.0) {
                        if (this.attackTime <= 0) {
                            this.attackTime = 20 + this.banshee.random.nextInt(10) * 20 / 2;
                            this.banshee.level().addFreshEntity(new WillOWisp(this.banshee, this.banshee.level(), target, banshee.getX(), banshee.getY() + 0.5, banshee.getZ(), 0.2f));
                            this.banshee.playSound(SoundEvents.SHULKER_SHOOT, 2.0F, (this.banshee.random.nextFloat() - this.banshee.random.nextFloat()) * 0.2F + 1.0F);
                        }
                    } else {
                        this.banshee.setTarget(null);
                    }
                    super.tick();
                }
            }
        }
    }
}
