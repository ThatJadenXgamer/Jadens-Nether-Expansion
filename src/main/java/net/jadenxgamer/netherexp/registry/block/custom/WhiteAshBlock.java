package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class WhiteAshBlock extends Block{
    public WhiteAshBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && state.isOf(Blocks.BASALT)) {
            WhiteAshBlock.ashyBasalt(world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public static void ashyBasalt(WorldAccess world, BlockPos pos) {
        world.setBlockState(pos.down(), ModBlocks.ASHY_BASALT.getDefaultState(), NOTIFY_LISTENERS);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (blockState.isOf(Blocks.BASALT)) {
            WhiteAshBlock.ashyBasalt(ctx.getWorld(), ctx.getBlockPos());
        }
        return super.getPlacementState(ctx);
    }
}
