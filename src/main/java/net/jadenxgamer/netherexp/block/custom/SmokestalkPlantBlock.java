package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.misc_registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SmokestalkPlantBlock
extends AbstractPlantBlock {

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
        AbstractPlantStemBlock abstractPlantStemBlock = this.getStem();
        if (direction == this.growthDirection && !neighborState.isOf(this) && !neighborState.isOf(abstractPlantStemBlock)) {
            return this.copyState(state, abstractPlantStemBlock.getRandomGrowthState(world));
        }
        if (this.tickWater) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (!blockState.isIn(ModTags.Blocks.SMOKESTALK_CONNECTS_TO)) {
            return state.with(LEAVES,true);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEAVES);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.SMOKESTALK;
    }
}
