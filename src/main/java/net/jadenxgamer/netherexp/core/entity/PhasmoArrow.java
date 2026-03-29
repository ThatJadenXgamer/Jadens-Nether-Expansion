package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class PhasmoArrow extends AbstractArrow {
    public PhasmoArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public PhasmoArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(JNEEntityType.PHASMO_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    public PhasmoArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(JNEEntityType.PHASMO_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }

        if (!this.level().getBlockState(this.blockPosition()).isAir()) {
            Vec3 delta = this.getDeltaMovement();
            this.setDeltaMovement(delta.x, delta.y, delta.z);
        }
    }

    @Override
    public boolean isNoGravity() {
        return !this.level().getBlockState(this.blockPosition()).isAir();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(JNEItems.PHASMO_ARROW.get());
    }
}
