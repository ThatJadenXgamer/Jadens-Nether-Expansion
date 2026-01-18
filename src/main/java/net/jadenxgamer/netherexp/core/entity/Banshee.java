package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;
import java.util.EnumSet;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class Banshee extends PossessedMob implements RangedAttackMob {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState shootAnimation = new AnimationState();
    private static final EntityDataAccessor<Integer> STUN_TIME = SynchedEntityData.defineId(Banshee.class, EntityDataSerializers.INT);
    private int attackTime = 0;
    private int idleAnimationTimeout = 0;
    private BlockPos teleportAnchor;
    private int teleportCount = 0;

    public Banshee(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level, NetherExp.idVanilla("blaze"));
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        this.xpReward = 10;
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
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Piglin.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) this.setupAnimationStates();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        var stunTime = getStunTime();
        if (stunTime > 0) setStunTime(--stunTime);

        float yawRot = this.getYHeadRot() * ((float)Math.PI / 180F);
        double forwardX = -Math.sin(yawRot);
        double forwardZ = Math.cos(yawRot);

        for (int i = 0; i < 2; ++i) {
            double x = this.getX() + (-forwardX);
            double y = this.getRandomY() - 0.25;
            double z = this.getZ() + (-forwardZ);

            x += (this.random.nextDouble() - 0.5) * 1.5;
            z += (this.random.nextDouble() - 0.5) * 0.5;

            breathingParticle(level(), random, x, y, z);
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity target = this.getTarget();
        if (target != null && this.canAttack(target)) {
            if (teleportAnchor == null) teleportAnchor = this.blockPosition();
            this.setNoGravity(true);
        } else {
            this.setNoGravity(false);
            teleportAnchor = null;
            teleportCount = 0;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity) {
            this.attackTime += BANSHEE_ATTACK_INTERVAL_STAGGER.get();
            if (BANSHEE_TELEPORTS_AFTER_HIT.get()) this.teleport();
        }
        return super.hurt(source, amount);
    }

    protected void teleport() {
        if (!this.isAlive() || this.level().isClientSide()) return;
        BlockPos targetPos = null;
        var difficulty = level().getDifficulty();
        var teleportInterval = BANSHEE_ANCHOR_INTERVAL.get();
        if (difficulty.equals(Difficulty.EASY)) teleportInterval -= 1;
        else if (difficulty.equals(Difficulty.HARD)) teleportInterval += 1;

        if (teleportCount % teleportInterval == 0) {
            targetPos = teleportAnchor;
        } else {
            int x = teleportAnchor.getX() + this.random.nextInt(13) - 6;
            int y = teleportAnchor.getY() + this.random.nextInt(7) - 3;
            int z = teleportAnchor.getZ() + this.random.nextInt(13) - 6;
            var pos = new BlockPos(x, y, z);
            if (level().getBlockState(pos).isAir()) targetPos = pos;
        }
        if (targetPos == null) return;

        for (int i = 0; i < 12; i++) ((ServerLevel) this.level()).sendParticles(ParticleTypes.SOUL, this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
        this.playSound(JNESoundEvents.BANSHEE_TELEPORT.get(), 1.0f, 1.0f);
        this.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        teleportCount++;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        this.level().broadcastEntityEvent(this, (byte) 58);
        var difficulty = level().getDifficulty();
        float manoeuvrability = (float) BANSHEE_WILL_O_WISP_MANEUVERABILITY.getAsDouble();
        if (MANEUVERABILITY_AFFECTED_BY_DIFFICULTY.get()) {
            if (difficulty.equals(Difficulty.EASY)) manoeuvrability -= 0.05f;
            else if (difficulty.equals(Difficulty.HARD)) manoeuvrability += 0.04f;
        }

        WillOWisp willOWisp = new WillOWisp(this, this.level(), target, this.getX(), this.getY() + 0.5, this.getZ(), 6);
        willOWisp.setOwner(this);
        willOWisp.setManoeuvrability(manoeuvrability);

        this.playSound(JNESoundEvents.BANSHEE_SHOOT.get(), 2.0f,
                (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
        this.level().addFreshEntity(willOWisp);
    }

    //////////
    // DATA //
    //////////

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STUN_TIME, 0);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 58) shootAnimation.start(this.tickCount);
        super.handleEntityEvent(id);
    }

    @Override
    public int apparitionPersonality() {
        return 4;
    }

    @Override
    protected double apparitionUnleashingOdds() {
        return BANSHEE_UNLEASHING_ODDS.get();
    }

    public int getStunTime() {
        return this.entityData.get(STUN_TIME);
    }

    public void setStunTime(int stunTime) {
        var stunSound = new EntityBoundSoundInstance(JNESoundEvents.BANSHEE_STUN.get(), SoundSource.HOSTILE, 1.0f, 1.0f, this, 0);
        if (this.getStunTime() == 0 && stunTime > 0) Minecraft.getInstance().getSoundManager().play(stunSound);
        this.entityData.set(STUN_TIME, stunTime);
    }

    public boolean isStunned() {
        return getStunTime() > 0;
    }

    ////////////////
    // ANIMATIONS //
    ////////////////

    public static void breathingParticle(Level level, RandomSource random, double x, double y, double z) {
        LodestoneWorldParticleType particle = random.nextBoolean() ? JNEParticleTypes.REDUX_DUST_BLOB.get() : JNEParticleTypes.REDUX_DUST_STAR.get();
        var startColor = new Color(0x0aabaf);
        var endColor = new Color(0x203f64);
        WorldParticleBuilder.create(particle)
                .setFullBrightLighting()
                .setScaleData(GenericParticleData.create(0.33f).build())
                .setTransparencyData(GenericParticleData.create(0.65f).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setColorData(ColorParticleData.create(startColor, endColor).setEasing(Easing.SINE_IN_OUT).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setLifetime(20)
                .enableNoClip()
                .spawn(level, x, y, z);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idleAnimation.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return JNESoundEvents.BANSHEE_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return JNESoundEvents.BANSHEE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return JNESoundEvents.BANSHEE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(JNESoundEvents.APPARITION_FLY.get(), 0.15F, 1.0F);
    }

    ////////
    // AI //
    ////////

    static class BansheeAttackGoal extends Goal {
        private final Banshee banshee;

        public BansheeAttackGoal(Banshee banshee) {
            this.banshee = banshee;
            this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.banshee.getTarget();
            return target != null && target.isAlive() && banshee.getStunTime() <= 0;
        }

        public void start() {
            this.banshee.attackTime = 20;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            super.tick();

            --this.banshee.attackTime;
            LivingEntity target = this.banshee.getTarget();
            if (target == null) return;
            this.banshee.getLookControl().setLookAt(target, 10.0f, 10.0f);
            double distanceFromTarget = this.banshee.distanceToSqr(target);
            if (distanceFromTarget < 600.0) {
                if (banshee.attackTime == 0) this.banshee.performRangedAttack(target, 0.0f);
                if (banshee.attackTime <= -20) {
                    banshee.attackTime = (int) (BANSHEE_ATTACK_INTERVAL.get() + Mth.randomBetween(this.banshee.random, 0, BANSHEE_ATTACK_INTERVAL_BONUS.get()));
                    this.banshee.teleport();
                }
            }
        }
    }
}
