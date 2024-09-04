package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

    @Redirect(
            method = "releaseUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;shootFromRotation(Lnet/minecraft/world/entity/Entity;FFFFF)V")
    )
    private void netherexp$modifyArrowVelocity(AbstractArrow instance, Entity entity, float x, float y, float z, float v, float a) {
        // makes it so arrows travel further if you have the artemis enchantment
        if (entity instanceof Player player) {
            int artemis = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.ARTEMIS.get(), player.getMainHandItem());
            instance.shootFromRotation(entity, x, y, z, v + artemis, a);
        }
    }
}
