package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNECriteriaTriggers {
    
    public static final DeferredRegister<CriterionTrigger<?>> CRITERIA_TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, NetherExp.MOD_ID);
    
    public static final Supplier<PlayerTrigger> TAME_STAMPEDE = CRITERIA_TRIGGERS.register("tame_stampede", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> GROW_CEREBRAGE_CLARET = CRITERIA_TRIGGERS.register("grow_cerebrage_claret", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> PLANTED_CEREBRAGE = CRITERIA_TRIGGERS.register("planted_cerebrage", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> BROKEN_FOSSIL_FUEL_ORE = CRITERIA_TRIGGERS.register("broken_fossil_fuel_ore", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> EXORCISM = CRITERIA_TRIGGERS.register("exorcism", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> REVIVE_CARCASS = CRITERIA_TRIGGERS.register("revive_carcass", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> ACTIVATE_SANCTUM_COMPASS = CRITERIA_TRIGGERS.register("activate_sanctum_compass", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> KILLED_WITH_PUMP_CHARGE = CRITERIA_TRIGGERS.register("killed_with_pump_charge", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> BANSHEE_REDIRECT = CRITERIA_TRIGGERS.register("banshee_redirect", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> DISMANTLE_PETRIFIED_ECTO_SLAB = CRITERIA_TRIGGERS.register("dismantle_petrified_ecto_slab", PlayerTrigger::new);
    public static final Supplier<PlayerTrigger> INVOLUNTARY_EVICTION = CRITERIA_TRIGGERS.register("involuntary_eviction", PlayerTrigger::new);

    public static void init(IEventBus eventBus) {
        CRITERIA_TRIGGERS.register(eventBus);
    }
}