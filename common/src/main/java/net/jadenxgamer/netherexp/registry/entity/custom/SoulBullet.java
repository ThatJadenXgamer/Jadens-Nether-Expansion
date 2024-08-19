package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class SoulBullet extends AbstractArrow {
    public SoulBullet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SoulBullet(double d, double e, double f, Level level) {
        this(JNEEntityType.SOUL_BULLET.get(), level);
        this.setPos(d, e, f);
    }

    public SoulBullet(Level level, LivingEntity owner) {
        super(JNEEntityType.SOUL_BULLET.get(), owner, level);
    }

    public SoulBullet(double d, double e, double f, Level level, LivingEntity owner) {
        super(JNEEntityType.SOUL_BULLET.get(), owner, level);
        this.setPos(d, e, f);
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
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.hurt(this.damageSources().source(JNEDamageSources.SOUL_BULLET), 1);
        if (!this.level().isClientSide) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
            this.discard();
        }
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.SOUL_ESCAPE;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        if (!this.level().isClientSide) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
            this.discard();
        }
    }
}
