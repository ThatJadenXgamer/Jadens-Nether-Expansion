package net.jadenxgamer.netherexp.registry.worldgen.generate.biome;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.config.other.worldgen.WarpedForestConfigs;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModPlacedFeatures;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class WarpedForestFeatures {
    private static final WarpedForestConfigs wfConfigs = NetherExp.getConfig().worldgen.warpedForestConfigs;

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

        if (wfConfigs.generate_sporeshroom) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WARPED_SPORESHROOM);
        }

        // STEP 10 - TOP_LAYER_MODIFICATION

        if (wfConfigs.generate_wart_beard) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.WARPED_WART_BEARD);
        }

        if (wfConfigs.generate_spotted_wart_blocks) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.WARPED_WART_BLOCK_SPOTTER_SMALL);

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.WARPED_WART_BLOCK_SPOTTER_MEDIUM);

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.WARPED_WART_BLOCK_SPOTTER_BIG);
        }

        if (wfConfigs.generate_twisting_ivy) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.TWISTING_IVY);
        }
    }
}
