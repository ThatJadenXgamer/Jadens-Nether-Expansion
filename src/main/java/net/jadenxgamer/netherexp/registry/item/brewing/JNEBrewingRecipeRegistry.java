package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.*;
import net.minecraft.potion.Potions;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class JNEBrewingRecipeRegistry {
    private static final List<Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>>> ANTIDOTE_RECIPES = new ArrayList<>();

    static {
        NbtCompound awkward = new NbtCompound();
        awkward.putString("AntidoteEffects", "netherexp:empty");
        ANTIDOTE_RECIPES.add(convert(Triple.of(new BrewingIngredientPotion(Potions.WATER), null, 0), (new BrewingIngredientItem(JNEItems.WARPED_WART)), JNEItems.AWKWARD_ANTIDOTE, awkward, 0));
        NbtCompound inactiveSpeed = new NbtCompound();
        awkward.putString("AntidoteEffects", "netherexp:speed");
        awkward.putBoolean("Inactive", true);
        ANTIDOTE_RECIPES.add(convert(Triple.of(new BrewingIngredientItem(JNEItems.AWKWARD_ANTIDOTE), awkward, 0), (new BrewingIngredientItem(Items.HONEYCOMB)), JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE, inactiveSpeed, 0));
        NbtCompound speed = new NbtCompound();
        awkward.putString("AntidoteEffects", "netherexp:speed");
        ANTIDOTE_RECIPES.add(convert(Triple.of(new BrewingIngredientItem(JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE), inactiveSpeed, 0), (new BrewingIngredientPotion(Potions.SWIFTNESS)), JNEItems.SWIFTNESS_ANTIDOTE, speed, 0));
        ANTIDOTE_RECIPES.add(convert(Triple.of(new BrewingIngredientItem(JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE), inactiveSpeed, 0), (new BrewingIngredientPotion(Potions.LONG_SWIFTNESS)), JNEItems.SWIFTNESS_ANTIDOTE, speed, 0));
        ANTIDOTE_RECIPES.add(convert(Triple.of(new BrewingIngredientItem(JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE), inactiveSpeed, 0), (new BrewingIngredientPotion(Potions.STRONG_SWIFTNESS)), JNEItems.SWIFTNESS_ANTIDOTE, speed, 0));
    }

    public static List<Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>>> getRecipes() {
        return ANTIDOTE_RECIPES;
    }

    private static Triple<Triple<BrewingIngredient, NbtCompound, Integer>, BrewingIngredient, Triple<ItemStack, NbtCompound, Integer>> convert(Triple<BrewingIngredient, NbtCompound, Integer> input, BrewingIngredient ingredient, Item output, NbtCompound nbt, int nbtFlag) {
        ItemStack stack = output.getDefaultStack();
        if (nbt != null) {
            stack.setNbt(nbt);
        }
        return new Triple<>() {
            @Override
            public Triple<BrewingIngredient, NbtCompound, Integer> getLeft() {
                return input;
            }

            @Override
            public BrewingIngredient getMiddle() {
                return ingredient;
            }

            @Override
            public Triple<ItemStack, NbtCompound, Integer> getRight() {
                return Triple.of(stack, nbt, nbtFlag);
            }
        };
    }
}
