package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.client.sound.LoopedEntityBoundSoundInstance;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNEDamageTypes;
import net.jadenxgamer.netherexp.registry.*;
import net.jadenxgamer.netherexp.util.AdvancementGranter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.util.ArrayList;
import java.util.List;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class WillOWisp extends ThrowableItemProjectile {
    private static final float INITIAL_SPEED_BPS = 0.1f; // the blocks per second it starts at
    private static final float MAX_SPEED_BPS = 16.0f; // the maximum blocks pet second it can speed up to
    private static final float ACCELERATION_TIME = 5.0f; // the rate of acceleration for it to go from initial -> max
    private static final float VELOCITY_SMOOTHING = 0.3f; // smoothing of movement velocity
    private static final int MAX_LIFETIME_TICKS = 300; // pretty self-explanatory

    private static final float INITIAL_SPEED_PER_TICK = INITIAL_SPEED_BPS / 20.0f;
    private static final float MAX_SPEED_PER_TICK = MAX_SPEED_BPS / 20.0f;

    // the turn curve changes depending on acceleration time to ensure the particle is still like somewhat accurate regardless of speed
    private static final float[][] BASE_TURN_CURVE = {
            {0.0f, 1.80f}, // Start
            {0.2f, 2.00f},
            {0.4f, 2.20f},
            {0.6f, 2.40f},
            {0.8f, 2.60f},
            {1.0f, 2.80f}  // End
    };

    private static final List<TurnPoint> STATIC_TURN_CURVE = new ArrayList<>();

    static {
        for (float[] point : BASE_TURN_CURVE) {
            float time = point[0] * ACCELERATION_TIME;
            float turnFactor = point[1];
            STATIC_TURN_CURVE.add(new TurnPoint(time, turnFactor));
        }
        STATIC_TURN_CURVE.sort((a, b) -> Float.compare(a.time, b.time));
    }

    public final AnimationState loopAnimation = new AnimationState();
    private LivingEntity target;
    private float currentSpeed;
    private int damage = 3;
    private SoundInstance soundInstance;
    private Vec3 currentDirection;
    private Vec3 smoothedDirection;
    private Vec3 lastParticlePos;
    private double distanceSinceLastParticle = 0.0;
    private boolean particleTrailInitialized = false;
    private float manoeuvrability = ((float) JNEConfigs.GENERIC_WILL_O_WISP_MANEUVERABILITY.getAsDouble());

    public WillOWisp(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        initialize();
    }

    public WillOWisp(LivingEntity shooter, Level level, LivingEntity target) {
        this(shooter, level, target, shooter.getX(), shooter.getY(), shooter.getZ(), 3);
    }

    public WillOWisp(LivingEntity shooter, Level level, LivingEntity target, double x, double y, double z, int damage) {
        super(JNEEntityType.WILL_O_WISP.get(), shooter, level);
        this.noPhysics = true;
        this.target = target;
        this.damage = damage;
        this.setPos(x, y, z);
        initialize();
        initializeDirection(shooter);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 40) impactParticle(this.level(), this.position());
        super.handleEntityEvent(id);
    }

    private void initialize() {
        this.currentSpeed = INITIAL_SPEED_PER_TICK;
        this.currentDirection = new Vec3(1, 0, 0);
        this.smoothedDirection = this.currentDirection;
        this.particleTrailInitialized = false;
    }

    private void initializeDirection(LivingEntity shooter) {
        Vec3 lookAngle = shooter.getLookAngle().normalize();

        if (this.target != null && this.target.isAlive()) {
            Vec3 toTarget = new Vec3(
                    this.target.getX() - this.getX(),
                    (this.target.getY() + 1.0) - this.getY(),
                    this.target.getZ() - this.getZ()
            );

            if (toTarget.lengthSqr() > 0.001) {
                this.currentDirection = lookAngle.scale(0.3).add(toTarget.normalize().scale(0.7)).normalize();
            } else {
                this.currentDirection = lookAngle;
            }
        } else {
            this.currentDirection = lookAngle;
        }

        this.smoothedDirection = this.currentDirection;
        this.setDeltaMovement(this.currentDirection.scale(this.currentSpeed));
        updateRotationFromDirection();
    }

    private void updateRotationFromDirection() {
        double horizontalDistance = Math.sqrt(currentDirection.x * currentDirection.x + currentDirection.z * currentDirection.z);
        this.setYRot((float) (Math.atan2(currentDirection.x, currentDirection.z) * (180.0 / Math.PI)));
        this.setXRot((float) (Math.atan2(currentDirection.y, horizontalDistance) * (180.0 / Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getOwner() != null && !this.getOwner().isAlive()) this.impact(this.position());

        float ageInSeconds = tickCount / 20.0f;
        float accelerationRate = (MAX_SPEED_PER_TICK - INITIAL_SPEED_PER_TICK) / (ACCELERATION_TIME * 20);

        if (ageInSeconds < ACCELERATION_TIME) currentSpeed = Math.min(currentSpeed + accelerationRate, MAX_SPEED_PER_TICK);
        if (this.target != null && this.target.isAlive()) updateDirection();

        Vec3 targetVelocity = this.smoothedDirection.scale(this.currentSpeed);
        Vec3 currentVelocity = this.getDeltaMovement();
        Vec3 smoothedVelocity = currentVelocity.scale(1.0f - VELOCITY_SMOOTHING)
                .add(targetVelocity.scale(VELOCITY_SMOOTHING));

        this.setDeltaMovement(smoothedVelocity);
        updateProjectileRotation();

        if (this.level().isClientSide()) {
            if (WILL_O_WISP_PARTICLES.get()) updateParticleTrail();
            if (WILL_O_WISP_SOUNDS.get()) updateSound();
            loopAnimation.startIfStopped(this.tickCount);
        } else if (tickCount > MAX_LIFETIME_TICKS) this.impact(this.position());
    }

    private void updateProjectileRotation() {
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() > 0.001) {
            double horizontalDistance = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
            float newYRot = (float) (Math.atan2(motion.x, motion.z) * (180.0 / Math.PI));
            float newXRot = (float) (Math.atan2(motion.y, horizontalDistance) * (180.0 / Math.PI));

            this.setYRot(lerpProjectileRotation(this.yRotO, newYRot));
            this.setXRot(lerpProjectileRotation(this.xRotO, newXRot));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    private float lerpProjectileRotation(float current, float target) {
        float difference = target - current;
        while (difference < -180.0F) difference += 360.0F;
        while (difference >= 180.0F) difference -= 360.0F;
        return current + difference * 0.3f;
    }

    private void updateSound() {
        if (this.soundInstance == null) {
            this.soundInstance = new LoopedEntityBoundSoundInstance(
                    JNESoundEvents.WILL_O_WISP_AMBIENT.get(), SoundSource.NEUTRAL,
                    0.5f, 1.0f, this, 0);
            Minecraft.getInstance().getSoundManager().play(this.soundInstance);
        }
    }

    private void updateDirection() {
        Vec3 desiredDirection = new Vec3(
                this.target.getX() - this.getX(),
                (this.target.getY() + 1.0) - this.getY(),
                this.target.getZ() - this.getZ()
        );

        if (desiredDirection.lengthSqr() < 0.001) return;

        desiredDirection = desiredDirection.normalize();
        float turnFactor = calculateTurnFactor(tickCount / 20.0f);

        this.currentDirection = currentDirection.scale(1.0f - turnFactor)
                .add(desiredDirection.scale(turnFactor)).normalize();
        this.smoothedDirection = smoothedDirection.scale(1.0f - manoeuvrability)
                .add(currentDirection.scale(manoeuvrability)).normalize();
    }

    private float calculateTurnFactor(float time) {
        if (time >= STATIC_TURN_CURVE.getLast().time) return STATIC_TURN_CURVE.getLast().turnFactor;

        for (int i = 0; i < STATIC_TURN_CURVE.size() - 1; i++) {
            TurnPoint start = STATIC_TURN_CURVE.get(i);
            TurnPoint end = STATIC_TURN_CURVE.get(i + 1);

            if (time >= start.time && time <= end.time) {
                float segmentProgress = (time - start.time) / (end.time - start.time);
                return start.turnFactor + (end.turnFactor - start.turnFactor) * segmentProgress;
            }
        }

        return STATIC_TURN_CURVE.getFirst().turnFactor;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity living) {
            if (wasRedirected(living)) {
                switch (BANSHEE_REDIRECT_STUNS.get()) {
                    case STUN -> ((Banshee) living).setStunTime(JNEConfigs.BANSHEE_STUN_TIMER.get());
                    case INSTAKILL -> living.kill();
                }
                AdvancementGranter.grantPlayersInRadius(level(), this.blockPosition(), JNECriteriaTriggers.BANSHEE_REDIRECT);
            }

            if (living.isBlocking()) {
                this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.SHIELD_BLOCK, SoundSource.NEUTRAL, 1f, 1f);
                living.getUseItem().hurtAndBreak(5, living, LivingEntity.getSlotForHand(living.getUsedItemHand()));
            } else entity.hurt(this.damageSources().source(JNEDamageTypes.WILL_O_WISP, this.getOwner()), damage);
        }
        this.impact(this.position());
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.impact(this.position());
    }

    private void impact(Vec3 impactPos) {
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 40);
            this.level().playSound(null, impactPos.x, impactPos.y, impactPos.z,
                    SoundEvents.MUD_PLACE, SoundSource.NEUTRAL, 1F, 1F);
            this.discard();
        }
    }

    private void impactParticle(Level level, Vec3 pos) {
        impactParticle(level, pos.x, pos.y, pos.z);
    }

    private void impactParticle(Level level, double x, double y, double z) {
        WorldParticleBuilder.create(JNEParticleTypes.WILL_O_WISP_IMPACT.get())
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.create(0).build())
                .setScaleData(GenericParticleData.create(0.595f).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setLifetime(10)
                .enableNoClip()
                .spawn(level, x, y, z);
    }

    private void spawnSpeedAdjustedTrailParticle(double x, double y, double z) {
        int lifetime = Math.max(1, (int)(6.0f / currentSpeed) + level().random.nextInt(-12, 13));
        lifetime = Math.min(lifetime, 100);

        WorldParticleBuilder.create(JNEParticleTypes.WISP.get())
                .setFullBrightLighting()
                .setScaleData(GenericParticleData.create(0.13f).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setLifetime(lifetime)
                .enableNoClip()
                .spawn(level(), x, y, z);
    }

    private void updateParticleTrail() {
        Vec3 currentPos = this.position();
        Vec3 motion = this.getDeltaMovement();

        if (!particleTrailInitialized) {
            lastParticlePos = motion.lengthSqr() > 0.001 ? currentPos.subtract(motion.normalize().scale(0.2)) : currentPos;
            particleTrailInitialized = true;
            return;
        }

        if (lastParticlePos == null) {
            lastParticlePos = currentPos;
            return;
        }

        double distanceMoved = currentPos.distanceTo(lastParticlePos);
        distanceSinceLastParticle += distanceMoved;
        double particleInterval = 0.5 / (1.0 + (currentSpeed / 0.8f) * 2.0);

        while (distanceSinceLastParticle >= particleInterval) {
            distanceSinceLastParticle -= particleInterval;

            double distance = currentPos.distanceTo(lastParticlePos);
            if (distance < 0.001) {
                lastParticlePos = currentPos;
                break;
            }
            Vec3 spawnPos = lastParticlePos.add(currentPos.subtract(lastParticlePos).scale(particleInterval / distance));
            spawnParticleWithOffset(spawnPos, 0.3);
            if (currentSpeed > 0.24f) {
                int clusterCount = (int)((currentSpeed / 0.8f) * 4.0f);
                for (int i = 0; i < clusterCount; i++) spawnParticleWithOffset(spawnPos, 0.15);
            }

            lastParticlePos = spawnPos;
        }

        if (level().random.nextFloat() < 0.3f) spawnSpeedAdjustedTrailParticle(this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5));
    }

    private void spawnParticleWithOffset(Vec3 pos, double offsetRange) {
        double offsetX = (level().random.nextDouble() - 0.5) * offsetRange;
        double offsetY = (level().random.nextDouble() - 0.5) * offsetRange;
        double offsetZ = (level().random.nextDouble() - 0.5) * offsetRange;

        spawnSpeedAdjustedTrailParticle(pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return JNEItems.WILL_O_WISP.get();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    private boolean wasRedirected(LivingEntity hitEntity) {
        Entity owner = this.getOwner();
        return owner != null && hitEntity == owner && hitEntity.getType() == JNEEntityType.BANSHEE.get();
    }

    public float getManoeuvrability() {
        return manoeuvrability;
    }

    public void setManoeuvrability(float manoeuvrability) {
        this.manoeuvrability = manoeuvrability;
    }

    private record TurnPoint(float time, float turnFactor) {}
}