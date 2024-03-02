package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potions;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class JNEBrewingRecipeRegistry {
    private static final List<Triple<Pair<BrewingIngredient, NbtCompound>, BrewingIngredient, Pair<ItemStack, NbtCompound>>> ANTIDOTE_RECIPES = new ArrayList<>();

    static {
        /*
        * "Antidote" | Used for the Antidote's Name and could be considered an equivalent to it's "Registration"
        * "AntidoteEffect" | Is the effect you'll get once you drink it. OR if the Antidote is Inactive then it's used for the activation tooltip
        * "Inactive" | A Boolean that determines if it's Inactive or not
        * "CustomAntidoteColor" | By Default grabs the color from the AntidoteEffect but if present will override it. normally used for Inactive Antidotes
        */
        NbtCompound awkward = new NbtCompound();awkward.putString("Antidote", "awkward");awkward.putInt("CustomAntidoteColor", 3694022);
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientPotion(Potions.WATER), null), (new BrewingIngredientItem(JNEItems.WARPED_WART)), JNEItems.ANTIDOTE, awkward));

        // INACTIVE NBT
        NbtCompound inactiveSwiftness = new NbtCompound();inactiveSwiftness.putString("Antidote", "inactive_swiftness");inactiveSwiftness.putString("AntidoteEffect", "minecraft:speed");inactiveSwiftness.putInt("CustomAntidoteColor", 7124156);inactiveSwiftness.putBoolean("Inactive", true);
        NbtCompound inactiveSlowness = new NbtCompound();inactiveSlowness.putString("Antidote", "inactive_slowness");inactiveSlowness.putString("AntidoteEffect", "minecraft:slowness");inactiveSlowness.putInt("CustomAntidoteColor", 6391508);inactiveSlowness.putBoolean("Inactive", true);
        NbtCompound inactiveJumpBoost = new NbtCompound();inactiveJumpBoost.putString("Antidote", "inactive_leaping");inactiveJumpBoost.putString("AntidoteEffect", "minecraft:jump_boost");inactiveJumpBoost.putInt("CustomAntidoteColor", 10203046);inactiveJumpBoost.putBoolean("Inactive", true);

        // ANTIDOTES NBT
        NbtCompound swiftness = new NbtCompound();swiftness.putString("Antidote", "swiftness");swiftness.putString("AntidoteEffect", "netherexp:speed_immunity");
        NbtCompound slowness = new NbtCompound();slowness.putString("Antidote", "slowness");slowness.putString("AntidoteEffect", "netherexp:slowness_immunity");
        NbtCompound jumpBoost = new NbtCompound();jumpBoost.putString("Antidote", "leaping");jumpBoost.putString("AntidoteEffect", "netherexp:jump_boost_immunity");
        NbtCompound resistance = new NbtCompound();resistance.putString("Antidote", "resistance");resistance.putString("AntidoteEffect", "netherexp:resistance_immunity");

        // INACTIVE
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.HONEYCOMB)), JNEItems.ANTIDOTE, inactiveSwiftness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.COOKIE)), JNEItems.ANTIDOTE, inactiveSlowness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.GRAVEL)), JNEItems.ANTIDOTE, inactiveJumpBoost));

        // ANTIDOTES
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveSwiftness), (new BrewingIngredientPotion(Potions.SWIFTNESS)), JNEItems.ANTIDOTE, swiftness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveSlowness), (new BrewingIngredientPotion(Potions.SLOWNESS)), JNEItems.ANTIDOTE, slowness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveJumpBoost), (new BrewingIngredientPotion(Potions.LEAPING)), JNEItems.ANTIDOTE, jumpBoost));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.GLASS)), JNEItems.ANTIDOTE, resistance));
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
