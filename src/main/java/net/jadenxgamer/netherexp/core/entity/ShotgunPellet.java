package net.jadenxgamer.netherexp.core.entity;

import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShotgunPellet extends AbstractPellet {

    public ShotgunPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public ShotgunPellet(double x, double y, double z, Level level) {
        this(JNEEntityType.SHOTGUN_PELLET.get(), level);
        this.setPos(x, y, z);
    }
    public ShotgunPellet(double x, double y, double z, Level level, Entity owner) {
        this(JNEEntityType.SHOTGUN_PELLET.get(), level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return JNEItems.SHOTGUN_SHELL.get().getDefaultInstance();
    }
}
