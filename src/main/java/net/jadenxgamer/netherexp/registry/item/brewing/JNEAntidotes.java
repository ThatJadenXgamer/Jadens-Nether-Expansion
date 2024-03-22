package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;

public class JNEAntidotes {
    public static final List<NbtCompound> ANTIDOTES = new ArrayList<>();

    static {
        ANTIDOTES.add(JNEAntidotes.AWKWARD());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_SWIFTNESS());
        ANTIDOTES.add(JNEAntidotes.SWIFTNESS());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_SLOWNESS());
        ANTIDOTES.add(JNEAntidotes.SLOWNESS());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_STRENGTH());
        ANTIDOTES.add(JNEAntidotes.STRENGTH());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_JUMP_BOOST());
        ANTIDOTES.add(JNEAntidotes.JUMP_BOOST());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_REGENERATION());
        ANTIDOTES.add(JNEAntidotes.REGENERATION());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_FIRE_RESISTANCE());
        ANTIDOTES.add(JNEAntidotes.FIRE_RESISTANCE());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_WATER_BREATHING());
        ANTIDOTES.add(JNEAntidotes.WATER_BREATHING());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_INVISIBILITY());
        ANTIDOTES.add(JNEAntidotes.INVISIBILITY());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_WEAKNESS());
        ANTIDOTES.add(JNEAntidotes.WEAKNESS());
        ANTIDOTES.add(JNEAntidotes.INACTIVE_POISON());
        ANTIDOTES.add(JNEAntidotes.POISON());
        ANTIDOTES.add(JNEAntidotes.RESISTANCE());
        ANTIDOTES.add(JNEAntidotes.ABSORPTION());
        ANTIDOTES.add(JNEAntidotes.HASTE());
        ANTIDOTES.add(JNEAntidotes.MINING_FATIGUE());
        ANTIDOTES.add(JNEAntidotes.DARKNESS());
        ANTIDOTES.add(JNEAntidotes.LEVITATION());
        ANTIDOTES.add(JNEAntidotes.HUNGER());
    }

    /*
     * "Antidote" | Used for the Antidote's Name and could be considered an equivalent to it's "Registration"
     * "AntidoteEffect" | Is the effect you'll get once you drink it. OR if the Antidote is Inactive then it's used for the activation tooltip
     * "Inactive" | A Boolean that determines if it's Inactive or not
     * "CustomAntidoteColor" | By Default grabs the color from the AntidoteEffect but if present will override it. normally used for Inactive Antidotes
     */

    public static NbtCompound AWKWARD() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "awkward");
        nbt.putInt("CustomAntidoteColor", 3694022);
        return nbt;
    }

    public static NbtCompound INACTIVE_SWIFTNESS() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_swiftness");
        nbt.putString("AntidoteEffect", "minecraft:speed");
        nbt.putInt("CustomAntidoteColor", 7124156);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_SLOWNESS() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_slowness");
        nbt.putString("AntidoteEffect", "minecraft:slowness");
        nbt.putInt("CustomAntidoteColor", 6391508);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_STRENGTH() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_strength");
        nbt.putString("AntidoteEffect", "minecraft:strength");
        nbt.putInt("CustomAntidoteColor", 10261348);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_JUMP_BOOST() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_leaping");
        nbt.putString("AntidoteEffect", "minecraft:jump_boost");
        nbt.putInt("CustomAntidoteColor", 10203046);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_REGENERATION() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_regeneration");
        nbt.putString("AntidoteEffect", "minecraft:regeneration");
        nbt.putInt("CustomAntidoteColor", 8609209);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_FIRE_RESISTANCE() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_fire_resistance");
        nbt.putString("AntidoteEffect", "minecraft:fire_resistance");
        nbt.putInt("CustomAntidoteColor", 12420418);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_WATER_BREATHING() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_water_breathing");
        nbt.putString("AntidoteEffect", "minecraft:water_breathing");
        nbt.putInt("CustomAntidoteColor", 7909826);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_INVISIBILITY() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_invisibility");
        nbt.putString("AntidoteEffect", "minecraft:invisibility");
        nbt.putInt("CustomAntidoteColor", 13029354);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_WEAKNESS() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_weakness");
        nbt.putString("AntidoteEffect", "minecraft:weakness");
        nbt.putInt("CustomAntidoteColor", 4477288);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound INACTIVE_POISON() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "inactive_poison");
        nbt.putString("AntidoteEffect", "minecraft:poison");
        nbt.putInt("CustomAntidoteColor", 6917252);
        nbt.putBoolean("Inactive", true);
        return nbt;
    }

    public static NbtCompound SWIFTNESS() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "swiftness");
        nbt.putString("AntidoteEffect", "netherexp:speed_immunity");
        return nbt;
    }

    public static NbtCompound SLOWNESS() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "slowness");
        nbt.putString("AntidoteEffect", "netherexp:slowness_immunity");
        return nbt;
    }

    public static NbtCompound STRENGTH() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "strength");
        nbt.putString("AntidoteEffect", "netherexp:strength_immunity");
        return nbt;
    }

    public static NbtCompound JUMP_BOOST() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "leaping");
        nbt.putString("AntidoteEffect", "netherexp:jump_boost_immunity");
        return nbt;
    }

    public static NbtCompound REGENERATION() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "regeneration");
        nbt.putString("AntidoteEffect", "netherexp:regeneration_immunity");
        return nbt;
    }

    public static NbtCompound FIRE_RESISTANCE() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "fire_resistance");
        nbt.putString("AntidoteEffect", "netherexp:fire_resistance_immunity");
        return nbt;
    }

    public static NbtCompound WATER_BREATHING() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "water_breathing");
        nbt.putString("AntidoteEffect", "netherexp:water_breathing_immunity");
        return nbt;
    }

    public static NbtCompound INVISIBILITY() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "invisibility");
        nbt.putString("AntidoteEffect", "netherexp:invisibility_immunity");
        return nbt;
    }

    public static NbtCompound WEAKNESS() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "weakness");
        nbt.putString("AntidoteEffect", "netherexp:weakness_immunity");
        return nbt;
    }

    public static NbtCompound POISON() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "poison");
        nbt.putString("AntidoteEffect", "netherexp:poison_immunity");
        return nbt;
    }

    public static NbtCompound RESISTANCE() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "resistance");
        nbt.putString("AntidoteEffect", "netherexp:resistance_immunity");
        return nbt;
    }

    public static NbtCompound ABSORPTION() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "absorption");
        nbt.putString("AntidoteEffect", "netherexp:absorption_immunity");
        return nbt;
    }

    public static NbtCompound HASTE() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "haste");
        nbt.putString("AntidoteEffect", "netherexp:haste_immunity");
        return nbt;
    }

    public static NbtCompound MINING_FATIGUE() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "mining_fatigue");
        nbt.putString("AntidoteEffect", "netherexp:mining_fatigue_immunity");
        return nbt;
    }

    public static NbtCompound DARKNESS() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "darkness");
        nbt.putString("AntidoteEffect", "netherexp:darkness_immunity");
        return nbt;
    }

    public static NbtCompound LEVITATION() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "levitation");
        nbt.putString("AntidoteEffect", "netherexp:levitation_immunity");
        return nbt;
    }

    public static NbtCompound HUNGER() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("Antidote", "hunger");
        nbt.putString("AntidoteEffect", "netherexp:hunger_immunity");
        return nbt;
    }
}
