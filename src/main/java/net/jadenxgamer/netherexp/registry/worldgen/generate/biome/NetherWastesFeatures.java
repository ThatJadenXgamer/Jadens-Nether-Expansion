package net.jadenxgamer.netherexp.registry.worldgen.generate.biome;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.config.other.worldgen.CrimsonForestConfigs;
import net.jadenxgamer.netherexp.registry.config.other.worldgen.NetherWastesConfigs;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModPlacedFeatures;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class NetherWastesFeatures {
    private static final NetherWastesConfigs nwConfigs = NetherExp.getConfig().worldgen.netherWastesConfigs;

    public static void generateFeatures() {

        // STEP 0 - RAW_GENERATION

        // STEP 1 - LAKES

        // STEP 2 - LOCAL_MODIFICATIONS

        // STEP 3 - UNDERGROUND_STRUCTURES

        // STEP 4 - SURFACE_STRUCTURES

        // STEP 5 - STRONGHOLDS

        // STEP 6 - UNDERGROUND_ORES

        // STEP 7 - UNDERGROUND_DECORATION

        // STEP 8 - FLUID_SPRINGS

        // STEP 9 - VEGETAL_DECORATION

        // STEP 10 - TOP_LAYER_MODIFICATION

        if (nwConfigs.generate_quartz_crystals) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.QUARTZ_CRYSTAL);

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.QUARTZ_CRYSTAL_EXTRA);
        }
    }
}