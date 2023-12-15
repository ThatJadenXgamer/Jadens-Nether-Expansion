package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class BasaltBlock extends PillarBlock {
    public static final BooleanProperty ASHY = BooleanProperty.of("ashy");

    public BasaltBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ASHY, false).with(AXIS, Direction.Axis.Y));
    }

    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP ? state.with(ASHY, isAsh(neighborState)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        return this.getDefaultState().with(ASHY, isAsh(blockState)).with(AXIS, ctx.getSide().getAxis());
    }

    private static boolean isAsh(BlockState state) {
        return state.isIn(ModTags.Blocks.WHITE_ASH);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ASHY, AXIS);
    }
}
