package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityTypes;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SoulBulletEntity extends PersistentProjectileEntity {
    public SoulBulletEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SoulBulletEntity(World world, LivingEntity owner) {
        super(JNEEntityTypes.SOUL_BULLET, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && age > 600) {
            this.playSound(getHitSound(), 0.5f, 1.0f);
            this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient) {
            Entity entity = entityHitResult.getEntity();
            Entity owner = getOwner();
            entity.damage(this.getDamageSources().create(JNEDamageSources.SOUL_BULLET, this, owner), 2);
            this.playSound(getHitSound(), 0.5f, 1.0f);
            this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.discard();
        }
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_SOUL_SAND_BREAK;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.playSound(getHitSound(), 0.5f, 1.0f);
        this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        this.discard();
    }
}
