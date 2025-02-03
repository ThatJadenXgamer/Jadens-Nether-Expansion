package net.jadenxgamer.netherexp.forge.mixin.forge;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

    @WrapOperation(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V")
    )
    private void netherexp$modifyArrowVelocity(AbstractArrow instance, Entity entity, float x, float y, float z, float v, float a, Operation<Void> original) {
        // makes it so arrows travel further if you have the artemis enchantment
        if (entity instanceof Player player && EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.ARTEMIS.get(), player.getMainHandItem()) > 0) {
            int artemis = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.ARTEMIS.get(), player.getMainHandItem());
            instance.shootFromRotation(entity, x, y, z, v + artemis, a);
        } else {
            original.call(instance, entity, x, y, z, v, a);
        }
    }
}
