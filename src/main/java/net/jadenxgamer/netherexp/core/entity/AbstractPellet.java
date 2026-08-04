package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;

import java.awt.*;

import static net.jadenxgamer.netherexp.registry.JNESoundEvents.SHOTGUN_IMPACT;

public abstract class AbstractPellet extends AbstractArrow {

    public final TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(4);
    private boolean projectileBoost = false;

    protected AbstractPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && (this.tickCount > getMaxLife() || this.isInWater())) {
            this.level().broadcastEntityEvent(this, (byte) 40);
            this.hitGroundSound();
            this.discard();
        }
        if (this.level().isClientSide) trailPointBuilder.addTrailPoint(this.position().add(0.0, 0.15, 0.0));
        trailPointBuilder.tickTrailPoints();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (getOwner() != null) {
            target.hurt(this.damageSources().source(getDamageSource(), getOwner()), getDamage());
        } else target.hurt(this.damageSources().source(getDamageSource()), getDamage());
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 40);
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 40);
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    public float getPickRadius() {
        return 0.0f;
    }

    private void triggerExplosion() {
        this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 2.0f, Level.ExplosionInteraction.NONE);
    }

    protected void hitGroundSound() {
        this.playSound(getHitSound(), 0.15f, 1.0f);
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return getHitSound();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 40) Client.hitParticle(this, this.level(), this.position());
        super.handleEntityEvent(id);
    }

    ///////////////////
    // PELLET STUFFS //
    ///////////////////

    protected SoundEvent getHitSound() {
        return SHOTGUN_IMPACT.get();
    }

    protected float getDamage() {
        return 1.0f;
    }

    protected int getMaxLife() {
        return 40;
    }

    protected ResourceKey<DamageType> getDamageSource() {
        return JNEDamageSources.SHOTGUN_PELLET;
    }

    public Color getTrailColor() {
        return new Color(0xFFFFFF);
    }

    public Color getHitColor() {
        return new Color(0xFFFFFF);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {

        private static void hitParticle(AbstractPellet pellet, Level level, Vec3 pos) {
            hitParticle(pellet, level, pos.x, pos.y, pos.z);
        }

        private static void hitParticle(AbstractPellet pellet, Level level, double x, double y, double z) {
            WorldParticleBuilder.create(JNEParticleTypes.PELLET_HIT.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.create(0).build())
                    .setScaleData(GenericParticleData.create(0.195f).build())
                    .setColorData(ColorParticleData.create(pellet.getHitColor()).build())
                    .setTransparencyData(GenericParticleData.create(1).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(Mth.randomBetweenInclusive(level.random, 3, 7))
                    .enableNoClip()
                    .spawn(level, x, y, z);
        }
    }
}