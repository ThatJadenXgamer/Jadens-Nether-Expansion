package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.worldgen.features.BrainTreeFeature;
import net.jadenxgamer.netherexp.core.worldgen.features.JNEHugeFungusFeature;
import net.jadenxgamer.netherexp.core.worldgen.features.MoundFeature;
import net.jadenxgamer.netherexp.core.worldgen.features.JNELargeDripstoneFeature;
import net.jadenxgamer.netherexp.core.worldgen.features.configuration.JNEHugeFungusConfiguration;
import net.jadenxgamer.netherexp.core.worldgen.features.configuration.MoundConfiguration;
import net.jadenxgamer.netherexp.core.worldgen.features.configuration.JNELargeDripstoneConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNEFeatureTypes {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, NetherExp.MOD_ID);

    public static final Supplier<Feature<JNELargeDripstoneConfiguration>> NOT_GARBAGE_LARGE_DRIPSTONE = FEATURES.register("not_garbage_large_dripstone", () ->
            new JNELargeDripstoneFeature(JNELargeDripstoneConfiguration.CODEC));

    public static final Supplier<Feature<?>> JNE_HUGE_FUNGUS = FEATURES.register("jne_huge_fungus", () ->
            new JNEHugeFungusFeature(JNEHugeFungusConfiguration.CODEC));

    public static final Supplier<Feature<?>> BRAIN_TREE = FEATURES.register("brain_tree", () ->
            new BrainTreeFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<MoundConfiguration>> MOUND = FEATURES.register("mound", () ->
            new MoundFeature(MoundConfiguration.CODEC));

    public static void init(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
