package net.jadenxgamer.netherexp.mixin.brewing;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandMenu.PotionSlot.class)
public class PotionSlotMixin {

    @Inject(
            method = "mayPlaceItem",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$changeMatches(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(JNEItems.ANTIDOTE.get())) {
            cir.setReturnValue(true);
        }
    }
}
