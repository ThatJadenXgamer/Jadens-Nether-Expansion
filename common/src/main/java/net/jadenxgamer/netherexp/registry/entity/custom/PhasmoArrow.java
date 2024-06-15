package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class PhasmoArrow extends AbstractArrow {
    public PhasmoArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public PhasmoArrow(Level level, LivingEntity livingEntity) {
        super(JNEEntityType.PHASMO_ARROW.get(), livingEntity, level);
    }

    public PhasmoArrow(Level level, double d, double e, double f) {
        super(JNEEntityType.PHASMO_ARROW.get(), d, e, f, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(JNEItems.PHASMO_ARROW.get());
    }
}
