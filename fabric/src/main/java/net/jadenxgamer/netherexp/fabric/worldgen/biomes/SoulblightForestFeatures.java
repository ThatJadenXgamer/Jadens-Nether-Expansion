package net.jadenxgamer.netherexp.fabric.worldgen.biomes;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.compat.CompatUtil;
import net.jadenxgamer.netherexp.registry.worldgen.feature.JNEPlacedFeatures;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class SoulblightForestFeatures {

    public static void init() {

        // STEP 0 - RAW_GENERATION

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.RAW_GENERATION, JNEPlacedFeatures.PALE_SOUL_SLATE);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.RAW_GENERATION, JNEPlacedFeatures.PALE_SOUL_SLATE_SURFACE);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, JNEPlacedFeatures.SOUL_MAGMA_CLUSTER);

        // STEP 1 - LAKES

        // STEP 2 - LOCAL_MODIFICATIONS

        // STEP 3 - UNDERGROUND_STRUCTURES

        // STEP 4 - SURFACE_STRUCTURES

        // STEP 5 - STRONGHOLDS

        // STEP 6 - UNDERGROUND_ORES

        // STEP 7 - UNDERGROUND_DECORATION

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, JNEPlacedFeatures.ORE_SOUL_MAGMA);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, JNEPlacedFeatures.FOSSIL_FUEL_ORE);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, JNEPlacedFeatures.FOSSIL_ORE);

        // STEP 8 - FLUID_SPRINGS

        // STEP 9 - VEGETAL_DECORATION

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.SOULBLIGHT_SPORESHROOM);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.BLIGHT_SWIRLS_CEILING);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.BLIGHT_SWIRLS_FLOOR);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.ECTO_SOUL_SAND);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CompatUtil.BiomeKeys.SOULBLIGHT_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.ECTOPLASM_LAKE);
        
        // STEP 10 - TOP_LAYER_MODIFICATION
    }
}
