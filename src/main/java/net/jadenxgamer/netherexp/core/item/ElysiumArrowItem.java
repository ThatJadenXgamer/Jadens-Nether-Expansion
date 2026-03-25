package net.jadenxgamer.netherexp.core.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;

//TODO: Move to ElysiumAPI
public class ElysiumArrowItem extends ArrowItem {

    private final Class<? extends AbstractArrow> arrowClass;
    private final AbstractArrow.Pickup pickup;

    /**
     * A custom arrow item that dynamically creates projectile entities of a specified type using reflection.
     * <p>
     * This class extends {@link ArrowItem} and allows the creation of custom {@link AbstractArrow} subclasses
     * without requiring a dedicated item class for each arrow type. The arrow class to instantiate is provided
     * via the constructor and must implement two specific constructors:
     * <ul>
     *   <li>{@code (Level, LivingEntity, ItemStack, ItemStack)} – used when the arrow is shot by an entity
     *       (via {@link #createArrow(Level, ItemStack, LivingEntity, ItemStack)}).</li>
     *   <li>{@code (Level, double, double, double, ItemStack, ItemStack)} – used when the arrow is placed in the world
     *       (via {@link #asProjectile(Level, Position, ItemStack, Direction)}).</li>
     * </ul>
     * <p>
     * You can also specify the pickup behavior of the arrow created via the {@code pickup} constructor parameter.
     *
     * @see ArrowItem
     * @see AbstractArrow
     */
    public ElysiumArrowItem(Class<? extends AbstractArrow> arrowClass, AbstractArrow.Pickup pickup, Properties properties) {
        super(properties);
        this.arrowClass = arrowClass;
        this.pickup = pickup;
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        try {
            Constructor<? extends AbstractArrow> constructor = arrowClass.getConstructor(
                    Level.class, LivingEntity.class, ItemStack.class, ItemStack.class);
            return constructor.newInstance(level, shooter, ammo.copyWithCount(1), weapon);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create arrow via constructor (Level, LivingEntity, ItemStack, ItemStack) for class: " + arrowClass.getName(), e);
        }
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        try {
            Constructor<? extends AbstractArrow> constructor = arrowClass.getConstructor(
                    Level.class, double.class, double.class, double.class, ItemStack.class, ItemStack.class);
            AbstractArrow arrow = constructor.newInstance(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
            arrow.pickup = pickup;
            return arrow;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create arrow via constructor (Level, double, double, double, ItemStack, ItemStack) for class: " + arrowClass.getName(), e);
        }
    }
}
