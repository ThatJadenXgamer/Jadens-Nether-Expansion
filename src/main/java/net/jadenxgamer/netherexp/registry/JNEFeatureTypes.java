package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.worldgen.feature.BrainTreeFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEHugeFungusFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.MoundFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.NotGarbageLargeDripstoneFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.config.JNEHugeFungusFeatureConfiguration;
import net.jadenxgamer.netherexp.core.worldgen.feature.config.MoundFeatureConfiguration;
import net.jadenxgamer.netherexp.core.worldgen.feature.config.NotGarbageLargeDripstoneFeatureConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JNEFeatureTypes {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, NetherExp.MOD_ID);

    public static final Supplier<Feature<?>> NOT_GARBAGE_LARGE_DRIPSTONE = FEATURES.register("not_garbage_large_dripstone", () ->
            new NotGarbageLargeDripstoneFeature(NotGarbageLargeDripstoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<?>> JNE_HUGE_FUNGUS = FEATURES.register("jne_huge_fungus", () ->
            new JNEHugeFungusFeature(JNEHugeFungusFeatureConfiguration.CODEC));

    public static final Supplier<Feature<?>> BRAIN_TREE = FEATURES.register("brain_tree", () ->
            new BrainTreeFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<?>> MOUND = FEATURES.register("mound", () ->
            new MoundFeature(MoundFeatureConfiguration.CODEC));

    public static void init(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
