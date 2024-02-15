package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class ModBrewingRecipeRegistry {
    private static final List<Triple<BrewingIngredient, BrewingIngredient, ItemStack>> ANTIDOTE_RECIPES = new ArrayList<>();

    static {
        ANTIDOTE_RECIPES.add(convert(new BrewingIngredientPotion(Potions.WATER), (new BrewingIngredientItem(ModItems.WARPED_WART)), ModItems.AWKWARD_ANTIDOTE));
        ANTIDOTE_RECIPES.add(convert(new BrewingIngredientItem(ModItems.AWKWARD_ANTIDOTE), (new BrewingIngredientItem(Items.HONEYCOMB)), ModItems.INACTIVE_SWIFTNESS_ANTIDOTE));
        ANTIDOTE_RECIPES.add(convert(new BrewingIngredientItem(ModItems.INACTIVE_SWIFTNESS_ANTIDOTE), (new BrewingIngredientPotion(Potions.SWIFTNESS)), ModItems.SWIFTNESS_ANTIDOTE));
        ANTIDOTE_RECIPES.add(convert(new BrewingIngredientItem(ModItems.INACTIVE_SWIFTNESS_ANTIDOTE), (new BrewingIngredientPotion(Potions.LONG_SWIFTNESS)), ModItems.SWIFTNESS_ANTIDOTE));
        ANTIDOTE_RECIPES.add(convert(new BrewingIngredientItem(ModItems.INACTIVE_SWIFTNESS_ANTIDOTE), (new BrewingIngredientPotion(Potions.STRONG_SWIFTNESS)), ModItems.SWIFTNESS_ANTIDOTE));
    }

    public static List<Triple<BrewingIngredient, BrewingIngredient, ItemStack>> getRecipes() {
        return ANTIDOTE_RECIPES;
    }

    private static Triple<BrewingIngredient, BrewingIngredient, ItemStack> convert(BrewingIngredient input, BrewingIngredient ingredient, Item output) {
        return new Triple<>() {
            @Override
            public BrewingIngredient getLeft() {
                return input;
            }

            @Override
            public BrewingIngredient getMiddle() {
                return ingredient;
            }

            @Override
            public ItemStack getRight() {
                return output.getDefaultStack();
            }
        };
    }
}
