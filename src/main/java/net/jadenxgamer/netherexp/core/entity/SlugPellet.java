package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SlugPellet extends AbstractPellet {

    public SlugPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SlugPellet(double x, double y, double z, Level level) {
        this(JNEEntityType.SLUG_PELLET.get(), level);
        this.setPos(x, y, z);
    }

    public SlugPellet(double x, double y, double z, Level level, Entity owner) {
        this(JNEEntityType.SLUG_PELLET.get(), level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return JNEItems.SLUG_SHOTGUN_SHELL.get().getDefaultInstance();
    }

    @Override
    protected ResourceKey<DamageType> getDamageSource() {
        return JNEDamageSources.SLUG_PELLET;
    }

    @Override
    protected ParticleOptions getHitParticle() {
        return ParticleTypes.FLAME;
    }
}
