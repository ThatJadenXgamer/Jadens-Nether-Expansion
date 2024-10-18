package net.jadenxgamer.netherexp.registry.worldgen.feature;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.BrainTreeFeature;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.SoulMagmaClusterFeature;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.WarpedFungusFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class JNEFeature {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(NetherExp.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<Feature<HugeFungusConfiguration>> WARPED_HUGE_FUNGUS = FEATURES.register("warped_huge_fungus", () ->
            new WarpedFungusFeature(HugeFungusConfiguration.CODEC));

    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> SOUL_MAGMA_CLUSTER = FEATURES.register("soul_magma_cluster", () ->
            new SoulMagmaClusterFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> BRAIN_TREE = FEATURES.register("brain_tree", () ->
            new BrainTreeFeature(NoneFeatureConfiguration.CODEC));

    public static void init() {
        FEATURES.register();
    }
}
