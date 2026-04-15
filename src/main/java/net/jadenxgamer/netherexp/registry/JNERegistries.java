package net.jadenxgamer.netherexp.registry;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.datadriven.ApparitionAggressions;
import net.jadenxgamer.netherexp.core.datadriven.ApparitionGargoyleStatues;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.jadenxgamer.netherexp.core.datadriven.WispArchaeology;
import net.jadenxgamer.netherexp.core.datadriven.Antidote;
import net.jadenxgamer.netherexp.core.misc.neoforge.ConfigCondition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;

public class JNERegistries {

    /**
     * NeoForge Deferred Registries
     */

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, NetherExp.MOD_ID);
    public static final Supplier<MapCodec<ConfigCondition>> CONFIG = CONDITION_CODECS.register("config", () -> ConfigCondition.CODEC);

    /**
     * JNE Registries
     */

    //public static final Registry<Antidote> ANTIDOTE_TYPES = new RegistryBuilder<>(JNERegistries.Keys.ANTIDOTE_TYPE).sync(true).create();

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(NetherExp.netherexpPath(name));
    }

    public static void init(IEventBus eventBus) {
        CONDITION_CODECS.register(eventBus);
    }

    public static void registryInit(NewRegistryEvent event) {
        //event.register(ANTIDOTE_TYPES);
    }

    public static void datapackInit(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(JNERegistries.Keys.WISP_ARCHAEOLOGY, WispArchaeology.CODEC);
        event.dataPackRegistry(JNERegistries.Keys.ON_DEATH_GROUND_CONVERSION, OnDeathGroundConversion.CODEC);
        event.dataPackRegistry(JNERegistries.Keys.APPARITION_AGGRESSIONS, ApparitionAggressions.CODEC);
        event.dataPackRegistry(JNERegistries.Keys.APPARITION_GARGOYLE_STATUES, ApparitionGargoyleStatues.CODEC);
        event.dataPackRegistry(JNERegistries.Keys.ANTIDOTE, Antidote.CODEC);
    }

    public static final class Keys {
        // JNE Registries
        //public static final ResourceKey<Registry<Antidote>> ANTIDOTE_TYPE = ResourceKey.createRegistryKey(NetherExp.netherexpPath("antidote_type"));

        // Data-Driven Registries
        public static final ResourceKey<Registry<WispArchaeology>> WISP_ARCHAEOLOGY = key("wisp_archaeology");
        public static final ResourceKey<Registry<OnDeathGroundConversion>> ON_DEATH_GROUND_CONVERSION = key("on_death_ground_conversion");
        public static final ResourceKey<Registry<ApparitionAggressions>> APPARITION_AGGRESSIONS = key("apparition/aggressions");
        public static final ResourceKey<Registry<ApparitionGargoyleStatues>> APPARITION_GARGOYLE_STATUES = key("apparition/gargoyle_statues");
        public static final ResourceKey<Registry<Antidote>> ANTIDOTE = key("antidote");
    }
}
