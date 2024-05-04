package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class BrewingRecipeHelper {
    public static boolean isIngredient(ItemStack stack) {
        List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> recipes = JNEPotionRecipe.getRecipes();
        for (Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>> recipe : recipes) {
            if (recipe.getMiddle().matches(stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasMix(ItemStack input, ItemStack ingredient) {
        if (input.is(JNEItems.ANTIDOTE.get()) && ingredient.is(Items.GUNPOWDER)) {
            return true;
        }
        List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> recipes = JNEPotionRecipe.getRecipes();
        for (Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>> recipe : recipes) {
            if (recipe.getLeft().getLeft().matches(input) && recipe.getMiddle().matches(ingredient)) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack mix(ItemStack input, ItemStack ingredient) {
        if (input.is(JNEItems.ANTIDOTE.get()) && ingredient.is(Items.GUNPOWDER)) {
            CompoundTag inputNbt = input.getOrCreateTag();
            ItemStack result = JNEItems.GRENADE_ANTIDOTE.get().getDefaultInstance();
            result.setTag(inputNbt);
            return result;
        }
        List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> recipes = JNEPotionRecipe.getRecipes();
        for (Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>> recipe : recipes) {
            Pair<BrewingIngredient, CompoundTag> recipeInput = recipe.getLeft();
            if (recipeInput.getLeft().matches(input)) {
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
        if (recipeNbt == null) {
            return true;
        }
        return inputNbt.equals(recipeNbt);
    }
}
