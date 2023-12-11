package net.jadenxgamer.netherexp.registry.item.potion;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class ModBrewingRecipeRegistry extends BrewingRecipeRegistry {

    private static final List<ModBrewingRecipeRegistry.Recipe<Item>> ANTIDOTE_RECIPES = Lists.newArrayList();

    public static void registerAntidoteRecipe(Item input, Item ingredient, Item output) {
        ANTIDOTE_RECIPES.add(new ModBrewingRecipeRegistry.Recipe<>(input, Ingredient.ofItems(ingredient), output));
    }

    static class Recipe<T> {
        final T input;
        final Ingredient ingredient;
        final T output;

        public Recipe(T input, Ingredient ingredient, T output) {
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
        }
    }
}
