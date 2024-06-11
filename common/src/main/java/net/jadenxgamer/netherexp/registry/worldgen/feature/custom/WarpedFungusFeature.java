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

    public WarpedFungusFeature(Codec<HugeFungusConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeFungusConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        ChunkGenerator chunk = context.chunkGenerator();
        HugeFungusConfiguration hugeFungusConfiguration = context.config();
        Block block = hugeFungusConfiguration.validBaseState.getBlock();
        BlockPos pos2 = null;
        BlockState blockState = worldGenLevel.getBlockState(pos.below());
        if (blockState.is(block)) {
            pos2 = pos;
        }

        if (pos2 == null) {
            return false;
        } else {
            int i = Mth.nextInt(random, 4, 13);
            if (random.nextInt(12) == 0) {
                i *= 2;
            }

            if (!hugeFungusConfiguration.planted) {
                int j = chunk.getGenDepth();
                if (pos2.getY() + i + 1 >= j) {
                    return false;
                }
            }

            boolean bl = !hugeFungusConfiguration.planted && random.nextFloat() < 0.06F;
            worldGenLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
            this.placeStem(worldGenLevel, random, hugeFungusConfiguration, pos2, i, bl);
            this.placeHat(worldGenLevel, random, hugeFungusConfiguration, pos2, i, bl);
            return true;
        }
    }

    private static boolean isReplaceable(WorldGenLevel level, BlockPos pos, HugeFungusConfiguration config, boolean bl) {
        if (level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::canBeReplaced)) {
            return true;
        } else {
            return bl && config.replaceableBlocks.test(level, pos);
        }
    }

    private void placeStem(WorldGenLevel level, RandomSource random, HugeFungusConfiguration config, BlockPos pos, int i, boolean bl) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockState blockState = config.stemState;
        int j = bl ? 1 : 0;

        for(int k = -j; k <= j; ++k) {
            for(int l = -j; l <= j; ++l) {
                boolean bl2 = bl && Mth.abs(k) == j && Mth.abs(l) == j;

                for(int m = 0; m < i; ++m) {
                    mutableBlockPos.setWithOffset(pos, k, m, l);
                    if (isReplaceable(level, mutableBlockPos, config, true)) {
                        if (config.planted) {
                            if (!level.getBlockState(mutableBlockPos.below()).isAir()) {
                                level.destroyBlock(mutableBlockPos, true);
                            }

                            level.setBlock(mutableBlockPos, blockState, 3);
                        } else if (bl2) {
                            if (random.nextFloat() < 0.1F) {
                                this.setBlock(level, mutableBlockPos, blockState);
                            }
                        } else {
                            this.setBlock(level, mutableBlockPos, blockState);
                        }
                    }
                }
            }
        }

    }

    private void placeHat(WorldGenLevel level, RandomSource random, HugeFungusConfiguration config, BlockPos pos, int i, boolean bl) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        boolean beardValid = config.hatState.is(JNETags.Blocks.WART_BEARD_FEATURE_VALID);
        int j = Math.min(random.nextInt(1 + i / 3) + 5, i);
        int k = i - j;

        for(int l = k; l <= i; ++l) {
            int m = l < i - random.nextInt(3) ? 2 : 1;
            if (j > 8 && l < k + 4) {
                m = 3;
            }

            if (bl) {
                ++m;
            }

            for(int n = -m; n <= m; ++n) {
                for(int o = -m; o <= m; ++o) {
                    boolean bl3 = n == -m || n == m;
                    boolean bl4 = o == -m || o == m;
                    boolean bl5 = !bl3 && !bl4 && l != i;
                    boolean bl6 = bl3 && bl4;
                    boolean bl7 = l < k + 3;
                    mutableBlockPos.setWithOffset(pos, n, l, o);
                    if (isReplaceable(level, mutableBlockPos, config, false)) {
                        if (config.planted && !level.getBlockState(mutableBlockPos.below()).isAir()) {
                            level.destroyBlock(mutableBlockPos, true);
                        }

                        if (bl7) {
                            if (!bl5) {
                                this.placeHatDropBlock(level, random, mutableBlockPos, config.hatState, beardValid);
                            }
                        } else if (bl5) {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.1F, 0.2F, beardValid ? 0.1F : 0.0F);
                        } else if (bl6) {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.01F, 0.7F, beardValid ? 0.083F : 0.0F);
                        } else {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 5.0E-4F, 0.98F, beardValid ? 0.07F : 0.0F);
                        }
                    }
                }
            }
        }

    }

    private void placeHatBlock(LevelAccessor level, RandomSource random, HugeFungusConfiguration context, BlockPos.MutableBlockPos pos, float f, float g, float h) {
        if (random.nextFloat() < f) {
            this.setBlock(level, pos, context.decorState);
        }
        else if (random.nextFloat() < g) {
            this.setBlock(level, pos, context.hatState);
            tryPlaceBeard(pos, level);
        }
    }

    private void placeHatDropBlock(LevelAccessor level, RandomSource random, BlockPos pos, BlockState state, boolean bl) {
        if (level.getBlockState(pos.below()).is(state.getBlock())) {
            this.setBlock(level, pos, state);
        }
        else if ((double)random.nextFloat() < 0.15) {
            this.setBlock(level, pos, state);
        }
        else if (level.getBlockState(pos.above()).is(state.getBlock())) {
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
