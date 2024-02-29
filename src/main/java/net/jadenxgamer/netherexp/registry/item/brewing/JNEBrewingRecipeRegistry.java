package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.*;
import net.minecraft.potion.Potions;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class JNEBrewingRecipeRegistry {
    private static final List<Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>>> ANTIDOTE_RECIPES = new ArrayList<>();

    static {
        NbtCompound awkward = new NbtCompound();
        awkward.putString("AntidoteEffects", "minecraft:empty");
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientPotion(Potions.WATER), null), (new BrewingIngredientItem(JNEItems.WARPED_WART)), JNEItems.AWKWARD_ANTIDOTE, awkward));
        NbtCompound inactiveSpeed = new NbtCompound();
        inactiveSpeed.putString("AntidoteEffects", "minecraft:speed");
        inactiveSpeed.putBoolean("Inactive", true);
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.AWKWARD_ANTIDOTE), awkward), (new BrewingIngredientItem(Items.HONEYCOMB)), JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE, inactiveSpeed));
        NbtCompound speed = new NbtCompound();
        speed.putString("AntidoteEffects", "minecraft:speed");
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE), inactiveSpeed), (new BrewingIngredientPotion(Potions.SWIFTNESS)), JNEItems.SWIFTNESS_ANTIDOTE, speed));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE), inactiveSpeed), (new BrewingIngredientPotion(Potions.LONG_SWIFTNESS)), JNEItems.SWIFTNESS_ANTIDOTE, speed));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.INACTIVE_SWIFTNESS_ANTIDOTE), inactiveSpeed), (new BrewingIngredientPotion(Potions.STRONG_SWIFTNESS)), JNEItems.SWIFTNESS_ANTIDOTE, speed));
    }

    public static List<Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>>> getRecipes() {
        return ANTIDOTE_RECIPES;
    }

    private static Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>> convert(Pair<BrewingIngredient, NbtCompound> input, BrewingIngredient ingredient, Item output, NbtCompound nbt) {
        ItemStack stack = output.getDefaultStack();
        if (nbt != null) {
            stack.setNbt(nbt);
        }
        return new Triple<>() {
            @Override
            public Pair<BrewingIngredient, NbtCompound> getLeft() {
                return input;
            }

            @Override
            public BrewingIngredient getMiddle() {
                return ingredient;
            }

            @Override
            public Pair<ItemStack, NbtCompound> getRight() {
                return Pair.of(stack, nbt);
            }
        };
    }
}
