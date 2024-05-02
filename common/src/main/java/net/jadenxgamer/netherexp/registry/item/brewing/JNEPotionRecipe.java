package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.mixin.brewing.PotionBrewingAccessor;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

public class JNEPotionRecipe {
    private static final List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> ANTIDOTE_RECIPES = new ArrayList<>();

    static {
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientPotion(Potions.WATER), null), (new BrewingIngredientItem(JNEItems.WARPED_WART.get())), JNEItems.ANTIDOTE.get(), Antidotes.AWKWARD()));

        // INACTIVE
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.HONEYCOMB)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_SWIFTNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.COOKIE)), JNEItems.ANTIDOTE.get(), Antidotes.SLOWNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.RAW_COPPER)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_STRENGTH()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.GRAVEL)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_JUMP_BOOST()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.BLUE_ICE)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_REGENERATION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.FIRE_CHARGE)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_FIRE_RESISTANCE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.SLIME_BALL)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_WATER_BREATHING()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.QUARTZ)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_INVISIBILITY()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.BEETROOT)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_WEAKNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.CHARCOAL)), JNEItems.ANTIDOTE.get(), Antidotes.INACTIVE_POISON()));

        // ANTIDOTES
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_SWIFTNESS()), (new BrewingIngredientPotion(Potions.SWIFTNESS)), JNEItems.ANTIDOTE.get(), Antidotes.SWIFTNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_SLOWNESS()), (new BrewingIngredientPotion(Potions.SLOWNESS)), JNEItems.ANTIDOTE.get(), Antidotes.SLOWNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_STRENGTH()), (new BrewingIngredientPotion(Potions.STRENGTH)), JNEItems.ANTIDOTE.get(), Antidotes.STRENGTH()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_JUMP_BOOST()), (new BrewingIngredientPotion(Potions.LEAPING)), JNEItems.ANTIDOTE.get(), Antidotes.JUMP_BOOST()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_REGENERATION()), (new BrewingIngredientPotion(Potions.REGENERATION)), JNEItems.ANTIDOTE.get(), Antidotes.REGENERATION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_FIRE_RESISTANCE()), (new BrewingIngredientPotion(Potions.FIRE_RESISTANCE)), JNEItems.ANTIDOTE.get(), Antidotes.FIRE_RESISTANCE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_WATER_BREATHING()), (new BrewingIngredientPotion(Potions.WATER_BREATHING)), JNEItems.ANTIDOTE.get(), Antidotes.WATER_BREATHING()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INVISIBILITY()), (new BrewingIngredientPotion(Potions.INVISIBILITY)), JNEItems.ANTIDOTE.get(), Antidotes.INVISIBILITY()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_WEAKNESS()), (new BrewingIngredientPotion(Potions.WEAKNESS)), JNEItems.ANTIDOTE.get(), Antidotes.WEAKNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.INACTIVE_POISON()), (new BrewingIngredientPotion(Potions.POISON)), JNEItems.ANTIDOTE.get(), Antidotes.POISON()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.GLASS)), JNEItems.ANTIDOTE.get(), Antidotes.RESISTANCE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.GOLDEN_APPLE)), JNEItems.ANTIDOTE.get(), Antidotes.ABSORPTION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.PRISMARINE_SHARD)), JNEItems.ANTIDOTE.get(), Antidotes.HASTE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.CLOCK)), JNEItems.ANTIDOTE.get(), Antidotes.MINING_FATIGUE()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.ECHO_SHARD)), JNEItems.ANTIDOTE.get(), Antidotes.DARKNESS()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.CHORUS_FRUIT)), JNEItems.ANTIDOTE.get(), Antidotes.LEVITATION()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.ROTTEN_FLESH)), JNEItems.ANTIDOTE.get(), Antidotes.HUNGER()));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE.get()), Antidotes.AWKWARD()), (new BrewingIngredientItem(Items.DIAMOND)), JNEItems.ANTIDOTE.get(), Antidotes.WITHER()));
    }

    public static List<Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>>> getRecipes() {
        return ANTIDOTE_RECIPES;
    }

    private static Triple<Pair<BrewingIngredient, CompoundTag>, BrewingIngredient, Pair<ItemStack, CompoundTag>> convert(Pair<BrewingIngredient, CompoundTag> input, BrewingIngredient ingredient, Item output, CompoundTag nbt) {
        ItemStack stack = output.getDefaultInstance();
        if (nbt != null) {
            stack.setTag(nbt);
        }
        return new Triple<>() {
            @Override
            public Pair<BrewingIngredient, CompoundTag> getLeft() {
                return input;
            }

            @Override
            public BrewingIngredient getMiddle() {
                return ingredient;
            }

            @Override
            public Pair<ItemStack, CompoundTag> getRight() {
                return Pair.of(stack, nbt);
            }
        };
    }

    public static void addInvokerPotionRecipes() {
        PotionBrewingAccessor.netherexp$addMix(Potions.AWKWARD, JNEItems.FOGGY_ESSENCE.get(), JNEPotions.FOGSIGHT.get());
        PotionBrewingAccessor.netherexp$addMix(JNEPotions.FOGSIGHT.get(), Items.REDSTONE, JNEPotions.LONG_FOGSIGHT.get());
    }
}
