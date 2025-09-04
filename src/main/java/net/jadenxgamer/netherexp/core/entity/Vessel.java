package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Vessel extends PossessedMob implements RangedAttackMob {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState prepareAimAnimation = new AnimationState();
    public final AnimationState aimAnimation = new AnimationState();
    public final AnimationState shootAnimation = new AnimationState();
    public final AnimationState blinkAnimation = new AnimationState();

    private static final EntityDataAccessor<Boolean> IS_DOOM = SynchedEntityData.defineId(Vessel.class, EntityDataSerializers.BOOLEAN);
    private int idleAnimationTimeout = 0;
    private int prepareAimAnimationTimeout = 20;
    private boolean isAiming = false;
    private boolean isShooting = false;

    public Vessel(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level, NetherExp.idVanilla("skeleton"));
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 16.0);
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
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        SoundEvent fireSound = getFireSound();
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), fireSound, this.getSoundSource(), 1.0F, 1.0F);
        int count = Mth.nextInt(this.level().random, JNEConfigs.MIN_VESSEL_BULLETS.get(), JNEConfigs.MAX_VESSEL_BULLETS.get());
        Vec3 lookVector = this.getLookAngle();
        double xVector = target.getX() - this.getX();
        double zVector = target.getZ() - this.getZ();
        for (int i = 0; i < count; i++) {
            ShotgunPellet soulBullet = new ShotgunPellet(this.getX(), this.getY() + 1.5, this.getZ(), this.level(), this);
            soulBullet.shoot(xVector, lookVector.y, zVector, 1.0F, 16);
            this.level().addFreshEntity(soulBullet);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }

    //////////
    // DATA //
    //////////

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_DOOM, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsDoom", this.isDoom());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setDoom(nbt.getBoolean("IsDoom"));
    }

    @Override
    public int apparitionPersonality() {
        return 1;
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        var doomValidNames = name != null && (name.getString().equals("ShotgunGuy") || name.getString().equals("Shotgun Guy") || name.getString().equals("DOOM"));
        if (!this.isDoom() && doomValidNames) setDoom(true);
    }

    ///////////////////////
    // GETTERS & SETTERS //
    ///////////////////////

    public boolean isDoom() {
        return this.entityData.get(IS_DOOM);
    }

    public void setDoom(boolean doom) {
        this.entityData.set(IS_DOOM, doom);
    }

    ////////////////
    // ANIMATIONS //
    ////////////////

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 51 -> {
                prepareAimAnimationTimeout = 20;
                isAiming = true;
            }
            case 52 -> {
                prepareAimAnimation.stop();
                aimAnimation.stop();
                prepareAimAnimationTimeout = 20;
                isAiming = false;
            }
            case 53 -> isShooting = true;
            case 54 -> {
                isShooting = false;
                shootAnimation.stop();
            }
        }
        super.handleEntityEvent(id);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimation.startIfStopped(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (isAiming) {
            if (prepareAimAnimationTimeout == 20) prepareAimAnimation.startIfStopped(this.tickCount);
            else if (prepareAimAnimationTimeout == 0) {
                prepareAimAnimation.stop();
                aimAnimation.startIfStopped(this.tickCount);
            }
            if (prepareAimAnimationTimeout > 0) --prepareAimAnimationTimeout;
        }

        if (isShooting) {
            shootAnimation.startIfStopped(tickCount);
        } else if (this.random.nextInt(8) == 0) blinkAnimation.startIfStopped(this.tickCount);
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(partialTick * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    ////////////
    // SOUNDS //
    ////////////

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_AMBIENT.get() : JNESoundEvents.VESSEL_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_HURT.get() : JNESoundEvents.VESSEL_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_DEATH.get() : JNESoundEvents.VESSEL_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getFireSound() {
        return this.isDoom() ? JNESoundEvents.SHOTGUN_GUY_FIRE.get() : JNESoundEvents.VESSEL_FIRE.get();
    }

    ////////
    // AI //
    ////////

    private static class VesselAttackGoal extends Goal {
        private final Vessel vessel;
        private int attackTime;
        private boolean finished;
        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime;

        private static int ATTACK_INTERVAL = JNEConfigs.VESSEL_ATTACK_TIME.get();
        private static int START_AIM_AT_TICK = JNEConfigs.VESSEL_SHOOTS_AT_ATTACK_TIME.get();
        private static int WARN_AT_TICK = START_AIM_AT_TICK - 30;
        private static int SHOOT_AT_TICK = START_AIM_AT_TICK - 60;

        private VesselAttackGoal(Vessel vessel) {
            this.vessel = vessel;
            this.attackTime = ATTACK_INTERVAL;
            this.strafingTime = -1;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.vessel.getTarget();
            return target != null && target.isAlive() && this.vessel.canAttack(target);
        }

        @Override
        public boolean canContinueToUse() {
            return !finished && super.canContinueToUse();
        }

        @Override
        public void start() {
            this.attackTime = ATTACK_INTERVAL;
            super.start();
        }

        @Override
        public void stop() {
            this.attackTime = ATTACK_INTERVAL;
            vessel.level().broadcastEntityEvent(vessel, (byte) 52); // Stop Aim
            vessel.level().broadcastEntityEvent(vessel, (byte) 54); // Stop Shoot
            this.finished = false;
            this.seeTime = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            super.tick();
            LivingEntity target = this.vessel.getTarget();
            if (target == null) return;
            boolean hasSight = this.vessel.getSensing().hasLineOfSight(target);
            double distanceFromTarget = this.vessel.distanceToSqr(target);
            double attackRadius = getFollowDistance() * getFollowDistance();
            boolean hadSightLastTick = this.seeTime > 0;

            if (hasSight != hadSightLastTick) this.seeTime = 0;

            if (hasSight) {
                ++this.seeTime;
            } else --this.seeTime;

            if (!(distanceFromTarget > getFollowDistance()) && this.seeTime >= 20) {
                this.vessel.getNavigation().stop();
                ++this.strafingTime;
            } else {
                this.vessel.getNavigation().moveTo(target, 1.0);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if (this.vessel.getRandom().nextFloat() < 0.3) this.strafingClockwise = !this.strafingClockwise;
                if (this.vessel.getRandom().nextFloat() < 0.3) this.strafingBackwards = !this.strafingBackwards;
                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (distanceFromTarget > attackRadius * 0.75F) this.strafingBackwards = false;
                else if (distanceFromTarget < attackRadius * 0.25F) this.strafingBackwards = true;

                this.vessel.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                Entity vehicle = this.vessel.getControlledVehicle();
                if (vehicle instanceof Mob vehicleMob) vehicleMob.lookAt(target, 30.0F, 30.0F);

                this.vessel.lookAt(target, 30.0F, 30.0F);
            } else {
                this.vessel.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (distanceFromTarget < attackRadius) {
                if (this.attackTime > (SHOOT_AT_TICK + 10)) {
                    if (hasSight) --this.attackTime;
                } else --this.attackTime;

                // switch cases cannot be used since the constant is a runtime-evaluated expression 🥀 🥀 🥀
                if (this.attackTime == START_AIM_AT_TICK) vessel.level().broadcastEntityEvent(vessel, (byte) 51); // Start Aim
                if (this.attackTime == WARN_AT_TICK) vessel.level().playSound(null, vessel.blockPosition(), JNESoundEvents.VESSEL_WARN.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
                if (this.attackTime == SHOOT_AT_TICK) {
                    vessel.level().broadcastEntityEvent(vessel, (byte) 52); // Stop Aim
                    vessel.level().broadcastEntityEvent(vessel, (byte) 53); // Start Shoot
                    vessel.performRangedAttack(target, 1.0f);
                }
                if (this.attackTime == 0) this.finished = true;
            }
        }

        private double getFollowDistance() {
            return this.vessel.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }
}
