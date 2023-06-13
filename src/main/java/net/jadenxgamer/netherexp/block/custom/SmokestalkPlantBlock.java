package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;

public class SmokestalkPlantBlock
extends AbstractStalkBlock {

    public static final BooleanProperty LEAVES = BooleanProperty.of("leaves");

    public static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 16, 11);

    public SmokestalkPlantBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEAVES, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        AbstractStalkStemBlock abstractStalkStemBlock = this.getStem();
        if (direction == this.growthDirection && !neighborState.isOf(this) && !neighborState.isOf(abstractStalkStemBlock)) {
            return this.copyState(state, abstractStalkStemBlock.getRandomGrowthState(world));
        }
        if (this.tickWater) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (blockState.isOf(Blocks.GRAY_CONCRETE)) {
            return state.with(LEAVES, true);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEAVES);
    }

    @Override
    protected AbstractStalkStemBlock getStem() {
        return (AbstractStalkStemBlock) ModBlocks.SMOKESTALK;
    }
}
