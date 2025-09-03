package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class ShotgunPellet extends AbstractArrow {

    public ShotgunPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ShotgunPellet(double x, double y, double z, Level level) {
        this(JNEEntityType.SHOTGUN_PELLET.get(), level);
        this.setPos(x, y, z);
    }
    public ShotgunPellet(double x, double y, double z, Level level, Entity owner) {
        this(JNEEntityType.SHOTGUN_PELLET.get(), level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && tickCount > 10) {
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (getOwner() != null) {
            entity.hurt(this.damageSources().source(JNEDamageSources.SHOTGUN_PELLET, getOwner()), 1);
        } else entity.hurt(this.damageSources().source(JNEDamageSources.SHOTGUN_PELLET), 1);
        this.level().addParticle(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), true, this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f, 0.0f);
        if (!this.level().isClientSide) {
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.level().addParticle(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), true, this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f, 0.0f);
        if (!this.level().isClientSide) {
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.SOUL_ESCAPE.value();
    }

    private void hitGroundSound() {
        this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}
