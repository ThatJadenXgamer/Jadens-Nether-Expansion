package net.jadenxgamer.netherexp.core.worldgen.feature;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.core.worldgen.feature.config.JNEHugeFungusFeatureConfiguration;
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

public class JNEHugeFungusFeature extends Feature<JNEHugeFungusFeatureConfiguration> {

    // Based on Vanilla HugeFungusFeature with cleaner variables, more configurable bits and beard logic

    public JNEHugeFungusFeature(Codec<JNEHugeFungusFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<JNEHugeFungusFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        JNEHugeFungusFeatureConfiguration config = context.config();
        Block baseBlock = config.validBaseState().getBlock();
        BlockPos placePos = null;
        BlockState baseBlockState = level.getBlockState(origin.below());

        if (baseBlockState.is(baseBlock)) {
            placePos = origin;
        }

        if (placePos == null) {
            return false;
        } else {
            int height = Mth.nextInt(random, config.minHeight(), config.maxHeight());
            if (random.nextInt(12) == 0) {
                height *= config.bonusHeight();
            }

            if (!config.planted()) {
                int maxGenDepth = chunkGenerator.getGenDepth();
                if (placePos.getY() + height + 1 >= maxGenDepth) {
                    return false;
                }
            }

            boolean largeStem = !config.planted() && random.nextFloat() < 0.06F;
            level.setBlock(origin, Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
            this.placeStem(level, random, config, placePos, height, largeStem);
            this.placeHat(level, random, config, placePos, height, largeStem);
            return true;
        }
    }

    private static boolean isReplaceable(WorldGenLevel level, BlockPos pos, JNEHugeFungusFeatureConfiguration config, boolean checkReplaceableBlocks) {
        if (level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::canBeReplaced)) {
            return true;
        } else {
            return checkReplaceableBlocks && config.replaceableBlocks().test(level, pos);
        }
    }

    private void placeStem(WorldGenLevel level, RandomSource random, JNEHugeFungusFeatureConfiguration config, BlockPos pos, int height, boolean largeStem) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockState stemState = config.stemState();
        int stemRadius = largeStem ? 1 : 0;

        for (int x = -stemRadius; x <= stemRadius; ++x) {
            for (int z = -stemRadius; z <= stemRadius; ++z) {
                boolean isCorner = largeStem && Mth.abs(x) == stemRadius && Mth.abs(z) == stemRadius;

                for (int y = 0; y < height; ++y) {
                    mutableBlockPos.setWithOffset(pos, x, y, z);
                    if (isReplaceable(level, mutableBlockPos, config, true)) {
                        if (config.planted()) {
                            if (!level.getBlockState(mutableBlockPos.below()).isAir()) {
                                level.destroyBlock(mutableBlockPos, true);
                            }
                            level.setBlock(mutableBlockPos, stemState, Block.UPDATE_ALL);
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

    private void placeHat(WorldGenLevel level, RandomSource random, JNEHugeFungusFeatureConfiguration config, BlockPos pos, int height, boolean largeStem) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
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
                        if (config.planted() && !level.getBlockState(mutableBlockPos.below()).isAir()) {
                            level.destroyBlock(mutableBlockPos, true);
                        }

                        if (lowHat) {
                            if (!isCenter) {
                                this.placeHatDropBlock(level, random, config, mutableBlockPos, config.hatState());
                            }
                        } else if (isCenter) {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.1F, 0.2F);
                        } else if (isCorner) {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.01F, 0.7F);
                        } else {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 5.0E-4F, 0.98F);
                        }
                    }
                }
            }
        }
    }

    private void placeHatBlock(LevelAccessor level, RandomSource random, JNEHugeFungusFeatureConfiguration config, BlockPos.MutableBlockPos pos, float probability1, float probability2) {
        if (random.nextFloat() < probability1) {
            this.setBlock(level, pos, config.decorState());
        } else if (random.nextFloat() < probability2) {
            this.setBlock(level, pos, config.hatState());
            tryPlaceBeard(pos, level, config);
        }
    }

    private void placeHatDropBlock(LevelAccessor level, RandomSource random, JNEHugeFungusFeatureConfiguration config, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos.below()).is(state.getBlock())) {
            this.setBlock(level, pos, state);
        } else if (random.nextFloat() < 0.15) {
            this.setBlock(level, pos, state);
        } else if (level.getBlockState(pos.above()).is(state.getBlock())) {
            tryPlaceBeard(pos, level, config);
        }
    }

    private static void tryPlaceBeard(BlockPos pos, LevelAccessor level, JNEHugeFungusFeatureConfiguration config) {
        if (config.beardState().isEmpty()) return;
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.DOWN);
        if (level.isEmptyBlock(mutableBlockPos) && level.isEmptyBlock(mutableBlockPos.below()) && level.getBlockState(mutableBlockPos.above()).is(config.hatState().getBlock())) {
            level.setBlock(mutableBlockPos, config.beardState().get(), Block.UPDATE_CLIENTS);
        }
    }
}
