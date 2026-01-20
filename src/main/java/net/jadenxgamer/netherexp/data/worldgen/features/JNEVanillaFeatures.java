package net.jadenxgamer.netherexp.data.worldgen.features;

import net.jadenxgamer.netherexp.data.worldgen.JNEFeatures;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class JNEVanillaFeatures {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        // Vanilla
        FeatureUtils.register(
                context,
                TreeFeatures.WARPED_FUNGUS,
                Feature.HUGE_FUNGUS,
                new HugeFungusConfiguration(
                        Blocks.WARPED_NYLIUM.defaultBlockState(),
                        Blocks.WARPED_STEM.defaultBlockState(),
                        Blocks.WARPED_WART_BLOCK.defaultBlockState(),
                        JNEBlocks.SHROOMNIGHT.get().defaultBlockState(),
                        JNEFeatures.TREE_REPLACEABLE,
                        false));

        FeatureUtils.register(
                context,
                TreeFeatures.WARPED_FUNGUS_PLANTED,
                Feature.HUGE_FUNGUS,
                new HugeFungusConfiguration(
                        Blocks.WARPED_NYLIUM.defaultBlockState(),
                        Blocks.WARPED_STEM.defaultBlockState(),
                        Blocks.WARPED_WART_BLOCK.defaultBlockState(),
                        JNEBlocks.SHROOMNIGHT.get().defaultBlockState(),
                        JNEFeatures.TREE_REPLACEABLE,
                        true));

        FeatureUtils.register(
                context, NetherFeatures.PATCH_CRIMSON_ROOTS, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.CRIMSON_ROOTS.defaultBlockState(), 5).add(
                                        JNEBlocks.CRIMSON_SPROUTS.get()
                                                .defaultBlockState(), 1)))));

    }


}
