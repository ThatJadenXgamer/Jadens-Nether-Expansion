package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.lang.reflect.Constructor;


public class ShotgunShellItem extends Item {

    private final Class<? extends Projectile> pelletClass;

    /**
     * A custom shotgun shell item that dynamically creates projectile entities of a specified type using reflection.
     * <p>
     * This class is much like {@link ElysiumArrowItem} in-which it allows you to register new shotgun shells
     * without needing to make a dedicated subclass for all of them like vanilla does (:cringe:)
     * <p>
     * The pellet class must have a constructor with the following signature:
     * {@code (double, double, double, Level, LivingEntity)}.
     *
     * @see ShotgunFistItem
     * @see ShotgunPellet
     * @see ElysiumArrowItem
     */
    public ShotgunShellItem(Class<? extends Projectile> pelletClass, Properties properties) {
        super(properties);
        this.pelletClass = pelletClass;
    }

    public Projectile createPellet(Level level, LivingEntity shooter, double x, double y, double z) {
        try {
            Constructor<? extends Projectile> constructor = pelletClass.getConstructor(
                    double.class, double.class, double.class, Level.class, Entity.class);
            return constructor.newInstance(x, y, z, level, shooter);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create pellet via constructor (double, double, double, Level, LivingEntity) for class: " + pelletClass.getName(), e);
        }
    }
}