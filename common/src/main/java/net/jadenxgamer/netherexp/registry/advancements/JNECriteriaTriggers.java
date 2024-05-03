package net.jadenxgamer.netherexp.registry.advancements;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceLocation;

public class JNECriteriaTriggers extends CriteriaTriggers {
    public static PlayerTrigger BROKEN_FOSSIL_FUEL_ORE;
    public static PlayerTrigger BROKEN_DIAMOND_FOSSIL_ORE;

    public static PlayerTrigger EXORCISM;

    public static void init() {
        BROKEN_FOSSIL_FUEL_ORE = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "broken_fossil_fuel_ore")));
        BROKEN_DIAMOND_FOSSIL_ORE = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "broken_diamond_fossil_ore")));
        EXORCISM = JNECriteriaTriggers.register(new PlayerTrigger(new ResourceLocation(NetherExp.MOD_ID, "exorcism")));
    }
}
