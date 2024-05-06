package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class Antidotes {
    public static final List<CompoundTag> ANTIDOTES = new ArrayList<>();

    static {
        ANTIDOTES.add(Antidotes.AWKWARD());
        ANTIDOTES.add(Antidotes.SWIFTNESS());
        ANTIDOTES.add(Antidotes.SLOWNESS());
        ANTIDOTES.add(Antidotes.STRENGTH());
        ANTIDOTES.add(Antidotes.JUMP_BOOST());
        ANTIDOTES.add(Antidotes.REGENERATION());
        ANTIDOTES.add(Antidotes.FIRE_RESISTANCE());
        ANTIDOTES.add(Antidotes.WATER_BREATHING());
        ANTIDOTES.add(Antidotes.INVISIBILITY());
        ANTIDOTES.add(Antidotes.WEAKNESS());
        ANTIDOTES.add(Antidotes.POISON());
        ANTIDOTES.add(Antidotes.RESISTANCE());
        ANTIDOTES.add(Antidotes.ABSORPTION());
        ANTIDOTES.add(Antidotes.HASTE());
        ANTIDOTES.add(Antidotes.MINING_FATIGUE());
        ANTIDOTES.add(Antidotes.DARKNESS());
        ANTIDOTES.add(Antidotes.LEVITATION());
        ANTIDOTES.add(Antidotes.HUNGER());
        ANTIDOTES.add(Antidotes.WITHER());
        ANTIDOTES.add(Antidotes.LUCK());
        ANTIDOTES.add(Antidotes.UNLUCK());
        ANTIDOTES.add(Antidotes.INACTIVE_SWIFTNESS());
        ANTIDOTES.add(Antidotes.INACTIVE_SLOWNESS());
        ANTIDOTES.add(Antidotes.INACTIVE_STRENGTH());
        ANTIDOTES.add(Antidotes.INACTIVE_JUMP_BOOST());
        ANTIDOTES.add(Antidotes.INACTIVE_REGENERATION());
        ANTIDOTES.add(Antidotes.INACTIVE_FIRE_RESISTANCE());
        ANTIDOTES.add(Antidotes.INACTIVE_WATER_BREATHING());
        ANTIDOTES.add(Antidotes.INACTIVE_INVISIBILITY());
        ANTIDOTES.add(Antidotes.INACTIVE_WEAKNESS());
        ANTIDOTES.add(Antidotes.INACTIVE_POISON());
    }

    /*
     * "Antidote" | Used for the Antidote's Name and could be considered an equivalent to it's "Registration"
     * "AntidoteEffect" | Is the MobEffect you'll get once you drink it. OR if the Antidote is Inactive then it's used for the activation tooltip
     * "Inactive" | A Boolean that determines if it's Inactive or not
     * "CustomAntidoteColor" | By Default grabs the color from the MobEffect but if present will override it. normally used for Inactive Antidotes
     * "Duration" | Gets the Duration for the MobEffectInstance
     */

    public static CompoundTag AWKWARD() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "awkward");
        nbt.putInt("CustomAntidoteColor", 3694022);
        return nbt;
    }

    public static CompoundTag INACTIVE_SWIFTNESS() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_swiftness");
        nbt.putString("AntidoteEffect", "minecraft:speed");
        nbt.putInt("CustomAntidoteColor", 7124156);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_SLOWNESS() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_slowness");
        nbt.putString("AntidoteEffect", "minecraft:slowness");
        nbt.putInt("CustomAntidoteColor", 6391508);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_STRENGTH() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_strength");
        nbt.putString("AntidoteEffect", "minecraft:strength");
        nbt.putInt("CustomAntidoteColor", 10261348);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_JUMP_BOOST() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_leaping");
        nbt.putString("AntidoteEffect", "minecraft:jump_boost");
        nbt.putInt("CustomAntidoteColor", 10203046);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_REGENERATION() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_regeneration");
        nbt.putString("AntidoteEffect", "minecraft:regeneration");
        nbt.putInt("CustomAntidoteColor", 8609209);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_FIRE_RESISTANCE() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_fire_resistance");
        nbt.putString("AntidoteEffect", "minecraft:fire_resistance");
        nbt.putInt("CustomAntidoteColor", 12420418);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_WATER_BREATHING() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_water_breathing");
        nbt.putString("AntidoteEffect", "minecraft:water_breathing");
        nbt.putInt("CustomAntidoteColor", 7909826);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_INVISIBILITY() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_invisibility");
        nbt.putString("AntidoteEffect", "minecraft:invisibility");
        nbt.putInt("CustomAntidoteColor", 13029354);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_WEAKNESS() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_weakness");
        nbt.putString("AntidoteEffect", "minecraft:weakness");
        nbt.putInt("CustomAntidoteColor", 4477288);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag INACTIVE_POISON() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "inactive_poison");
        nbt.putString("AntidoteEffect", "minecraft:poison");
        nbt.putInt("CustomAntidoteColor", 6917252);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static CompoundTag SWIFTNESS() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "swiftness");
        nbt.putString("AntidoteEffect", "netherexp:speed_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag SLOWNESS() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "slowness");
        nbt.putString("AntidoteEffect", "netherexp:slowness_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag STRENGTH() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "strength");
        nbt.putString("AntidoteEffect", "netherexp:strength_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag JUMP_BOOST() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "leaping");
        nbt.putString("AntidoteEffect", "netherexp:jump_boost_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag REGENERATION() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "regeneration");
        nbt.putString("AntidoteEffect", "netherexp:regeneration_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag FIRE_RESISTANCE() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "fire_resistance");
        nbt.putString("AntidoteEffect", "netherexp:fire_resistance_immunity");
        nbt.putInt("Duration", 120);
        return nbt;
    }

    public static CompoundTag WATER_BREATHING() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "water_breathing");
        nbt.putString("AntidoteEffect", "netherexp:water_breathing_immunity");
        nbt.putInt("Duration", 120);
        return nbt;
    }

    public static CompoundTag INVISIBILITY() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "invisibility");
        nbt.putString("AntidoteEffect", "netherexp:invisibility_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag WEAKNESS() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "weakness");
        nbt.putString("AntidoteEffect", "netherexp:weakness_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag POISON() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "poison");
        nbt.putString("AntidoteEffect", "netherexp:poison_immunity");
        nbt.putInt("Duration", 600);
        return nbt;
    }

    public static CompoundTag RESISTANCE() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "resistance");
        nbt.putString("AntidoteEffect", "netherexp:resistance_immunity");
        nbt.putInt("Duration", 900);
        return nbt;
    }

    public static CompoundTag ABSORPTION() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "absorption");
        nbt.putString("AntidoteEffect", "netherexp:absorption_immunity");
        nbt.putInt("Duration", 900);
        return nbt;
    }

    public static CompoundTag HASTE() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "haste");
        nbt.putString("AntidoteEffect", "netherexp:haste_immunity");
        nbt.putInt("Duration", 1800);
        return nbt;
    }

    public static CompoundTag MINING_FATIGUE() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "mining_fatigue");
        nbt.putString("AntidoteEffect", "netherexp:mining_fatigue_immunity");
        nbt.putInt("Duration", 1800);
        return nbt;
    }

    public static CompoundTag DARKNESS() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "darkness");
        nbt.putString("AntidoteEffect", "netherexp:darkness_immunity");
        nbt.putInt("Duration", 1800);
        return nbt;
    }

    public static CompoundTag LEVITATION() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "levitation");
        nbt.putString("AntidoteEffect", "netherexp:levitation_immunity");
        nbt.putInt("Duration", 1080);
        return nbt;
    }

    public static CompoundTag HUNGER() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "hunger");
        nbt.putString("AntidoteEffect", "netherexp:hunger_immunity");
        nbt.putInt("Duration", 1080);
        return nbt;
    }

    public static CompoundTag WITHER() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "decay");
        nbt.putString("AntidoteEffect", "netherexp:wither_immunity");
        nbt.putInt("Duration", 900);
        return nbt;
    }

    public static CompoundTag LUCK() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "luck");
        nbt.putString("AntidoteEffect", "netherexp:luck_immunity");
        nbt.putInt("Duration", 900);
        return nbt;
    }

    public static CompoundTag UNLUCK() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "unluck");
        nbt.putString("AntidoteEffect", "netherexp:unluck_immunity");
        nbt.putInt("Duration", 900);
        return nbt;
    }
}
