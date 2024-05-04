package net.jadenxgamer.netherexp.mixin.brewing;

import net.jadenxgamer.netherexp.registry.item.brewing.BrewingRecipeHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

    @Inject(
            method = "isIngredient",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (BrewingRecipeHelper.isIngredient(stack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "hasMix",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$hasMix(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        if (BrewingRecipeHelper.hasMix(input, ingredient)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "mix",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private static void netherexp$mix(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        if (!cir.getReturnValue().isEmpty()) {
            final ItemStack result = BrewingRecipeHelper.mix(input, ingredient);
            if (!result.isEmpty()) {
                cir.setReturnValue(result);
            }
        }
    }
}
