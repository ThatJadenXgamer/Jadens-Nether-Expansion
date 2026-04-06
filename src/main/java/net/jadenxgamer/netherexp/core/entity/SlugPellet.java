package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.util.BlockCrackTracker;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.awt.*;

public class SlugPellet extends AbstractPellet {

    public SlugPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("unused")
    public SlugPellet(double x, double y, double z, Level level) {
        this(JNEEntityType.SLUG_PELLET.get(), level);
        this.setPos(x, y, z);
    }

    @SuppressWarnings("unused")
    public SlugPellet(double x, double y, double z, Level level, Entity owner) {
        this(JNEEntityType.SLUG_PELLET.get(), level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    @Override
    public Color getTrailColor() {
        return new Color(0xFFAE00);
    }

    @Override
    public Color getHitColor() {
        return new Color(0xFFAE00);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockCrackTracker.onBlockHit(this.level(), result.getBlockPos(), this.level().getBlockState(result.getBlockPos()), JNEConfigs.SLUG_BLOCK_DAMAGE_STRENGTH.get());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return JNEItems.SLUG_SHOTGUN_SHELL.get().getDefaultInstance();
    }

    @Override
    protected ResourceKey<DamageType> getDamageSource() {
        return JNEDamageSources.SLUG_PELLET;
    }
}
