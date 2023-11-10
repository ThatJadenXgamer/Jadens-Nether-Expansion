package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class NetheritePlatedBlock extends Block {
    private final BlockState rustyState;

    public NetheritePlatedBlock(Settings settings, Block rustyState) {
        super(settings);
        this.rustyState = rustyState.getDefaultState();
    }

    private static boolean rustsIn(BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = blockView.getBlockState(blockPos);
        return shouldRust(blockView, blockPos, blockState) ? this.rustyState : super.getPlacementState(ctx);
    }

    private static boolean shouldRust(BlockView world, BlockPos pos, BlockState state) {
        return rustsIn(state) || rustsOnAnySide(world, pos);
    }

    private static boolean rustsOnAnySide(BlockView world, BlockPos pos) {
        boolean bl = false;
        BlockPos.Mutable mutable = pos.mutableCopy();
        Direction[] var4 = Direction.values();

        for (Direction direction : var4) {
            BlockState blockState = world.getBlockState(mutable);
            if (direction != Direction.DOWN || rustsIn(blockState)) {
                mutable.set(pos, direction);
                blockState = world.getBlockState(mutable);
                if (rustsIn(blockState) && !blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return rustsOnAnySide(world, pos) ? this.rustyState : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
