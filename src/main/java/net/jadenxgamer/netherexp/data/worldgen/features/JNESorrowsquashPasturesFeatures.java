package net.jadenxgamer.netherexp.data.worldgen.features;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class JNESorrowsquashPasturesFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> SORROWEED_VEGETATION = registerKey("sorroweed_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SORROWEED_PATCH_BONEMEAL = registerKey("sorroweed_patch_bonemeal");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NetherExp.id("soul_sand_valley/sorrowsquash_pastures/" + name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        FeatureUtils.register(
                context, SORROWEED_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(Blocks.AIR.defaultBlockState(), 45)
                                        .add(Blocks.CRIMSON_ROOTS.defaultBlockState(), 25)
                                        .add(JNEBlocks.SOUL_SWIRLS.get().defaultBlockState(), 10)
                        )
                ));

        FeatureUtils.register(
                context, SORROWEED_PATCH_BONEMEAL, Feature.VEGETATION_PATCH, new VegetationPatchConfiguration(
                        JNETags.Blocks.SORROWEED_REPLACEABLE,
                        BlockStateProvider.simple(JNEBlocks.SORROWEED.get()),
                        PlacementUtils.inlinePlaced(holderGetter.getOrThrow(SORROWEED_VEGETATION)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0,
                        5,
                        0.6f,
                        UniformInt.of(1, 2),
                        0.75f
                ));
    }

}
