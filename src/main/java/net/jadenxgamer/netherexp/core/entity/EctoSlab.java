package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;

import java.awt.*;
import java.util.EnumSet;

import static net.jadenxgamer.netherexp.config.JNEConfigs.SILVER_PARANORMAL_DAMAGE_MULTIPLIER;
import static net.jadenxgamer.netherexp.config.JNEConfigs.SILVER_PARANORMAL_INFLICTS_SLOWNESS;

public class EctoSlab extends PossessedMob {

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState idleMirroredAnimation = new AnimationState();
    public final AnimationState idleBurrowedAnimation = new AnimationState();
    public final AnimationState burrowAnimation = new AnimationState();
    public final AnimationState emergeAnimation = new AnimationState();
    private boolean wasOnGround;
    private int burrowedCooldown = 40;

    private static final EntityDimensions BURROWED_DIMENSIONS = EntityDimensions.scalable(1.375f, 0.2f);
    private static final EntityDataAccessor<Boolean> IS_BURROWED = SynchedEntityData.defineId(EctoSlab.class, EntityDataSerializers.BOOLEAN);
    private int burrowAnimationAnimationTimer = 20;
    private int emergeAnimationAnimationTimer = 15;
    private boolean belowGroundAnimation = false;
    private boolean aboveGroundAnimation = true;
    public boolean showLight = false;
    public float currentShadowRadius = 0.8f;

    public EctoSlab(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level, NetherExp.minecraftPath("magma_cube"));
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        this.moveControl = new EctoSlabMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.JUMP_STRENGTH, 0.55)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EctoSlabFloatGoal(this));
        this.goalSelector.addGoal(2, new EctoSlabAttackGoal(this));
        this.goalSelector.addGoal(3, new EctoSlabRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new EctoSlabKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) this.setupAnimationStates();
        if (!isBurrowed() && (this.onGround() && !this.wasOnGround)) {
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        }
        this.wasOnGround = this.onGround();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override
    public void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
        net.neoforged.neoforge.common.CommonHooks.onLivingJump(this);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof ThrownPotion potion && hurtWithCleanWater(potion)) doExorcism();
        if (isBurrowed() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return false;
        if (source.getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(JNETags.Items.SILVER_WEAPONS)) {
                amount *= SILVER_PARANORMAL_DAMAGE_MULTIPLIER.get();
                if (SILVER_PARANORMAL_INFLICTS_SLOWNESS.get()) this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
                this.level().broadcastEntityEvent(this, (byte) 91);
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isPushable() {
        return !isBurrowed() && super.isPushable();
    }

    @Override
    protected void pushEntities() {
        if (!isBurrowed()) super.pushEntities();
    }

    @Override
    public void push(Entity entity) {
        if (isBurrowed()) return;
        super.push(entity);
        if (entity instanceof Piglin || entity instanceof IronGolem) this.dealDamage((LivingEntity) entity);
    }

    @Override
    public void playerTouch(Player entity) {
        if (!isBurrowed()) this.dealDamage(entity);
    }

    protected void dealDamage(LivingEntity victim) {
        if (this.isAlive() && this.isWithinMeleeAttackRange(victim) && this.hasLineOfSight(victim)) {
            DamageSource source = this.damageSources().mobAttack(this);
            if (victim.hurt(source, this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                if (this.level() instanceof ServerLevel serverlevel) EnchantmentHelper.doPostAttackEffects(serverlevel, victim, source);
            }
        }
    }

    //////////
    // DATA //
    //////////

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (IS_BURROWED.equals(key)) this.refreshDimensions();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_BURROWED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("isBurrowed", this.isBurrowed());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setBurrowed(nbt.getBoolean("isBurrowed"));
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return isBurrowed() ? BURROWED_DIMENSIONS : super.getDefaultDimensions(pose);
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public int apparitionPersonality() {
        return 3;
    }

    @Override
    protected double apparitionUnleashingOdds() {
        return super.apparitionUnleashingOdds();
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 81 -> {
                belowGroundAnimation = true;
                burrowAnimationAnimationTimer = 20;
            }
            case 82 -> {
                belowGroundAnimation = false;
                burrowAnimationAnimationTimer = 20;
                idleBurrowedAnimation.stop();
                burrowAnimation.stop();
            }
            case 83 -> {
                aboveGroundAnimation = true;
                emergeAnimationAnimationTimer = 15;
            }
            case 84 -> {
                aboveGroundAnimation = false;
                emergeAnimationAnimationTimer = 15;
                emergeAnimation.stop();
                idleAnimation.stop();
            }
            case 85 -> Client.warnParticle(this, this.level(), this.position());
        }
        super.handleEntityEvent(id);
    }

    ///////////////////////
    // GETTERS & SETTERS //
    ///////////////////////

    public boolean isBurrowed() {
        return this.entityData.get(IS_BURROWED);
    }

    public void setBurrowed(boolean burrowed) {
        this.entityData.set(IS_BURROWED, burrowed);
        this.refreshDimensions();
        if (burrowed) {
            this.level().broadcastEntityEvent(this, (byte) 81);
            this.level().broadcastEntityEvent(this, (byte) 84);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 83);
            this.level().broadcastEntityEvent(this, (byte) 82);
        }
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    ////////////////
    // ANIMATIONS //
    ////////////////

    private void setupAnimationStates() {
        if (belowGroundAnimation) {
            if (burrowAnimationAnimationTimer == 20) {
                burrowAnimation.startIfStopped(this.tickCount);
                showLight = true;
            }
            else if (burrowAnimationAnimationTimer == 0) {
                burrowAnimation.stop();
                idleBurrowedAnimation.startIfStopped(this.tickCount);
            }
            if (burrowAnimationAnimationTimer > 0) --burrowAnimationAnimationTimer;
        }
        if (aboveGroundAnimation) {
            if (emergeAnimationAnimationTimer == 15) emergeAnimation.startIfStopped(this.tickCount);
            else if (emergeAnimationAnimationTimer == 0) {
                emergeAnimation.stop();
                idleAnimation.startIfStopped(this.tickCount);
                showLight = false;
            }
            if (emergeAnimationAnimationTimer > 0) --emergeAnimationAnimationTimer;
        }

        if (isBurrowed()) Client.burrowedParticle(this, this.level(), this.random);
    }

    ////////////
    // SOUNDS //
    ////////////

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.MAGMA_CUBE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.MAGMA_CUBE_DEATH;
    }

    protected @NotNull SoundEvent getSquishSound() {
        return JNESoundEvents.ECTO_SLAB_SQUISH.get();
    }

    protected @NotNull SoundEvent getJumpSound() {
        return JNESoundEvents.ECTO_SLAB_JUMP.get();
    }


    protected @NotNull SoundEvent getWarnSound() {
        return JNESoundEvents.ECTO_SLAB_WARN.get();
    }

    ////////
    // AI //
    ////////

    static class EctoSlabMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final EctoSlab ectoSlab;
        private boolean isAggressive;

        public EctoSlabMoveControl(EctoSlab ectoSlab) {
            super(ectoSlab);
            this.ectoSlab = ectoSlab;
            this.yRot = 180.0F * ectoSlab.getYRot() / (float) Math.PI;
        }

        public void setDirection(float yRot, boolean aggressive) {
            this.yRot = yRot;
            this.isAggressive = aggressive;
        }

        public void setWantedMovement(double speed) {
            this.speedModifier = speed;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        @Override
        public void tick() {
            if (ectoSlab.isBurrowed()) {
                burrowBehavior();
            } else {
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
                this.mob.yHeadRot = this.mob.getYRot();
                this.mob.yBodyRot = this.mob.getYRot();
                slimeBehavior();
            }
        }

        private void slimeBehavior() {
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.ectoSlab.getJumpDelay();
                        if (this.isAggressive) this.jumpDelay /= 3;
                        this.ectoSlab.getJumpControl().jump();
                        this.ectoSlab.playSound(this.ectoSlab.getJumpSound(), this.ectoSlab.getSoundVolume(), 1.0f);
                    } else {
                        this.ectoSlab.xxa = 0.0F;
                        this.ectoSlab.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
        }

        private void burrowBehavior() {
            float n;
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
            } else this.mob.setZza(0.0F);
        }
    }

    static class EctoSlabAttackGoal extends Goal {
        private final EctoSlab ectoSlab;
        private int burrowedTime = 0;
        private int burrowAnimationDelay = 0;
        private static int MAX_DIG_TIME = 80;

        public EctoSlabAttackGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingentity = this.ectoSlab.getTarget();
            if (livingentity == null) return false;
            else return this.ectoSlab.canAttack(livingentity) && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
            burrowedTime = 0;
            burrowAnimationDelay = 0;
            ectoSlab.setBurrowed(false);
            ectoSlab.burrowedCooldown = 40;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.ectoSlab.getTarget();
            if (target == null) return false;
            else return this.ectoSlab.canAttack(target);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.ectoSlab.getTarget();
            if (target == null) return;

            if (!ectoSlab.isBurrowed()) {
                this.ectoSlab.lookAt(target, 10.0F, 10.0F);

                if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                    double dx = target.getX() - ectoSlab.getX();
                    double dz = target.getZ() - ectoSlab.getZ();
                    float yaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
                    control.setDirection(yaw, true);
                    control.setWantedMovement(ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                }

                if (ectoSlab.onGround() && ectoSlab.burrowedCooldown-- <= 0) {
                    ectoSlab.setBurrowed(true);
                    burrowedTime = MAX_DIG_TIME;
                    burrowAnimationDelay = 20;
                }
            } else {
                if (burrowAnimationDelay > 0) {
                    burrowAnimationDelay--;
                    if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                        control.setWantedPosition(ectoSlab.getX(), ectoSlab.getY(), ectoSlab.getZ(), 0.0);
                        ectoSlab.setDeltaMovement(Vec3.ZERO);
                        ectoSlab.setSpeed(0.0f);
                    }
                    return;
                }
                if (burrowedTime > 0) burrowedTime--;

                if (ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) {
                    if (burrowedTime >= 30) {
                        double dx = target.getX() - ectoSlab.getX();
                        double dy = target.getY() - ectoSlab.getY();
                        double dz = target.getZ() - ectoSlab.getZ();
                        double distSq = dx * dx + dy * dy + dz * dz;
                        boolean reached = distSq < 0.05;

                        if (reached) {
                            control.setWantedPosition(ectoSlab.getX(), ectoSlab.getY(), ectoSlab.getZ(), 0.0);
                            this.ectoSlab.setDeltaMovement(Vec3.ZERO);
                            this.ectoSlab.setSpeed(0.0f);
                        } else {
                            control.setWantedPosition(target.getX(), target.getY(), target.getZ(),
                                    ectoSlab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        }
                    }
                    if (burrowedTime == 30) {
                        ectoSlab.playSound(ectoSlab.getWarnSound(), this.ectoSlab.getSoundVolume(), 1.0f);
                        ectoSlab.level().getServer().execute(() -> ectoSlab.level().broadcastEntityEvent(ectoSlab, (byte) 85));
                    }
                    if (burrowedTime > 0 && burrowedTime < 30) {
                        control.setWantedPosition(ectoSlab.getX(), ectoSlab.getY(), ectoSlab.getZ(), 0.0);
                        this.ectoSlab.setDeltaMovement(Vec3.ZERO);
                        this.ectoSlab.setSpeed(0.0f);
                    }
                    if (burrowedTime == 0) {
                        ectoSlab.setBurrowed(false);
                        ectoSlab.burrowedCooldown = ectoSlab.level().random.nextInt(60, 80);
                    }
                }
            }
        }
    }

    static class EctoSlabFloatGoal extends Goal {
        private final EctoSlab ectoSlab;

        public EctoSlabFloatGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            ectoSlab.getNavigation().setCanFloat(true);
        }

        @Override
        public boolean canUse() {
            return (this.ectoSlab.isInWater() || this.ectoSlab.isInLava()) && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.ectoSlab.getRandom().nextFloat() < 0.8F) this.ectoSlab.getJumpControl().jump();
            if (this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) control.setWantedMovement(1.2);
        }
    }

    static class EctoSlabKeepOnJumpingGoal extends Goal {
        private final EctoSlab ectoSlab;

        public EctoSlabKeepOnJumpingGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.ectoSlab.isPassenger();
        }

        @Override
        public void tick() {
            if (this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) control.setWantedMovement(1.0);
        }
    }

    static class EctoSlabRandomDirectionGoal extends Goal {
        private final EctoSlab ectoSlab;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public EctoSlabRandomDirectionGoal(EctoSlab ectoSlab) {
            this.ectoSlab = ectoSlab;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.ectoSlab.getTarget() == null
                    && (this.ectoSlab.onGround() || this.ectoSlab.isInWater() || this.ectoSlab.isInLava() || this.ectoSlab.hasEffect(MobEffects.LEVITATION))
                    && this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl;
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.ectoSlab.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.ectoSlab.getRandom().nextInt(360);
            }

            if (this.ectoSlab.getMoveControl() instanceof EctoSlabMoveControl control) control.setDirection(this.chosenDegrees, false);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {

        public static void warnParticle(EctoSlab ectoSlab, Level level, Vec3 pos) {
            Vec3 direction = new Vec3(0.0, 1.0, 0.0);
            int delay = 0;
            for (int i = 0; i < 3; i++) {
                WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL.get())
                        .setFullBrightLighting()
                        .setColorData(ColorParticleData.create(new Color(0x72F4FF)).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1.0f).setEasing(Easing.SINE_OUT).build())
                        .setBehavior(DirectionalParticleBehavior.directional(direction))
                        .setTransparencyData(GenericParticleData.create(0.7f, 0.0f).build())
                        .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                        .setLifetime(20)
                        .disableNoClip()
                        .setLifeDelay(delay)
                        .addTickActor(actor -> {
                            if (ectoSlab.isBurrowed()) actor.setPos(ectoSlab.getX(), ectoSlab.getY() + 0.1, ectoSlab.getZ());
                        })
                        .spawn(level, pos.x, pos.y - 1.5, pos.z);
                delay += 7;
            }
        }

        public static void burrowedParticle(EctoSlab ectoSlab, Level level, RandomSource random) {
            var parX = ectoSlab.getRandomX(0.5f);
            var parZ = ectoSlab.getRandomZ(0.5f);
            WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                    .setScaleData(GenericParticleData.create(0.0f, 0.28f, 0.0f).build())
                    .setTransparencyData(GenericParticleData.create(0.5f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setLifetime(random.nextInt(20))
                    .disableNoClip()
                    .setGravity(0f)
                    .setColorData(ColorParticleData.create(new Color(0x72F4FF)).build())
                    .setMotion(0.0, 0.26, 0.0)
                    .spawn(level, parX, ectoSlab.getY(), parZ);
        }
    }
}
