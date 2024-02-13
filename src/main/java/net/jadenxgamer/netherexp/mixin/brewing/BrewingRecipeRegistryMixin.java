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

    /*
     * Now you might be wondering "Why are you removing a recipe and registering it again in ModPotions"
     * The method used to change the Ingredient works BUT only for Vanilla Items for whatever reason
     * when a Modded Ingredient is added the game freaks out and a bunch of stuff breaks
     * Including but not limited to (Breaking Rendering, ItemGroups, Chunk Loading and various Invisible Blocks)
     *
     * Again I have NO IDEA why this happens only when Modded Items are used
     * or how ANY Of the above issues occur because of a Brewing Recipe Mixin
     * Registering it separately from ModPotions seems to be the only workaround
     * But if you know a better way to do this, be sure to contact me through Discord
    */

    //TODO: Uncomment this brewing recipe code whenever Warphopper properly gets added
    /*
    @ModifyArg(
            method = "registerDefaults",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/BrewingRecipeRegistry;registerPotionRecipe(Lnet/minecraft/potion/Potion;Lnet/minecraft/item/Item;Lnet/minecraft/potion/Potion;)V", ordinal = 12)
    )
    private static Item netherexp$changeInvisibility(Item item) {
        // Removes Recipe, so it could be re-added in ModPotions
        return (Items.AIR);
    }

    @ModifyArg(
            method = "registerDefaults",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/BrewingRecipeRegistry;registerPotionRecipe(Lnet/minecraft/potion/Potion;Lnet/minecraft/item/Item;Lnet/minecraft/potion/Potion;)V", ordinal = 13)
    )
    private static Item netherexp$changeLongInvisibility(Item item) {
        // Removes Recipe because it can no longer be inverted
        return (Items.AIR);
    }
     */
}
