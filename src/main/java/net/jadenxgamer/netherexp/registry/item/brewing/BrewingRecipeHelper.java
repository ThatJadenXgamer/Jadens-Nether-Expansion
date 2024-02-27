package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class BrewingRecipeHelper {
    public static boolean isValidIngredientSlot(ItemStack stack) {
        List<Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>>> recipes = JNEBrewingRecipeRegistry.getRecipes();
        for (Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>> recipe : recipes) {
            if (recipe.getMiddle().matches(stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRecipe(ItemStack input, ItemStack ingredient) {
        List<Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>>> recipes = JNEBrewingRecipeRegistry.getRecipes();
        for (Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>> recipe : recipes) {
            if (recipe.getLeft().getLeft().matches(input) && recipe.getMiddle().matches(ingredient)) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack recipeResultOf(ItemStack input, ItemStack ingredient) {
        List<Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>>> recipes = JNEBrewingRecipeRegistry.getRecipes();
        for (Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>> recipe : recipes) {
            if (recipe.getLeft().getLeft().matches(input) && recipe.getMiddle().matches(ingredient)) {
                ItemStack result = recipe.getRight().getLeft().copy();
                NbtCompound nbt = recipe.getRight().getMiddle();
                if (nbt != null) {
                    result.setNbt(nbt);
                }
                return result;
            }
        }
        return ItemStack.EMPTY;
    }
}
