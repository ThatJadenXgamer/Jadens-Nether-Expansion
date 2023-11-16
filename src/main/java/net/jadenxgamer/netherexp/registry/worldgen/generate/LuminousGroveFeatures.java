package net.jadenxgamer.netherexp.registry.worldgen.generate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

public class LuminousGroveFeatures {
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

        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.LUMINOUS_GROVE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.TWILIGHT_VINES);

        // STEP 10 - TOP_LAYER_MODIFICATION

        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.LUMINOUS_GROVE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.SHROOMBLIGHT);

        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.LUMINOUS_GROVE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.UMBRAL_SPORESHROOM);

        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.LUMINOUS_GROVE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.TWILIGHT_IVY);
    }
}
