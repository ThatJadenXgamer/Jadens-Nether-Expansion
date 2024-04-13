package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class SoulBullet extends AbstractArrow {
    public SoulBullet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SoulBullet(double d, double e, double f, Level level) {
        this(JNEEntityType.SOUL_BULLET.get(), level);
        this.setPos(d, e, f);
    }

    public SoulBullet(Level world, LivingEntity owner) {
        super(JNEEntityType.SOUL_BULLET.get(), owner, world);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && tickCount > 600) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.5f, 1.0f);
            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!this.level().isClientSide) {
            Entity entity = entityHitResult.getEntity();
            entity.hurt(level().damageSources().source(JNEDamageSources.SOUL_BULLET), 1);
            this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.discard();
        }
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.FIRE_EXTINGUISH;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.playSound(getDefaultHitGroundSoundEvent(), 0.2f, 1.5f);
        this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        this.discard();
    }
}
