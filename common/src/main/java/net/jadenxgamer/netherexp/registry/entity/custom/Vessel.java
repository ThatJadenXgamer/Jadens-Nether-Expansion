package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Vessel extends Monster {
    public Vessel(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 12.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new VesselAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
    }

    ////////
    // AI //
    ////////

    private static class VesselAttackGoal extends Goal {
        private final Vessel vessel;
        private int attackTime;
        private int lastSeen;

        public VesselAttackGoal(Vessel vessel) {
            this.vessel = vessel;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.vessel.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.vessel.canAttack(livingEntity);
        }

        @Override
        public void stop() {
            this.lastSeen = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            --this.attackTime;
            LivingEntity livingEntity = this.vessel.getTarget();
            if (livingEntity != null) {
                boolean bl = this.vessel.getSensing().hasLineOfSight(livingEntity);
                if (bl) {
                    this.lastSeen = 0;
                } else {
                    ++this.lastSeen;
                }

                double d = this.vessel.distanceToSqr(livingEntity);
                if (d < this.getFollowDistance() * this.getFollowDistance() && bl) {
                    this.vessel.getLookControl().setLookAt(livingEntity, 10.0F, 10.0F);
                    if (this.attackTime == 40) {
                        vessel.level().playSound(null, vessel.getX(), vessel.getY(), vessel.getZ(), SoundEvents.AMETHYST_BLOCK_BREAK, vessel.getSoundSource(), 1.0F, 1.0F);
                    }
                    else if (this.attackTime <= 0) {
                        this.attackTime = 100;
                        vessel.level().playSound(null, vessel.getX(), vessel.getY(), vessel.getZ(), JNESoundEvents.SHOTGUN_USE.get(), vessel.getSoundSource(), 1.0F, 1.0F);
                        int count = Mth.nextInt(vessel.level().random, 8, 16);
                        for (int i = 0; i < count; i++) {
                            SoulBullet soulBullet = new SoulBullet(vessel.level(), vessel);
                            Vec3 lookVector = vessel.getLookAngle();
                            soulBullet.shoot(lookVector.x, lookVector.y, lookVector.z, 1.0F, 16);
                            vessel.level().addFreshEntity(soulBullet);
                        }
                    }
                }
                else if (this.lastSeen < 5) {
                    this.vessel.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
                }
            }

            super.tick();
        }

        private double getFollowDistance() {
            return this.vessel.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }
}
