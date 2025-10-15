package net.jadenxgamer.netherexp.core.worldgen.feature;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class JNEConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> SORROWEED_PATCH_BONEMEAL = registerKey("soul_sand_valley/sorrowsquash_pastures/sorroweed_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRAIN_TREE = registerKey("nether_wastes/brain_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NetherExp.id(name));
    }

    // We can register both minecraft and netherexp features here.
    // (We could also put them in separate files for organization)
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
                        TREE_REPLACEABLE,
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
                        TREE_REPLACEABLE,
                        true));
        FeatureUtils.register(
                context, NetherFeatures.PATCH_CRIMSON_ROOTS, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.CRIMSON_ROOTS.defaultBlockState(), 5).add(
                                        JNEBlocks.CRIMSON_SPROUTS.get()
                                                .defaultBlockState(), 1)))));
    }

    static final BlockPredicate TREE_REPLACEABLE = BlockPredicate.matchesBlocks(
            Blocks.OAK_SAPLING,
            Blocks.SPRUCE_SAPLING,
            Blocks.BIRCH_SAPLING,
            Blocks.JUNGLE_SAPLING,
            Blocks.ACACIA_SAPLING,
            Blocks.CHERRY_SAPLING,
            Blocks.DARK_OAK_SAPLING,
            Blocks.MANGROVE_PROPAGULE,
            Blocks.DANDELION,
            Blocks.TORCHFLOWER,
            Blocks.POPPY,
            Blocks.BLUE_ORCHID,
            Blocks.ALLIUM,
            Blocks.AZURE_BLUET,
            Blocks.RED_TULIP,
            Blocks.ORANGE_TULIP,
            Blocks.WHITE_TULIP,
            Blocks.PINK_TULIP,
            Blocks.OXEYE_DAISY,
            Blocks.CORNFLOWER,
            Blocks.WITHER_ROSE,
            Blocks.LILY_OF_THE_VALLEY,
            Blocks.BROWN_MUSHROOM,
            Blocks.RED_MUSHROOM,
            Blocks.WHEAT,
            Blocks.SUGAR_CANE,
            Blocks.ATTACHED_PUMPKIN_STEM,
            Blocks.ATTACHED_MELON_STEM,
            Blocks.PUMPKIN_STEM,
            Blocks.MELON_STEM,
            Blocks.LILY_PAD,
            Blocks.NETHER_WART,
            Blocks.COCOA,
            Blocks.CARROTS,
            Blocks.POTATOES,
            Blocks.CHORUS_PLANT,
            Blocks.CHORUS_FLOWER,
            Blocks.TORCHFLOWER_CROP,
            Blocks.PITCHER_CROP,
            Blocks.BEETROOTS,
            Blocks.SWEET_BERRY_BUSH,
            Blocks.WARPED_FUNGUS,
            Blocks.CRIMSON_FUNGUS,
            Blocks.WEEPING_VINES,
            Blocks.WEEPING_VINES_PLANT,
            Blocks.TWISTING_VINES,
            Blocks.TWISTING_VINES_PLANT,
            Blocks.CAVE_VINES,
            Blocks.CAVE_VINES_PLANT,
            Blocks.SPORE_BLOSSOM,
            Blocks.AZALEA,
            Blocks.FLOWERING_AZALEA,
            Blocks.MOSS_CARPET,
            Blocks.PINK_PETALS,
            Blocks.BIG_DRIPLEAF,
            Blocks.BIG_DRIPLEAF_STEM,
            Blocks.SMALL_DRIPLEAF
    );
}
