package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPellet extends AbstractArrow {

    protected AbstractPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount > getMaxLife()) {
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (getOwner() != null) {
            target.hurt(this.damageSources().source(getDamageSource(), getOwner()), getDamage());
        } else target.hurt(this.damageSources().source(getDamageSource()), getDamage());
        spawnHitParticle();
        if (!this.level().isClientSide) {
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        spawnHitParticle();
        if (!this.level().isClientSide) {
            this.hitGroundSound();
            this.discard();
        }
    }

    protected void spawnHitParticle() {
        this.level().addParticle(getHitParticle(),
                true, this.getX(), this.getY(), this.getZ(),
                0.0f, 0.0f, 0.0f);
    }

    protected void hitGroundSound() {
        this.playSound(getHitSound(), 0.3f, 1.0f);
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return getHitSound();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    ///////////////////
    // PELLET STUFFS //
    ///////////////////

    protected SoundEvent getHitSound() {
        return SoundEvents.SOUL_ESCAPE.value();
    }

    protected float getDamage() {
        return 1.0f;
    }

    protected int getMaxLife() {
        return 10;
    }

    protected ParticleOptions getHitParticle() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

    protected ResourceKey<DamageType> getDamageSource() {
        return JNEDamageSources.SHOTGUN_PELLET;
    }
}