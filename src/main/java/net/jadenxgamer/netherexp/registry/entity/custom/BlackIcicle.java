package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.BlackIcicleBlock;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BlackIcicle extends AbstractArrow {
    public BlackIcicle(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlackIcicle(Level level, LivingEntity owner) {
        super(JNEEntityType.BLACK_ICICLE.get(), owner, level);
        this.setOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(JNEParticleTypes.TRAIL_BLACK_FLAKE.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide) {
            this.level().levelEvent(2001, this.blockPosition(), BlackIcicleBlock.getId(JNEBlocks.BLACK_ICICLE.get().defaultBlockState()));
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!this.level().isClientSide) {
            Entity entity = pResult.getEntity();
            Entity owner = this.getOwner();
            DamageSource source;
            if (owner == null) {
                source = this.damageSources().arrow(this, this);
            } else {
                source = this.damageSources().arrow(this, owner);
                if (owner instanceof LivingEntity) {
                    ((LivingEntity)owner).setLastHurtMob(entity);
                }
            }
            if (entity.canFreeze()) {
                entity.setTicksFrozen(entity.getTicksFrozen() + JNEConfigs.BLACK_ICICLE_FREEZE_TICKS.get());
            }
            this.level().levelEvent(2001, pResult.getEntity().blockPosition(), BlackIcicleBlock.getId(JNEBlocks.BLACK_ICICLE.get().defaultBlockState()));
            entity.hurt(source, 5);
            this.discard();
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
}
