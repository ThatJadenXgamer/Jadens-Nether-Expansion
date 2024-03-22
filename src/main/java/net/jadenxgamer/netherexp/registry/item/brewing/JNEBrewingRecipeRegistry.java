package net.jadenxgamer.netherexp.registry.item.brewing;

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
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientPotion(Potions.WATER), null), (new BrewingIngredientItem(JNEItems.WARPED_WART)), JNEItems.ANTIDOTE, JNEAntidotes.AWKWARD()));

        // INACTIVE
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.HONEYCOMB)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_SWIFTNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.COOKIE)), JNEItems.ANTIDOTE, JNEAntidotes.SLOWNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.RAW_COPPER)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_STRENGTH()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.GRAVEL)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_JUMP_BOOST()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.BLUE_ICE)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_REGENERATION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.FIRE_CHARGE)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_FIRE_RESISTANCE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.SLIME_BALL)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_WATER_BREATHING()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.QUARTZ)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_INVISIBILITY()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.BEETROOT)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_WEAKNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.CHARCOAL)), JNEItems.ANTIDOTE, JNEAntidotes.INACTIVE_POISON()));

        // ANTIDOTES
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_SWIFTNESS()), (new BrewingIngredientPotion(Potions.SWIFTNESS)), JNEItems.ANTIDOTE, JNEAntidotes.SWIFTNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_SLOWNESS()), (new BrewingIngredientPotion(Potions.SLOWNESS)), JNEItems.ANTIDOTE, JNEAntidotes.SLOWNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_STRENGTH()), (new BrewingIngredientPotion(Potions.STRENGTH)), JNEItems.ANTIDOTE, JNEAntidotes.STRENGTH()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_JUMP_BOOST()), (new BrewingIngredientPotion(Potions.LEAPING)), JNEItems.ANTIDOTE, JNEAntidotes.JUMP_BOOST()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_REGENERATION()), (new BrewingIngredientPotion(Potions.REGENERATION)), JNEItems.ANTIDOTE, JNEAntidotes.REGENERATION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_FIRE_RESISTANCE()), (new BrewingIngredientPotion(Potions.FIRE_RESISTANCE)), JNEItems.ANTIDOTE, JNEAntidotes.FIRE_RESISTANCE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_WATER_BREATHING()), (new BrewingIngredientPotion(Potions.WATER_BREATHING)), JNEItems.ANTIDOTE, JNEAntidotes.WATER_BREATHING()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INVISIBILITY()), (new BrewingIngredientPotion(Potions.INVISIBILITY)), JNEItems.ANTIDOTE, JNEAntidotes.INVISIBILITY()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_WEAKNESS()), (new BrewingIngredientPotion(Potions.WEAKNESS)), JNEItems.ANTIDOTE, JNEAntidotes.WEAKNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.INACTIVE_POISON()), (new BrewingIngredientPotion(Potions.POISON)), JNEItems.ANTIDOTE, JNEAntidotes.POISON()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.GLASS)), JNEItems.ANTIDOTE, JNEAntidotes.RESISTANCE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.GOLDEN_APPLE)), JNEItems.ANTIDOTE, JNEAntidotes.ABSORPTION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.PRISMARINE_SHARD)), JNEItems.ANTIDOTE, JNEAntidotes.HASTE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.CLOCK)), JNEItems.ANTIDOTE, JNEAntidotes.MINING_FATIGUE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.ECHO_SHARD)), JNEItems.ANTIDOTE, JNEAntidotes.DARKNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.CHORUS_FRUIT)), JNEItems.ANTIDOTE, JNEAntidotes.LEVITATION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), JNEAntidotes.AWKWARD()), (new BrewingIngredientItem(Items.ROTTEN_FLESH)), JNEItems.ANTIDOTE, JNEAntidotes.HUNGER()));
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
