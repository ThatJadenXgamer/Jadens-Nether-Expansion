package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class Vessel extends Monster implements RangedAttackMob {
    public Vessel(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 10.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new VesselAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JNESoundEvents.SHOTGUN_USE.get(), this.getSoundSource(), 1.0F, 1.0F);
        int count = Mth.nextInt(this.level().random, 8, 16);
        Vec3 look = this.getLookAngle();
        double d0 = livingEntity.getX() - this.getX();
        double d2 = livingEntity.getZ() - this.getZ();
        for (int i = 0; i < count; i++) {
            SoulBullet soulBullet = new SoulBullet(this.getX(), this.getY() + 1.5, this.getZ(), this.level(), this);
            soulBullet.shoot(d0, look.y, d2, 1.0F, 16);
            this.level().addFreshEntity(soulBullet);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if (this.level().getDifficulty() == Difficulty.EASY) {
            for (int i = 0; i < this.level().getRandom().nextInt(2) + 1; i++) {
                Wisp wisp = JNEEntityType.WISP.get().create(this.level());
                if (wisp != null) {
                    wisp.setBoredDelay(0);
                    wisp.setPos(this.getX(), this.getY(), this.getZ());
                    this.level().addFreshEntity(wisp);
                }
            }
        }
        else {
            Apparition apparition = JNEEntityType.APPARITION.get().create(this.level());
            if (apparition != null) {
                apparition.setPos(this.getX(), this.getY(), this.getZ());
                if (this.getTarget() != null) {
                    apparition.setTarget(this.getTarget());
                }
            }
            this.level().addFreshEntity(apparition);
        }
    }

    ////////////
    // SOUNDS //
    ////////////

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
        private boolean shot;

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
        public boolean canContinueToUse() {
            return !shot && super.canContinueToUse();
        }

        @Override
        public void stop() {
            this.shot = false;
            this.attackTime = 80;
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

                double d = this.vessel.distanceToSqr(livingEntity);
                if (d < this.getFollowDistance() * this.getFollowDistance() && bl) {
                    this.vessel.getLookControl().setLookAt(livingEntity, 10.0F, 10.0F);
                    if (this.attackTime == 20) {
                        vessel.level().playSound(null, vessel.getX(), vessel.getY(), vessel.getZ(), SoundEvents.AMETHYST_BLOCK_BREAK, vessel.getSoundSource(), 1.0F, 1.0F);
                    } else if (this.attackTime <= 0) {
                        this.shot = true;
                        vessel.performRangedAttack(livingEntity, 1.0f);
                    }
                }
            }

            super.tick();
        }

        private double getFollowDistance() {
            return this.vessel.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }
}
