package net.jadenxgamer.netherexp.registry.entity.custom;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDamageSources;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class ShotgunPellet extends AbstractArrow {

    public ShotgunPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ShotgunPellet(double d, double e, double f, Level level) {
        this(JNEEntityType.SHOTGUN_PELLET.get(), level);
        this.setPos(d, e, f);
    }

    public ShotgunPellet(Level level, LivingEntity owner) {
        super(JNEEntityType.SHOTGUN_PELLET.get(), owner, level);
        this.setOwner(owner);
    }

    public ShotgunPellet(double d, double e, double f, Level level, LivingEntity owner) {
        super(JNEEntityType.SHOTGUN_PELLET.get(), owner, level);
        this.setPos(d, e, f);
        this.setOwner(owner);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && tickCount > 10) {
            this.playSound(getDefaultHitGroundSoundEvent(), 0.5f, 1.0f);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        entity.hurt(this.damageSources().source(JNEDamageSources.SHOTGUN_PELLET), 1);
        if (this.getOwner() != null && entity instanceof Monster monster && monster.getTarget() == null) {
            if (this.getOwner() instanceof Player player && player.getAbilities().instabuild) {
                monster.setTarget(player);
            }
        }
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

    @Override
    public boolean isNoGravity() {
        return true;
    }
}
