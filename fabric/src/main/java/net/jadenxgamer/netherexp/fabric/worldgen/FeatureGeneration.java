package net.jadenxgamer.netherexp.fabric.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.compat.CompatUtil;
import net.jadenxgamer.netherexp.fabric.worldgen.biomes.*;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.worldgen.feature.JNEPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FeatureGeneration {

    public static void generateFeatures() {
        NetherWastesFeatures.init();
        SoulSandValleyFeatures.init();
        CrimsonForestFeatures.init();
        WarpedForestFeatures.init();
        BasaltDeltasFeatures.init();
        if (CompatUtil.checkGardensOfTheDead()) {
            SoulblightForestFeatures.init();
        }

        init();
    }

    private static void init() {

        // STEP 0 - RAW_GENERATION

        // STEP 1 - LAKES

        // STEP 2 - LOCAL_MODIFICATIONS

        // STEP 3 - UNDERGROUND_STRUCTURES

        // STEP 4 - SURFACE_STRUCTURES

        // STEP 5 - STRONGHOLDS

        // STEP 6 - UNDERGROUND_ORES

        // STEP 7 - UNDERGROUND_DECORATION

        if (CompatUtil.checkRubinatedNether()) {
            BiomeModifications.addFeature(BiomeSelectors.tag(JNETags.Biomes.SOUL_RUBY_ORE_GENERATES),
                    GenerationStep.Decoration.UNDERGROUND_DECORATION, JNEPlacedFeatures.SOUL_MAGMA_ORE);
        }

        // STEP 8 - FLUID_SPRINGS

        // STEP 9 - VEGETAL_DECORATION

        // STEP 10 - TOP_LAYER_MODIFICATION
    }
}
