package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.*;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BlackIcicle extends AbstractArrow {
    public BlackIcicle(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BlackIcicle(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(JNEEntityType.BLACK_ICICLE.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    public BlackIcicle(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(JNEEntityType.BLACK_ICICLE.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(JNEParticleTypes.BLACK_FLAKE.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    private void shatterIcicle() {
        playSound(JNESoundEvents.BLACK_ICE_BREAK.get(), 1.0f, 1.4f);
        if (level() instanceof ServerLevel serverLevel) for (int i = 0; i < 8; i++) serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JNEBlocks.BLACK_ICICLE.get().defaultBlockState()),
                this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        shatterIcicle();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity victim) victim.setTicksFrozen(victim.getTicksFrozen() + JNEConfigs.BLACK_ICE_FREEZING_TICKS.get());
        shatterIcicle();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.15;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(JNEBlocks.BLACK_ICICLE.get());
    }
}