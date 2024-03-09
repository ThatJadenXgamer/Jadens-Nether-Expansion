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
        NbtCompound inactiveStrength = new NbtCompound();inactiveStrength.putString("Antidote", "inactive_strength");inactiveStrength.putString("AntidoteEffect", "minecraft:strength");inactiveStrength.putInt("CustomAntidoteColor", 10261348);inactiveStrength.putBoolean("Inactive", true);
        NbtCompound inactiveJumpBoost = new NbtCompound();inactiveJumpBoost.putString("Antidote", "inactive_leaping");inactiveJumpBoost.putString("AntidoteEffect", "minecraft:jump_boost");inactiveJumpBoost.putInt("CustomAntidoteColor", 10203046);inactiveJumpBoost.putBoolean("Inactive", true);
        NbtCompound inactiveRegeneration = new NbtCompound();inactiveRegeneration.putString("Antidote", "inactive_regeneration");inactiveRegeneration.putString("AntidoteEffect", "minecraft:regeneration");inactiveRegeneration.putInt("CustomAntidoteColor", 8609209);inactiveRegeneration.putBoolean("Inactive", true);
        NbtCompound inactiveFireResistance = new NbtCompound();inactiveFireResistance.putString("Antidote", "inactive_fire_resistance");inactiveFireResistance.putString("AntidoteEffect", "minecraft:fire_resistance");inactiveFireResistance.putInt("CustomAntidoteColor", 12420418);inactiveFireResistance.putBoolean("Inactive", true);
        NbtCompound inactiveWaterBreathing = new NbtCompound();inactiveWaterBreathing.putString("Antidote", "inactive_water_breathing");inactiveWaterBreathing.putString("AntidoteEffect", "minecraft:water_breathing");inactiveWaterBreathing.putInt("CustomAntidoteColor", 7909826);inactiveWaterBreathing.putBoolean("Inactive", true);
        NbtCompound inactiveInvisibility = new NbtCompound();inactiveInvisibility.putString("Antidote", "inactive_invisibility");inactiveInvisibility.putString("AntidoteEffect", "minecraft:invisibility");inactiveInvisibility.putInt("CustomAntidoteColor", 13029354);inactiveInvisibility.putBoolean("Inactive", true);
        NbtCompound inactiveWeakness = new NbtCompound();inactiveWeakness.putString("Antidote", "inactive_weakness");inactiveWeakness.putString("AntidoteEffect", "minecraft:weakness");inactiveWeakness.putInt("CustomAntidoteColor", 4477288);inactiveWeakness.putBoolean("Inactive", true);
        NbtCompound inactivePoison = new NbtCompound();inactivePoison.putString("Antidote", "inactive_poison");inactivePoison.putString("AntidoteEffect", "minecraft:poison");inactivePoison.putInt("CustomAntidoteColor", 6917252);inactivePoison.putBoolean("Inactive", true);

        // ANTIDOTES NBT
        NbtCompound swiftness = new NbtCompound();swiftness.putString("Antidote", "swiftness");swiftness.putString("AntidoteEffect", "netherexp:speed_immunity");
        NbtCompound slowness = new NbtCompound();slowness.putString("Antidote", "slowness");slowness.putString("AntidoteEffect", "netherexp:slowness_immunity");
        NbtCompound strength = new NbtCompound();strength.putString("Antidote", "strength");strength.putString("AntidoteEffect", "netherexp:strength_immunity");
        NbtCompound jumpBoost = new NbtCompound();jumpBoost.putString("Antidote", "leaping");jumpBoost.putString("AntidoteEffect", "netherexp:jump_boost_immunity");
        NbtCompound regeneration = new NbtCompound();regeneration.putString("Antidote", "regeneration");regeneration.putString("AntidoteEffect", "netherexp:regeneration_immunity");
        NbtCompound fireResistance = new NbtCompound();fireResistance.putString("Antidote", "fire_resistance");fireResistance.putString("AntidoteEffect", "netherexp:fire_resistance_immunity");
        NbtCompound waterBreathing = new NbtCompound();waterBreathing.putString("Antidote", "water_breathing");waterBreathing.putString("AntidoteEffect", "netherexp:water_breathing_immunity");
        NbtCompound invisibility = new NbtCompound();invisibility.putString("Antidote", "invisibility");invisibility.putString("AntidoteEffect", "netherexp:invisibility_immunity");
        NbtCompound weakness = new NbtCompound();weakness.putString("Antidote", "weakness");weakness.putString("AntidoteEffect", "netherexp:weakness_immunity");
        NbtCompound poison = new NbtCompound();poison.putString("Antidote", "poison");poison.putString("AntidoteEffect", "netherexp:poison_immunity");
        NbtCompound resistance = new NbtCompound();resistance.putString("Antidote", "resistance");resistance.putString("AntidoteEffect", "netherexp:resistance_immunity");
        NbtCompound absorption = new NbtCompound();absorption.putString("Antidote", "absorption");absorption.putString("AntidoteEffect", "netherexp:absorption_immunity");
        NbtCompound haste = new NbtCompound();haste.putString("Antidote", "haste");haste.putString("AntidoteEffect", "netherexp:haste_immunity");
        NbtCompound miningFatigue = new NbtCompound();miningFatigue.putString("Antidote", "mining_fatigue");miningFatigue.putString("AntidoteEffect", "netherexp:mining_fatigue_immunity");
        NbtCompound darkness = new NbtCompound();darkness.putString("Antidote", "darkness");darkness.putString("AntidoteEffect", "netherexp:darkness_immunity");
        NbtCompound levitation = new NbtCompound();levitation.putString("Antidote", "levitation");levitation.putString("AntidoteEffect", "netherexp:levitation_immunity");
        NbtCompound hunger = new NbtCompound();hunger.putString("Antidote", "hunger");hunger.putString("AntidoteEffect", "netherexp:hunger_immunity");

        // INACTIVE
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.HONEYCOMB)), JNEItems.ANTIDOTE, inactiveSwiftness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.COOKIE)), JNEItems.ANTIDOTE, inactiveSlowness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.RAW_COPPER)), JNEItems.ANTIDOTE, inactiveStrength));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.GRAVEL)), JNEItems.ANTIDOTE, inactiveJumpBoost));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.BLUE_ICE)), JNEItems.ANTIDOTE, inactiveRegeneration));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.FIRE_CHARGE)), JNEItems.ANTIDOTE, inactiveFireResistance));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.SLIME_BALL)), JNEItems.ANTIDOTE, inactiveWaterBreathing));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.QUARTZ)), JNEItems.ANTIDOTE, inactiveInvisibility));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.BEETROOT)), JNEItems.ANTIDOTE, inactiveWeakness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.CHARCOAL)), JNEItems.ANTIDOTE, inactivePoison));

        // ANTIDOTES
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveSwiftness), (new BrewingIngredientPotion(Potions.SWIFTNESS)), JNEItems.ANTIDOTE, swiftness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveSlowness), (new BrewingIngredientPotion(Potions.SLOWNESS)), JNEItems.ANTIDOTE, slowness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveStrength), (new BrewingIngredientPotion(Potions.STRENGTH)), JNEItems.ANTIDOTE, strength));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveJumpBoost), (new BrewingIngredientPotion(Potions.LEAPING)), JNEItems.ANTIDOTE, jumpBoost));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveRegeneration), (new BrewingIngredientPotion(Potions.REGENERATION)), JNEItems.ANTIDOTE, regeneration));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveFireResistance), (new BrewingIngredientPotion(Potions.FIRE_RESISTANCE)), JNEItems.ANTIDOTE, fireResistance));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveWaterBreathing), (new BrewingIngredientPotion(Potions.WATER_BREATHING)), JNEItems.ANTIDOTE, waterBreathing));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveInvisibility), (new BrewingIngredientPotion(Potions.INVISIBILITY)), JNEItems.ANTIDOTE, invisibility));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactiveWeakness), (new BrewingIngredientPotion(Potions.WEAKNESS)), JNEItems.ANTIDOTE, weakness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), inactivePoison), (new BrewingIngredientPotion(Potions.POISON)), JNEItems.ANTIDOTE, poison));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.GLASS)), JNEItems.ANTIDOTE, resistance));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.GOLDEN_APPLE)), JNEItems.ANTIDOTE, absorption));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.PRISMARINE_SHARD)), JNEItems.ANTIDOTE, haste));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.CLOCK)), JNEItems.ANTIDOTE, miningFatigue));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.ECHO_SHARD)), JNEItems.ANTIDOTE, darkness));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.CHORUS_FRUIT)), JNEItems.ANTIDOTE, levitation));
        ANTIDOTE_RECIPES.add(convert(Pair.of(new BrewingIngredientItem(JNEItems.ANTIDOTE), awkward), (new BrewingIngredientItem(Items.ROTTEN_FLESH)), JNEItems.ANTIDOTE, hunger));
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
