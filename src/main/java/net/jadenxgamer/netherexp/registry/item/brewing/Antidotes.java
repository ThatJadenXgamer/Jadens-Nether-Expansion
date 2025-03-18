package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class Antidotes {
    public static final List<CompoundTag> ANTIDOTES = new ArrayList<>();

    static {
        addAntidote("awkward", 3694022);
        addAntidote("swiftness", "netherexp:speed_immunity", 600);
        addAntidote("slowness", "netherexp:slowness_immunity", 600);
        addAntidote("strength", "netherexp:strength_immunity", 600);
        addAntidote("leaping", "netherexp:jump_boost_immunity", 600);
        addAntidote("regeneration", "netherexp:regeneration_immunity", 600);
        addAntidote("fire_resistance", "netherexp:fire_resistance_immunity", 120);
        addAntidote("water_breathing", "netherexp:water_breathing_immunity", 120);
        addAntidote("invisibility", "netherexp:invisibility_immunity", 600);
        addAntidote("weakness", "netherexp:weakness_immunity", 600);
        addAntidote("poison", "netherexp:poison_immunity", 600);
        addAntidote("resistance", "netherexp:resistance_immunity", 900);
        addAntidote("absorption", "netherexp:absorption_immunity", 900);
        addAntidote("haste", "netherexp:haste_immunity", 1800);
        addAntidote("mining_fatigue", "netherexp:mining_fatigue_immunity", 1800);
        addAntidote("darkness", "netherexp:darkness_immunity", 1800);
        addAntidote("levitation", "netherexp:levitation_immunity", 1080);
        addAntidote("hunger", "netherexp:hunger_immunity", 1080);
        addAntidote("decay", "netherexp:wither_immunity", 900);

        if (CompatUtil.checkDiceyVentures()) {
            addAntidote("luck", "netherexp:luck_immunity", 900);
            addAntidote("unluck", "netherexp:unluck_immunity", 900);
        }
        
        if (CompatUtil.checkOreganized()) {
            addAntidote("brain_damage", "netherexp:brain_damage_immunity", 900);
        }
    }

    private static void addAntidote(String antidoteName, String antidoteEffect, int duration) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", antidoteName);
        nbt.putString("AntidoteEffect", antidoteEffect);
        nbt.putInt("Duration", duration);
        ANTIDOTES.add(nbt);
    }

    private static void addAntidote(String antidoteName, int color) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Antidote", antidoteName);
        nbt.putInt("CustomAntidoteColor", color);
        ANTIDOTES.add(nbt);
    }
}
