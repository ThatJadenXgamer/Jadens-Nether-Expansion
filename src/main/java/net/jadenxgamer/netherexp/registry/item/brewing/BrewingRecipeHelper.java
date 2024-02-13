package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class BrewingRecipeHelper {
    public static boolean isValidIngredientSlot(ItemStack stack) {
        List<Triple<BrewingIngredient, BrewingIngredient, ItemStack>> recipes = ModBrewingRecipeRegistry.getRecipes();
        for (Triple<BrewingIngredient, BrewingIngredient, ItemStack> recipe : recipes) {
            if (recipe.getMiddle().matches(stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRecipe(ItemStack input, ItemStack ingredient) {
        List<Triple<BrewingIngredient, BrewingIngredient, ItemStack>> recipes = ModBrewingRecipeRegistry.getRecipes();
        for (Triple<BrewingIngredient, BrewingIngredient, ItemStack> recipe : recipes) {
            if (recipe.getLeft().matches(input) && recipe.getMiddle().matches(ingredient)) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack recipeResultOf(ItemStack input, ItemStack ingredient) {
        List<Triple<BrewingIngredient, BrewingIngredient, ItemStack>> recipes = ModBrewingRecipeRegistry.getRecipes();
        for (Triple<BrewingIngredient, BrewingIngredient, ItemStack> recipe : recipes) {
            if (recipe.getLeft().matches(input) && recipe.getMiddle().matches(ingredient)) {
                return recipe.getRight().getItem().getDefaultStack();
            }
        }
        return ItemStack.EMPTY;
    }
}
