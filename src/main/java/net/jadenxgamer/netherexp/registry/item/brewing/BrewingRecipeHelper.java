package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class BrewingRecipeHelper {
    public static boolean isValidIngredientSlot(ItemStack stack) {
        List<Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>>> recipes = JNEBrewingRecipeRegistry.getRecipes();
        for (Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>> recipe : recipes) {
            if (recipe.getMiddle().matches(stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRecipe(ItemStack input, ItemStack ingredient) {
        List<Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>>> recipes = JNEBrewingRecipeRegistry.getRecipes();
        for (Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>> recipe : recipes) {
            if (recipe.getLeft().getLeft().matches(input) && recipe.getMiddle().matches(ingredient)) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack recipeResultOf(ItemStack input, ItemStack ingredient) {
        List<Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>>> recipes = JNEBrewingRecipeRegistry.getRecipes();
        for (Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>> recipe : recipes) {
            Pair<BrewingIngredient, NbtCompound> recipeInput = recipe.getLeft();
            if (recipeInput.getLeft().matches(input)) {
                // Check if the NBT of input matches the NBT of the recipe
                NbtCompound inputNbt = input.getOrCreateNbt();
                NbtCompound recipeNbt = recipeInput.getRight();
                if (compareNbt(inputNbt, recipeNbt)) {
                    if (recipe.getMiddle().matches(ingredient)) {
                        ItemStack result = recipe.getRight().getLeft().copy();
                        NbtCompound nbt = recipe.getRight().getRight();
                        if (nbt != null) {
                            result.setNbt(nbt);
                        }
                        return result;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean compareNbt(NbtCompound inputNbt, NbtCompound recipeNbt) {
        // Check if both tags are null
        if (inputNbt == null && recipeNbt == null) {
            return true;
        }
        // Check if one of the tags is null
        if (inputNbt == null || recipeNbt == null) {
            return false;
        }
        // Compare the NBT tags
        return inputNbt.equals(recipeNbt);
    }
}
