package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class PhasmoPellet extends AbstractPellet {

    public PhasmoPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("unused")
    public PhasmoPellet(double x, double y, double z, Level level) {
        this(JNEEntityType.PHASMO_PELLET.get(), level);
        this.setPos(x, y, z);
    }

    @SuppressWarnings("unused")
    public PhasmoPellet(double x, double y, double z, Level level, Entity owner) {
        this(JNEEntityType.PHASMO_PELLET.get(), level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().getBlockState(this.blockPosition()).isAir()) {
            Vec3 delta = this.getDeltaMovement();
            this.setDeltaMovement(delta.x, delta.y, delta.z);
        }
    }

    @Override
    public Color getTrailColor() {
        return new Color(0x132A34);
    }

    @Override
    public Color getHitColor() {
        return new Color(0x256261);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return JNEItems.PHASMO_SHOTGUN_SHELL.get().getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
    }
}
