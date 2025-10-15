package net.jadenxgamer.netherexp.data.worldgen.features;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.BonePikeBlock;
import net.jadenxgamer.netherexp.core.block.SwirlsBlock;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.core.worldgen.feature.config.MoundConfiguration;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEFeatureTypes;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class JNESoulSandValleyFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BONE_PIKE = registerKey("bone_pike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ECTO_SOUL_SAND = registerKey("ecto_soul_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ECTOPLASM_LAKE = registerKey("ectoplasm_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOSSIL_FUEL_ORE = registerKey("fossil_fuel_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOSSIL_ORE = registerKey("fossil_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_MOUND = registerKey("hanging_mound");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOUND = registerKey("mound");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SOUL_MAGMA = registerKey("ore_soul_magma");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_SOUL_SLATE = registerKey("pale_soul_slate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_SOUL_SLATE_SURFACE = registerKey("pale_soul_slate_surface");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_SWIRLS_CEILING = registerKey("soul_swirls_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_SWIRLS_FLOOR = registerKey("soul_swirls_floor");

    public static final BlockPredicate ON_SOUL = BlockPredicate.allOf(
            BlockPredicate.matchesBlocks(Blocks.AIR), BlockPredicate.matchesBlocks(
                    new Vec3i(0, -1, 0),
                    Blocks.SOUL_SOIL,
                    Blocks.SOUL_SAND,
                    JNEBlocks.SOUL_SLATE.get(),
                    JNEBlocks.PALE_SOUL_SLATE.get(),
                    JNEBlocks.SOUL_PERMAFROST.get()));

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NetherExp.id("soul_sand_valley/" + name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(
                context, BONE_PIKE, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                        96, 5, 3, PlacementUtils.filtered(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList
                                .<BlockState>builder()
                                .add(Blocks.BONE_BLOCK.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y), 15)
                                .add(JNEBlocks.BONE_PIKE.get().defaultBlockState().setValue(BonePikeBlock.BONES, 1), 50)
                                .add(JNEBlocks.BONE_PIKE.get().defaultBlockState().setValue(BonePikeBlock.BONES, 2), 30)
                                .add(JNEBlocks.BONE_PIKE.get().defaultBlockState().setValue(BonePikeBlock.BONES, 3), 10)
                                .add(JNEBlocks.BONE_PIKE.get().defaultBlockState().setValue(BonePikeBlock.BONES, 4), 5)
                                .build())),
                        ON_SOUL)));

        FeatureUtils.register(
                context,
                ECTO_SOUL_SAND,
                Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(new BlockMatchTest(Blocks.SOUL_SAND), JNEBlocks.ECTO_SOUL_SAND.get().defaultBlockState())),
                        6,
                        0.0f));

        FeatureUtils.register(
                context, ECTOPLASM_LAKE, Feature.LAKE, new Configuration(
                        BlockStateProvider.simple(JNEFluids.ECTOPLASM.get()),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(JNEBlocks.SOUL_MAGMA_BLOCK.get().defaultBlockState(), 2).build())));

        FeatureUtils.register(
                context,
                FOSSIL_FUEL_ORE,
                Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(new BlockMatchTest(Blocks.SOUL_SOIL), JNEBlocks.FOSSIL_FUEL_ORE.get().defaultBlockState())),
                        8,
                        0.5f));

        FeatureUtils.register(
                context,
                FOSSIL_ORE,
                Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target(new BlockMatchTest(Blocks.SOUL_SOIL), JNEBlocks.FOSSIL_ORE.get().defaultBlockState())), 3, 0.2f));

        FeatureUtils.register(
                context,
                HANGING_MOUND,
                JNEFeatureTypes.MOUND.get(),
                new MoundConfiguration(HolderSet.direct(holder(Blocks.SOUL_SAND), holder(Blocks.SOUL_SOIL)), 3, 4, 3, 4, 1, 4, 3, true));

        FeatureUtils.register(
                context,
                MOUND,
                JNEFeatureTypes.MOUND.get(),
                new MoundConfiguration(
                        HolderSet.direct(holder(Blocks.SOUL_SAND), holder(Blocks.SOUL_SOIL), holder(JNEBlocks.PALE_SOUL_SLATE.get())),
                        3,
                        4,
                        3,
                        4,
                        1,
                        4,
                        3,
                        false));

        FeatureUtils.register(
                context, ORE_SOUL_MAGMA, Feature.ORE, new OreConfiguration(
                        List.of(OreConfiguration.target(new TagMatchTest(JNETags.Blocks.SOUL_MAGMA_REPLACEABLE), JNEBlocks.SOUL_MAGMA_BLOCK.get().defaultBlockState())),
                        33,
                        0.0f));

        FeatureUtils.register(
                context, PALE_SOUL_SLATE, Feature.DISK, new DiskConfiguration(
                        RuleBasedBlockStateProvider.simple(JNEBlocks.PALE_SOUL_SLATE.get()),
                        BlockPredicate.matchesBlocks(Blocks.NETHERRACK, JNEBlocks.PALE_SOUL_SLATE.get()),
                        UniformInt.of(4, 6),
                        2));

        FeatureUtils.register(
                context, PALE_SOUL_SLATE_SURFACE, Feature.DISK, new DiskConfiguration(
                        RuleBasedBlockStateProvider.simple(JNEBlocks.PALE_SOUL_SLATE.get()),
                        BlockPredicate.matchesTag(JNETags.Blocks.SOUL_SLATE_REPLACEABLE),
                        UniformInt.of(4, 6),
                        1));

        FeatureUtils.register(
                context, SOUL_SWIRLS_CEILING, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(
                        List.of(BlockColumnConfiguration.layer(
                                ConstantInt.of(1),
                                BlockStateProvider.simple(JNEBlocks.SOUL_SWIRLS.get().defaultBlockState().setValue(SwirlsBlock.FACING, Direction.DOWN)))),
                        Direction.DOWN,
                        BlockPredicate.matchesBlocks(Blocks.AIR),
                        true));

        FeatureUtils.register(
                context, SOUL_SWIRLS_FLOOR, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                        96,
                        7,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(JNEBlocks.SOUL_SWIRLS.get())), ON_SOUL)));
    }

    private static Holder<Block> holder(Block block) {
        return BuiltInRegistries.BLOCK.wrapAsHolder(block);
    }
}
