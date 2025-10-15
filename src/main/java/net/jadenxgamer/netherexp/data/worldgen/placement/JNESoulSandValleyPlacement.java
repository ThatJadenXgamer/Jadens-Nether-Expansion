package net.jadenxgamer.netherexp.data.worldgen.placement;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.data.worldgen.features.JNESoulSandValleyFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class JNESoulSandValleyPlacement {

    public static final ResourceKey<PlacedFeature> BONE_PIKE = registerKey("bone_pike");
    public static final ResourceKey<PlacedFeature> ECTO_SOUL_SAND = registerKey("ecto_soul_sand");
    public static final ResourceKey<PlacedFeature> ECTOPLASM_LAKE = registerKey("ectoplasm_lake");
    public static final ResourceKey<PlacedFeature> FOSSIL_FUEL_ORE = registerKey("fossil_fuel_ore");
    public static final ResourceKey<PlacedFeature> FOSSIL_ORE = registerKey("fossil_ore");
    public static final ResourceKey<PlacedFeature> HANGING_MOUND = registerKey("hanging_mound");
    public static final ResourceKey<PlacedFeature> MOUND = registerKey("mound");
    public static final ResourceKey<PlacedFeature> ORE_SOUL_MAGMA = registerKey("ore_soul_magma");
    public static final ResourceKey<PlacedFeature> PALE_SOUL_SLATE = registerKey("pale_soul_slate");
    public static final ResourceKey<PlacedFeature> PALE_SOUL_SLATE_SURFACE = registerKey("pale_soul_slate_surface");
    public static final ResourceKey<PlacedFeature> SOUL_SWIRLS_CEILING = registerKey("soul_swirls_ceiling");
    public static final ResourceKey<PlacedFeature> SOUL_SWIRLS_FLOOR = registerKey("soul_swirls_floor");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NetherExp.id("soul_sand_valley/" + name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(
                context,
                BONE_PIKE,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.BONE_PIKE),
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                ECTO_SOUL_SAND,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.ECTO_SOUL_SAND),
                CountPlacement.of(80),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                ECTOPLASM_LAKE,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.ECTOPLASM_LAKE),
                CountPlacement.of(4),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(48), VerticalAnchor.belowTop(2)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                FOSSIL_FUEL_ORE,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.FOSSIL_FUEL_ORE),
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                FOSSIL_ORE,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.FOSSIL_ORE),
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                HANGING_MOUND,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.HANGING_MOUND),
                NoiseBasedCountPlacement.of(5, 1d, 0.2d),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(4)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.matchesBlocks(Blocks.AIR), 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                MOUND,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.MOUND),
                NoiseBasedCountPlacement.of(2, 1d, -0.3d),
                CountOnEveryLayerPlacement.of(1),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                ORE_SOUL_MAGMA,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.ORE_SOUL_MAGMA),
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(90), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                PALE_SOUL_SLATE,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.PALE_SOUL_SLATE),
                CountPlacement.of(78),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                PALE_SOUL_SLATE_SURFACE,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.PALE_SOUL_SLATE_SURFACE),
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                SOUL_SWIRLS_CEILING,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.SOUL_SWIRLS_CEILING),
                CountPlacement.of(80),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(4)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.matchesBlocks(Blocks.AIR), 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                SOUL_SWIRLS_FLOOR,
                holderGetter.getOrThrow(JNESoulSandValleyFeatures.SOUL_SWIRLS_FLOOR),
                CountPlacement.of(4),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4)),
                BiomeFilter.biome());
    }
}
