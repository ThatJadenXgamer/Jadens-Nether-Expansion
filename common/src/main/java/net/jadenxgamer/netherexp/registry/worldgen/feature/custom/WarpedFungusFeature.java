package net.jadenxgamer.netherexp.registry.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;

public class WarpedFungusFeature extends Feature<HugeFungusConfiguration> {

    // Based on Vanilla HugeFungusFeature with cleaner variables, different height distribution and beard placement

    public WarpedFungusFeature(Codec<HugeFungusConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeFungusConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos originPos = context.origin();
        RandomSource random = context.random();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        HugeFungusConfiguration config = context.config();
        Block baseBlock = config.validBaseState.getBlock();
        BlockPos placePos = null;
        BlockState baseBlockState = worldGenLevel.getBlockState(originPos.below());

        if (baseBlockState.is(baseBlock)) {
            placePos = originPos;
        }

        if (placePos == null) {
            return false;
        } else {
            int height = Mth.nextInt(random, 9, 13);
            if (random.nextInt(12) == 0) {
                height *= 2;
            }

            if (!config.planted) {
                int maxGenDepth = chunkGenerator.getGenDepth();
                if (placePos.getY() + height + 1 >= maxGenDepth) {
                    return false;
                }
            }

            boolean largeStem = !config.planted && random.nextFloat() < 0.06F;
            worldGenLevel.setBlock(originPos, Blocks.AIR.defaultBlockState(), 4);
            this.placeStem(worldGenLevel, random, config, placePos, height, largeStem);
            this.placeHat(worldGenLevel, random, config, placePos, height, largeStem);
            return true;
        }
    }

    private static boolean isReplaceable(WorldGenLevel level, BlockPos pos, HugeFungusConfiguration config, boolean checkReplaceableBlocks) {
        if (level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::canBeReplaced)) {
            return true;
        } else {
            return checkReplaceableBlocks && config.replaceableBlocks.test(level, pos);
        }
    }

    private void placeStem(WorldGenLevel level, RandomSource random, HugeFungusConfiguration config, BlockPos pos, int height, boolean largeStem) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockState stemState = config.stemState;
        int stemRadius = largeStem ? 1 : 0;

        for (int x = -stemRadius; x <= stemRadius; ++x) {
            for (int z = -stemRadius; z <= stemRadius; ++z) {
                boolean isCorner = largeStem && Mth.abs(x) == stemRadius && Mth.abs(z) == stemRadius;

                for (int y = 0; y < height; ++y) {
                    mutableBlockPos.setWithOffset(pos, x, y, z);
                    if (isReplaceable(level, mutableBlockPos, config, true)) {
                        if (config.planted) {
                            if (!level.getBlockState(mutableBlockPos.below()).isAir()) {
                                level.destroyBlock(mutableBlockPos, true);
                            }
                            level.setBlock(mutableBlockPos, stemState, 3);
                        } else if (isCorner) {
                            if (random.nextFloat() < 0.1F) {
                                this.setBlock(level, mutableBlockPos, stemState);
                            }
                        } else {
                            this.setBlock(level, mutableBlockPos, stemState);
                        }
                    }
                }
            }
        }
    }

    private void placeHat(WorldGenLevel level, RandomSource random, HugeFungusConfiguration config, BlockPos pos, int height, boolean largeStem) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        boolean beardValid = config.hatState.is(JNETags.Blocks.WART_BEARD_FEATURE_VALID);
        int hatHeight = Math.min(random.nextInt(1 + height / 3) + 5, height);
        int stemHeight = height - hatHeight;

        for (int y = stemHeight; y <= height; ++y) {
            int hatRadius = y < height - random.nextInt(3) ? 2 : 1;
            if (hatHeight > 8 && y < stemHeight + 4) {
                hatRadius = 3;
            }

            if (largeStem) {
                ++hatRadius;
            }

            for (int x = -hatRadius; x <= hatRadius; ++x) {
                for (int z = -hatRadius; z <= hatRadius; ++z) {
                    boolean isEdgeX = x == -hatRadius || x == hatRadius;
                    boolean isEdgeZ = z == -hatRadius || z == hatRadius;
                    boolean isCenter = !isEdgeX && !isEdgeZ && y != height;
                    boolean isCorner = isEdgeX && isEdgeZ;
                    boolean lowHat = y < stemHeight + 3;
                    mutableBlockPos.setWithOffset(pos, x, y, z);
                    if (isReplaceable(level, mutableBlockPos, config, false)) {
                        if (config.planted && !level.getBlockState(mutableBlockPos.below()).isAir()) {
                            level.destroyBlock(mutableBlockPos, true);
                        }

                        if (lowHat) {
                            if (!isCenter) {
                                this.placeHatDropBlock(level, random, mutableBlockPos, config.hatState, beardValid);
                            }
                        } else if (isCenter) {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.1F, 0.2F, beardValid ? 0.1F : 0.0F);
                        } else if (isCorner) {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.01F, 0.7F, beardValid ? 0.083F : 0.0F);
                        } else {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 5.0E-4F, 0.98F, beardValid ? 0.07F : 0.0F);
                        }
                    }
                }
            }
        }
    }

    private void placeHatBlock(LevelAccessor level, RandomSource random, HugeFungusConfiguration config, BlockPos.MutableBlockPos pos, float probability1, float probability2, float probability3) {
        if (random.nextFloat() < probability1) {
            this.setBlock(level, pos, config.decorState);
        } else if (random.nextFloat() < probability2) {
            this.setBlock(level, pos, config.hatState);
            tryPlaceBeard(pos, level);
        }
    }

    private void placeHatDropBlock(LevelAccessor level, RandomSource random, BlockPos pos, BlockState state, boolean beardValid) {
        if (level.getBlockState(pos.below()).is(state.getBlock())) {
            this.setBlock(level, pos, state);
        } else if (random.nextFloat() < 0.15) {
            this.setBlock(level, pos, state);
        } else if (level.getBlockState(pos.above()).is(state.getBlock())) {
            tryPlaceBeard(pos, level);
        }
    }

    private static void tryPlaceBeard(BlockPos pos, LevelAccessor level) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.DOWN);
        if (level.isEmptyBlock(mutableBlockPos)) {
            level.setBlock(mutableBlockPos, JNEBlocks.WARPED_WART_BEARD.get().defaultBlockState(), 2);
        }
    }
}
