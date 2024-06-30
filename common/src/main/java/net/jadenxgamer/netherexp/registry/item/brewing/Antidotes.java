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
    }

    /*
     * "Antidote" | Used for the Antidote's Name and could be considered an equivalent to it's "Registration"
     * "AntidoteEffect" | Is the MobEffect you'll get once you drink it
     * "CustomAntidoteColor" | By Default grabs the color from the MobEffect but if present will override it
     * "Duration" | Gets the Duration for the MobEffectInstance
     */

    public static CompoundTag AWKWARD() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", "awkward");
        nbt.putInt("CustomAntidoteColor", 3694022);
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
