package net.jadenxgamer.netherexp.registry;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.jadenxgamer.netherexp.core.datadriven.WispArchaeology;
import net.jadenxgamer.netherexp.core.misc.neoforge.ConfigCondition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class JNERegistries {

    /**
     * NeoForge Deferred Registers
     */

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, NetherExp.MOD_ID);
    public static final Supplier<MapCodec<ConfigCondition>> CONFIG = CONDITION_CODECS.register("config", () -> ConfigCondition.CODEC);

    /**
     * Data Driven
     */

    public static final ResourceKey<Registry<WispArchaeology>> WISP_ARCHAEOLOGY = key("wisp_archaeology");
    public static final ResourceKey<Registry<OnDeathGroundConversion>> ON_DEATH_GROUND_CONVERSION = key("on_death_ground_conversion");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(NetherExp.id(name));
    }

    public static void init(IEventBus eventBus) {
        CONDITION_CODECS.register(eventBus);
    }

    public static void datapackInit(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(JNERegistries.WISP_ARCHAEOLOGY, WispArchaeology.CODEC);
        event.dataPackRegistry(JNERegistries.ON_DEATH_GROUND_CONVERSION, OnDeathGroundConversion.CODEC);
    }
}
