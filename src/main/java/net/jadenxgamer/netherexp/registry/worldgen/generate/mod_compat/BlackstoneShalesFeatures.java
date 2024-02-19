package net.jadenxgamer.netherexp.registry.worldgen.generate.mod_compat;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.config.other.modcompat.cinderscapes.BlackstoneShalesConfigs;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.jadenxgamer.netherexp.registry.worldgen.feature.ModPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

public class BlackstoneShalesFeatures {
    private static final BlackstoneShalesConfigs bsConfigs = NetherExp.getConfig().modcompat.cinderscapesConfigs.blackstoneShalesConfigs;

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

        if (bsConfigs.generate_geyser) {
            BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.BLACKSTONE_SHALES),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.BLACKSTONIC_GEYSER);
        }

        if (bsConfigs.generate_shale_swirls) {
            BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.BLACKSTONE_SHALES),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.BLACKSTONE_SHALES_SHALE_SWIRLS_CEILING);

            BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.Biomes.BLACKSTONE_SHALES),
                    GenerationStep.Feature.TOP_LAYER_MODIFICATION, ModPlacedFeatures.BLACKSTONE_SHALES_SHALE_SWIRLS_FLOOR);
        }
    }
}
