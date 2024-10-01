package net.jadenxgamer.netherexp.fabric.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.registry.worldgen.feature.JNEPlacedFeatures;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class CrimsonForestFeatures {

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

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.CRIMSON_SPORESHROOM);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.WEEPING_IVY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.CRIMSON_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, JNEPlacedFeatures.CRIMSON_SPROUTS);
        
        // STEP 10 - TOP_LAYER_MODIFICATION
    }
}
