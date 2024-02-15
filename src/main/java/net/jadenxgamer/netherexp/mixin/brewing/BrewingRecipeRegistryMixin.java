package net.jadenxgamer.netherexp.mixin.brewing;

import net.jadenxgamer.netherexp.registry.item.brewing.BrewingRecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {

    @Inject(
            method = "isValidIngredient",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$isValidIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (BrewingRecipeHelper.isValidIngredientSlot(stack)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "hasRecipe",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$hasRecipe(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        if (BrewingRecipeHelper.hasRecipe(input, ingredient)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "craft",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private static void netherexp$craft(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        if (!cir.getReturnValue().isEmpty()) {
            final ItemStack result = BrewingRecipeHelper.recipeResultOf(input, ingredient);
            if (!result.isEmpty()) {
                cir.setReturnValue(result);
            }
        }
    }
}
