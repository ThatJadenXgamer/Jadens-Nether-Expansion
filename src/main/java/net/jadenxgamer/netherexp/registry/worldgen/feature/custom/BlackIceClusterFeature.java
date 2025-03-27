package net.jadenxgamer.netherexp.registry.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class BlackIceClusterFeature extends Feature<DripstoneClusterConfiguration> {
    public BlackIceClusterFeature(Codec<DripstoneClusterConfiguration> pCodec) {
        super(pCodec);
    }

    public boolean place(FeaturePlaceContext<DripstoneClusterConfiguration> pContext) {
        WorldGenLevel level = pContext.level();
        BlockPos origin = pContext.origin();
        DripstoneClusterConfiguration config = pContext.config();
        RandomSource random = pContext.random();
        if (!isEmptyOrWater(level, origin)) {
            return false;
        } else {
            int clusterHeight = config.height.sample(random);
            float wetness = config.wetness.sample(random);
            float density = config.density.sample(random);
            int radiusX = config.radius.sample(random);
            int radiusZ = config.radius.sample(random);

            for(int dx = -radiusX; dx <= radiusX; ++dx) {
                for(int dz = -radiusZ; dz <= radiusZ; ++dz) {
                    double stalagmiteStalactiteChance = this.getChanceOfStalagmiteOrStalactite(radiusX, radiusZ, dx, dz, config);
                    BlockPos columnPos = origin.offset(dx, 0, dz);
                    this.placeColumn(level, random, columnPos, dx, dz, wetness, stalagmiteStalactiteChance, clusterHeight, density, config);
                }
            }

            return true;
        }
    }

    private void placeColumn(WorldGenLevel pLevel, RandomSource pRandom, BlockPos pPos, int pX, int pZ, float pWetness, double pChance, int pHeight, float pDensity, DripstoneClusterConfiguration pConfig) {
        Optional<Column> column = Column.scan(pLevel, pPos, pConfig.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater);
        if (column.isPresent()) {
            OptionalInt ceilingOptional = column.get().getCeiling();
            OptionalInt floorOptional = column.get().getFloor();
            if (ceilingOptional.isPresent() || floorOptional.isPresent()) {
                boolean shouldPlaceWater = pRandom.nextFloat() < pWetness;
                Column adjustedColumn;
                if (shouldPlaceWater && floorOptional.isPresent() && this.canPlacePool(pLevel, pPos.atY(floorOptional.getAsInt()))) {
                    int floorY = floorOptional.getAsInt();
                    adjustedColumn = column.get().withFloor(OptionalInt.of(floorY - 1));
                    pLevel.setBlock(pPos.atY(floorY), Blocks.WATER.defaultBlockState(), 2);
                } else {
                    adjustedColumn = column.get();
                }

                OptionalInt newFloorOptional = adjustedColumn.getFloor();
                boolean shouldPlaceCeilingStalactite = pRandom.nextDouble() < pChance;
                int stalactiteHeight;
                int stalagmiteHeight;
                if (ceilingOptional.isPresent() && shouldPlaceCeilingStalactite && !this.isLava(pLevel, pPos.atY(ceilingOptional.getAsInt()))) {
                    int thickness = pConfig.dripstoneBlockLayerThickness.sample(pRandom);
                    //this.replaceBlocksWithBlackIceBlocks(pLevel, pPos.atY(ceilingOptional.getAsInt()), thickness, Direction.UP);
                    int maxIcicleHeight;
                    if (newFloorOptional.isPresent()) {
                        maxIcicleHeight = Math.min(pHeight, ceilingOptional.getAsInt() - newFloorOptional.getAsInt());
                    } else {
                        maxIcicleHeight = pHeight;
                    }

                    stalactiteHeight = this.getDripstoneHeight(pRandom, pX, pZ, pDensity, maxIcicleHeight, pConfig);
                } else {
                    stalactiteHeight = 0;
                }

                boolean shouldPlaceFloorStalagmite = pRandom.nextDouble() < pChance;
                if (newFloorOptional.isPresent() && shouldPlaceFloorStalagmite && !this.isLava(pLevel, pPos.atY(newFloorOptional.getAsInt()))) {
                    int thickness = pConfig.dripstoneBlockLayerThickness.sample(pRandom);
                    //this.replaceBlocksWithBlackIceBlocks(pLevel, pPos.atY(newFloorOptional.getAsInt()), thickness, Direction.DOWN);
                    if (ceilingOptional.isPresent()) {
                        stalagmiteHeight = Math.max(0, stalactiteHeight + Mth.randomBetweenInclusive(pRandom, -pConfig.maxStalagmiteStalactiteHeightDiff, pConfig.maxStalagmiteStalactiteHeightDiff));
                    } else {
                        stalagmiteHeight = this.getDripstoneHeight(pRandom, pX, pZ, pDensity, pHeight, pConfig);
                    }
                } else {
                    stalagmiteHeight = 0;
                }

                int finalStalactiteHeight;
                int finalStalagmiteHeight;
                if (ceilingOptional.isPresent() && newFloorOptional.isPresent() && ceilingOptional.getAsInt() - stalactiteHeight <= newFloorOptional.getAsInt() + stalagmiteHeight) {
                    int floorYPosition = newFloorOptional.getAsInt();
                    int ceilingYPosition = ceilingOptional.getAsInt();
                    int stalactiteBase = Math.max(ceilingYPosition - stalactiteHeight, floorYPosition + 1);
                    int stalagmiteBase = Math.min(floorYPosition + stalagmiteHeight, ceilingYPosition - 1);
                    int midPoint = Mth.randomBetweenInclusive(pRandom, stalactiteBase, stalagmiteBase + 1);
                    int stalagmiteTipY = midPoint - 1;
                    finalStalactiteHeight = ceilingYPosition - midPoint;
                    finalStalagmiteHeight = stalagmiteTipY - floorYPosition;
                } else {
                    finalStalactiteHeight = stalactiteHeight;
                    finalStalagmiteHeight = stalagmiteHeight;
                }

                boolean mergeTips = pRandom.nextBoolean() && finalStalactiteHeight > 0 && finalStalagmiteHeight > 0 && adjustedColumn.getHeight().isPresent() && finalStalactiteHeight + finalStalagmiteHeight == adjustedColumn.getHeight().getAsInt();
                if (ceilingOptional.isPresent()) {
                    if (finalStalactiteHeight > 0) {
                        placeBlackIceIfPossible(pLevel, pPos.atY(ceilingOptional.getAsInt()));
                    }
                    growBlackIcicle(pLevel, pPos.atY(ceilingOptional.getAsInt() - 1), Direction.DOWN, finalStalactiteHeight, mergeTips);
                }

                if (newFloorOptional.isPresent()) {
                    if (finalStalagmiteHeight > 0) {
                        placeBlackIceIfPossible(pLevel, pPos.atY(newFloorOptional.getAsInt()));
                    }
                    growBlackIcicle(pLevel, pPos.atY(newFloorOptional.getAsInt() + 1), Direction.UP, finalStalagmiteHeight, mergeTips);
                }
            }
        }
    }

    private boolean isLava(LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).is(Blocks.LAVA);
    }

    private int getDripstoneHeight(RandomSource pRandom, int pX, int pZ, float pChance, int pHeight, DripstoneClusterConfiguration pConfig) {
        if (pRandom.nextFloat() > pChance) {
            return 0;
        } else {
            int $$6 = Math.abs(pX) + Math.abs(pZ);
            float $$7 = (float)Mth.clampedMap((double)$$6, 0.0, (double)pConfig.maxDistanceFromCenterAffectingHeightBias, (double)pHeight / 2.0, 0.0);
            return (int)randomBetweenBiased(pRandom, 0.0F, (float)pHeight, $$7, (float)pConfig.heightDeviation);
        }
    }

    private boolean canPlacePool(WorldGenLevel pLevel, BlockPos pPos) {
        BlockState $$2 = pLevel.getBlockState(pPos);
        if (!$$2.is(Blocks.WATER) && !$$2.is(JNEBlocks.BLACK_ICE.get()) && !$$2.is(JNEBlocks.BLACK_ICICLE.get())) {
            if (!pLevel.getBlockState(pPos.above()).getFluidState().is(FluidTags.WATER)) {
                Iterator<Direction> var4 = Direction.Plane.HORIZONTAL.iterator();

                Direction $$3;
                do {
                    if (!var4.hasNext()) {
                        return this.canBeAdjacentToWater(pLevel, pPos.below());
                    }

                    $$3 = var4.next();
                } while (this.canBeAdjacentToWater(pLevel, pPos.relative($$3)));

            }
        }
        return false;
    }

    private boolean canBeAdjacentToWater(LevelAccessor pLevel, BlockPos pPos) {
        BlockState $$2 = pLevel.getBlockState(pPos);
        return $$2.is(BlockTags.BASE_STONE_OVERWORLD) || $$2.getFluidState().is(FluidTags.WATER);
    }

    private void replaceBlocksWithBlackIceBlocks(WorldGenLevel pLevel, BlockPos pPos, int pThickness, Direction pDirection) {
        BlockPos.MutableBlockPos $$4 = pPos.mutable();

        for(int $$5 = 0; $$5 < pThickness; ++$$5) {
            if (!placeBlackIceIfPossible(pLevel, $$4)) {
                return;
            }

            $$4.move(pDirection);
        }

    }

    protected static boolean placeBlackIceIfPossible(LevelAccessor pLevel, BlockPos pPos) {
        BlockState state = pLevel.getBlockState(pPos);
        if (state.is(JNETags.Blocks.BLACK_ICE_REPLACEABLE)) {
            pLevel.setBlock(pPos, JNEBlocks.BLACK_ICE.get().defaultBlockState(), 2);
            return true;
        } else {
            return false;
        }
    }

    private double getChanceOfStalagmiteOrStalactite(int pXRadius, int pZRadius, int pX, int pZ, DripstoneClusterConfiguration pConfig) {
        int $$5 = pXRadius - Math.abs(pX);
        int $$6 = pZRadius - Math.abs(pZ);
        int $$7 = Math.min($$5, $$6);
        return Mth.clampedMap((float)$$7, 0.0F, (float)pConfig.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, pConfig.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0F);
    }

    private static float randomBetweenBiased(RandomSource pRandom, float pMin, float pMax, float pMean, float pDeviation) {
        return ClampedNormalFloat.sample(pRandom, pMean, pDeviation, pMin, pMax);
    }

    protected static boolean isEmptyOrWater(LevelAccessor pLevel, BlockPos pPos) {
        return pLevel.isStateAtPosition(pPos, DripstoneUtils::isEmptyOrWater);
    }

    protected static void growBlackIcicle(LevelAccessor pLevel, BlockPos pPos, Direction pDirection, int pHeight, boolean pMergeTip) {
        if (isBlackIceBaseOrLava(pLevel.getBlockState(pPos.relative(pDirection.getOpposite())))) {
            BlockPos.MutableBlockPos $$5 = pPos.mutable();
            buildBaseToTipColumn(pDirection, pHeight, pMergeTip, (p_277326_) -> {
                if (p_277326_.is(JNEBlocks.BLACK_ICICLE.get())) {
                    p_277326_ = p_277326_.setValue(PointedDripstoneBlock.WATERLOGGED, pLevel.isWaterAt($$5));
                }

                pLevel.setBlock($$5, p_277326_, 2);
                $$5.move(pDirection);
            });
        }
    }

    public static boolean isBlackIceBaseOrLava(BlockState pState) {
        return isBlackIceBase(pState) || pState.is(Blocks.LAVA);
    }

    public static boolean isBlackIceBase(BlockState pState) {
        return pState.is(JNEBlocks.BLACK_ICE.get()) || pState.is(JNETags.Blocks.BLACK_ICE_REPLACEABLE);
    }

    protected static void buildBaseToTipColumn(Direction pDirection, int pHeight, boolean pMergeTip, Consumer<BlockState> pBlockSetter) {
        if (pHeight >= 3) {
            pBlockSetter.accept(createBlackIcicle(pDirection, DripstoneThickness.BASE));

            for(int $$4 = 0; $$4 < pHeight - 3; ++$$4) {
                pBlockSetter.accept(createBlackIcicle(pDirection, DripstoneThickness.MIDDLE));
            }
        }

        if (pHeight >= 2) {
            pBlockSetter.accept(createBlackIcicle(pDirection, DripstoneThickness.FRUSTUM));
        }

        if (pHeight >= 1) {
            pBlockSetter.accept(createBlackIcicle(pDirection, pMergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }

    }

    private static BlockState createBlackIcicle(Direction pDirection, DripstoneThickness pDripstoneThickness) {
        return JNEBlocks.BLACK_ICICLE.get().defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, pDirection).setValue(PointedDripstoneBlock.THICKNESS, pDripstoneThickness);
    }
}