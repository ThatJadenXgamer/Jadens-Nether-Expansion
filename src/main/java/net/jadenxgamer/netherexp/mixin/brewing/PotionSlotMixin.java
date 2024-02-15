package net.jadenxgamer.netherexp.mixin.brewing;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandScreenHandler.PotionSlot.class)
public class PotionSlotMixin {

    @Inject(
            method = "matches",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$changeMatches(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isIn(ModTags.Items.ANTIDOTES)) {
            cir.setReturnValue(true);
        }
    }
}
