package net.jadenxgamer.netherexp.registry.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SoulMagmaClusterFeature extends Feature<NoneFeatureConfiguration> {

    public SoulMagmaClusterFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    // Taken from Vanilla's GlowstoneFeature.class because for whatever reason it's parameters are hardcoded and not using a configuration
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        BlockPos blockPos = featurePlaceContext.origin();
        RandomSource randomSource = featurePlaceContext.random();
        if (!worldGenLevel.isEmptyBlock(blockPos)) {
            return false;
        } else {
            BlockState blockState = worldGenLevel.getBlockState(blockPos.above());
            if (!blockState.is(Blocks.SOUL_SAND) && !blockState.is(Blocks.SOUL_SOIL) && !blockState.is(JNEBlocks.PALE_SOUL_SLATE.get())) {
                return false;
            } else {
                worldGenLevel.setBlock(blockPos, JNEBlocks.SOUL_MAGMA_BLOCK.get().defaultBlockState(), 2);

                for(int i = 0; i < 1500; ++i) {
                    BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(8) - randomSource.nextInt(8), -randomSource.nextInt(12), randomSource.nextInt(8) - randomSource.nextInt(8));
                    if (worldGenLevel.getBlockState(blockPos2).isAir()) {
                        int j = 0;
                        Direction[] var9 = Direction.values();

                        for (Direction direction : var9) {
                            if (worldGenLevel.getBlockState(blockPos2.relative(direction)).is(JNEBlocks.SOUL_MAGMA_BLOCK.get())) {
                                ++j;
                            }

                            if (j > 1) {
                                break;
                            }
                        }

                        if (j == 1) {
                            worldGenLevel.setBlock(blockPos2, JNEBlocks.SOUL_MAGMA_BLOCK.get().defaultBlockState(), 2);
                        }
                    }
                }

                return true;
            }
        }
    }
}
