package net.jadenxgamer.netherexp.registry.worldgen.generate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.config.other.worldgen.SoulSandValleyConfigs;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModPlacedFeatures;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class SoulSandValleyFeatures {

    private static final SoulSandValleyConfigs ssvConfigs = NetherExp.getConfig().worldgen.soulSandValleyConfigs;

    public static void generateFeatures() {

        // STEP 0 - RAW_GENERATION

        // STEP 1 - LAKES

        // STEP 2 - LOCAL_MODIFICATIONS

        // STEP 3 - UNDERGROUND_STRUCTURES

        // STEP 4 - SURFACE_STRUCTURES

        // STEP 5 - STRONGHOLDS

        // STEP 6 - UNDERGROUND_ORES

        // STEP 7 - UNDERGROUND_DECORATION

        if (ssvConfigs.generate_jagged_soul_slate) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.JAGGED_SOUL_SLATE);
        }

        if (ssvConfigs.generate_soul_magma_blobs) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.ORE_SOUL_MAGMA);
        }

        if (ssvConfigs.generate_bone_rods) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.BONE_ROD);
        }

        if (ssvConfigs.generate_fossil_fuel_ore) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FOSSIL_FUEL_ORE);
        }

        // STEP 8 - FLUID_SPRINGS

        // STEP 9 - VEGETAL_DECORATION

        if (ssvConfigs.generate_soul_swirls) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SOUL_SWIRLS_CEILING);
        }

        if (ssvConfigs.generate_soul_swirls) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SOUL_SWIRLS_FLOOR);
        }

        if (ssvConfigs.generate_ecto_soul_sand) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ECTO_SOUL_SAND);
        }

        if (ssvConfigs.generate_ectoplasm_lakes) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SOUL_SAND_VALLEY),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ECTOPLASM_LAKE);
        }

        // STEP 10 - TOP_LAYER_MODIFICATION
    }
}
