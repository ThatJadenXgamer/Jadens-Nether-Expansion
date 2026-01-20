package net.jadenxgamer.netherexp.data.worldgen.placement;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.data.worldgen.features.JNEMiscFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class JNEMiscPlacement {

    public static ResourceKey<PlacedFeature> NETHERRACK_SPELEOTHEM = registerKey("netherrack_speleothem");
    public static ResourceKey<PlacedFeature> SOUL_SOIL_SPELEOTHEM = registerKey("soul_soil_speleothem");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NetherExp.id(name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context,
                SOUL_SOIL_SPELEOTHEM,
                holderGetter.getOrThrow(JNEMiscFeatures.SOUL_SOIL_SPELEOTHEM),
                RarityFilter.onAverageOnceEvery(7),
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.aboveBottom(0),
                        VerticalAnchor.belowTop(4)
                ),
                BiomeFilter.biome());

        PlacementUtils.register(context,
                NETHERRACK_SPELEOTHEM,
                holderGetter.getOrThrow(JNEMiscFeatures.NETHERRACK_SPELEOTHEM),
                RarityFilter.onAverageOnceEvery(2),
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.aboveBottom(0),
                        VerticalAnchor.belowTop(4)
                ),
                BiomeFilter.biome());
    }
}
