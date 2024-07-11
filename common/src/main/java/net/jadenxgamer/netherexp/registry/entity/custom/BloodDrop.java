package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
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

public class BloodDrop extends AbstractArrow {
    public BloodDrop(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BloodDrop(double d, double e, double f, Level level) {
        this(JNEEntityType.BLOOD_DROP.get(), level);
        this.setPos(d, e, f);
    }

    public BloodDrop(Level level, LivingEntity owner) {
        super(JNEEntityType.BLOOD_DROP.get(), owner, level);
    }

    public BloodDrop(double d, double e, double f, Level level, LivingEntity owner) {
        super(JNEEntityType.BLOOD_DROP.get(), owner, level);
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
//            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Entity owner = this.getOwner();
        if (entity == owner && entity instanceof LivingEntity livingEntity) {
            livingEntity.heal(1);
        }
        if (!this.level().isClientSide) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
//            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.discard();
        }
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.HONEYCOMB_WAX_ON;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (!this.level().isClientSide) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.3f, 1.0f);
//            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.discard();
        }
    }
}
