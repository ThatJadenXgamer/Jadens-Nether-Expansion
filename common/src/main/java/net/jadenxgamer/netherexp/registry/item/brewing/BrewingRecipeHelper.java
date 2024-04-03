package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class BrewingRecipeHelper {
    public static boolean isValidIngredientSlot(ItemStack stack) {
        List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> recipes = JNEPotionRecipe.getRecipes();
        for (Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>> recipe : recipes) {
            if (recipe.getMiddle().matches(stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRecipe(ItemStack input, ItemStack ingredient) {
        List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> recipes = JNEPotionRecipe.getRecipes();
        for (Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>> recipe : recipes) {
            if (recipe.getLeft().getLeft().matches(input) && recipe.getMiddle().matches(ingredient)) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack recipeResultOf(ItemStack input, ItemStack ingredient) {
        List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> recipes = JNEPotionRecipe.getRecipes();
        for (Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>> recipe : recipes) {
            Pair<BrewingIngredient, CompoundTag> recipeInput = recipe.getLeft();
            if (recipeInput.getLeft().matches(input)) {
                // Grabs the NBT Tags from Input and Recipe's Input to check afterwards
                CompoundTag inputNbt = input.getOrCreateTag();
                CompoundTag recipeNbt = recipeInput.getRight();
                if (compareNbt(inputNbt, recipeNbt)) {
                    if (recipe.getMiddle().matches(ingredient)) {
                        ItemStack result = recipe.getRight().getLeft().copy();
                        CompoundTag nbt = recipe.getRight().getRight();
                        if (nbt != null) {
                            result.setTag(nbt);
                        }
                        return result;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean compareNbt(CompoundTag inputNbt, CompoundTag recipeNbt) {
        /*
        * Checks if the recipe's NBT is null
        * which means we aren't checking for NBT, so it'll pass true
        */
        if (recipeNbt == null) {
            return true;
        }
        // Compares the NBT tags
        return inputNbt.equals(recipeNbt);
    }
}
