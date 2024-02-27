package net.jadenxgamer.netherexp.registry.worldgen.generate.biome;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.registry.worldgen.biome.JNEBiomes;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

public class VentMireFeatures {
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

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(JNEBiomes.VENT_MIRE),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SMOKESTALK);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(JNEBiomes.VENT_MIRE),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.IGNEOUS_REEDS);

        // STEP 10 - TOP_LAYER_MODIFICATION

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(JNEBiomes.VENT_MIRE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.VENT_DELTA);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(JNEBiomes.VENT_MIRE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.BLACKSTONE_PATCH);
    }
}
