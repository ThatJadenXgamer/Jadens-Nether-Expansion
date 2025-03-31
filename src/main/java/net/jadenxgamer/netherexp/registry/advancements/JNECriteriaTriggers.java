package net.jadenxgamer.netherexp.registry.advancements;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceLocation;

public class JNECriteriaTriggers extends CriteriaTriggers {
    public static PlayerTrigger TAME_STAMPEDE;
    public static PlayerTrigger GROW_CEREBRAGE_CLARET;
    public static PlayerTrigger PLANTED_CEREBRAGE;
    public static PlayerTrigger BROKEN_FOSSIL_FUEL_ORE;
    public static PlayerTrigger BROKEN_DIAMOND_FOSSIL_ORE;
    public static PlayerTrigger EXORCISM;
    public static PlayerTrigger REVIVE_CARCASS;
    public static PlayerTrigger IMMORTAL_CARCASS;
    public static PlayerTrigger ACTIVATE_SANCTUM_COMPASS;
    public static PlayerTrigger KILLED_WITH_PUMP_CHARGE;
    public static PlayerTrigger MAKE_FAKE_CARCASS;

    public static void init() {
        TAME_STAMPEDE = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "tame_stampede")));
        GROW_CEREBRAGE_CLARET = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "grow_cerebrage_claret")));
        PLANTED_CEREBRAGE = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "planted_cerebrage")));
        BROKEN_FOSSIL_FUEL_ORE = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "broken_fossil_fuel_ore")));
        BROKEN_DIAMOND_FOSSIL_ORE = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "broken_diamond_fossil_ore")));
        EXORCISM = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "exorcism")));
        REVIVE_CARCASS = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "revive_carcass")));
        IMMORTAL_CARCASS = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "immortal_carcass")));
        ACTIVATE_SANCTUM_COMPASS = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "activate_sanctum_compass")));
        KILLED_WITH_PUMP_CHARGE = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "killed_with_pump_charge")));
        MAKE_FAKE_CARCASS = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "make_fake_carcass")));
    }
}
