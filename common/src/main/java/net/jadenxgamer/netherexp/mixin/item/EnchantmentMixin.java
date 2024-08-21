package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.QuickChargeEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(
            method = "canEnchant",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void canEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (((Enchantment) (Object) this) instanceof QuickChargeEnchantment) {
            if (stack.is(JNETags.Items.SHOTGUNS)) {
                cir.setReturnValue(true);
            }
        }
    }
}
