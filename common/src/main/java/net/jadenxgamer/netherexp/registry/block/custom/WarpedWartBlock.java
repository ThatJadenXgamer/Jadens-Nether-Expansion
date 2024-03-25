package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WarpedWartBlock extends BushBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final VoxelShape AGE_0_TOP = Block.box(5, 7, 5, 11, 16, 11);
    public static final VoxelShape AGE_1_TOP = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_2_TOP = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_3_TOP = Block.box(2, 0, 2, 14, 16, 14);
    public static final VoxelShape AGE_3_BOTTOM = Block.box(2, 5, 2, 14, 16, 14);

    public WarpedWartBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.UPPER));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        switch (blockState.getValue(AGE)) {
            default: {
                return AGE_0_TOP;
            }
            case 1: {
                return AGE_1_TOP;
            }
            case 2: {
                return AGE_2_TOP;
            }
            case 3:
        }
        if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return AGE_3_BOTTOM;
        }
        return AGE_3_TOP;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos upPos = blockPos.above();
        if (blockState.getValue(HALF) != DoubleBlockHalf.LOWER) {
            return this.canPlantDownBelow(levelReader.getBlockState(upPos));
        }
        else {
            BlockState upState = levelReader.getBlockState(upPos);
            return upState.is(this) && blockState.getValue(HALF) == DoubleBlockHalf.UPPER;
        }
    }
    protected boolean canPlantDownBelow(BlockState ceiling) {
        return ceiling.is(Blocks.SOUL_SAND);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(AGE) < 3;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        int i = blockState.getValue(AGE);
        if (i == 3 && direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.UPPER == (direction == Direction.DOWN) && (!blockState2.is(this) || blockState2.getValue(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.defaultBlockState();
        }
        else {
            return doubleBlockHalf == DoubleBlockHalf.UPPER && direction == Direction.UP && !blockState.canSurvive(levelAccessor, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int i = blockState.getValue(AGE);
        BlockPos floor = blockPos.below();
        if (i < 2 && randomSource.nextInt(10) == 0) {
            serverLevel.setBlock(blockPos, blockState.setValue(AGE, i + 1), 2);
        }
        else if (i == 2 && serverLevel.getBlockState(floor).isAir()) {
            serverLevel.setBlock(blockPos, blockState.setValue(AGE, i + 1), 2);
            serverLevel.setBlock(floor, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, 3), 2);
        }
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(JNEItems.WARPED_WART.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }
}
