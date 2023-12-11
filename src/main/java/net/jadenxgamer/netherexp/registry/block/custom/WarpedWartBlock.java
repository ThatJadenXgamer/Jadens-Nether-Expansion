package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WarpedWartBlock extends PlantBlock {

    public static final IntProperty AGE = Properties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    public static final VoxelShape AGE_0_TOP = Block.createCuboidShape(5, 7, 5, 11, 16, 11);
    public static final VoxelShape AGE_1_TOP = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_2_TOP = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_3_TOP = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
    public static final VoxelShape AGE_3_BOTTOM = Block.createCuboidShape(2, 5, 2, 14, 16, 14);

    public WarpedWartBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.UPPER));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(AGE)) {
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
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return AGE_3_BOTTOM;
        }
        return AGE_3_TOP;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        if (state.get(HALF) != DoubleBlockHalf.LOWER) {
            return this.canPlantDownBelow(world.getBlockState(blockPos));
        }
        else {
            BlockState blockState = world.getBlockState(pos.up());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.UPPER;
        }
    }

    protected boolean canPlantDownBelow(BlockState ceiling) {
        return ceiling.isOf(Blocks.SOUL_SAND);
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        int i = state.get(AGE);
        if (i == 3 && direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.UPPER == (direction == Direction.DOWN) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        }
        else {
            return doubleBlockHalf == DoubleBlockHalf.UPPER && direction == Direction.UP && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        BlockPos floor = pos.down();
        if (i < 2 && random.nextInt(10) == 0) {
            world.setBlockState(pos, state.with(AGE, i + 1), 2);
        }
        else if (i == 2 && world.getBlockState(floor).isAir()) {
            world.setBlockState(pos, state.with(AGE, i + 1), 2);
            world.setBlockState(floor, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(AGE, 3), 2);
        }
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.WARPED_WART);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }
}
