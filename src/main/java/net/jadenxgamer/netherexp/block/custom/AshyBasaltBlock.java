package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.misc_registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class AshyBasaltBlock extends PillarBlock {
    public AshyBasaltBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.isIn(ModTags.Blocks.WHITE_ASH)) {
            AshyBasaltBlock.revertToBasalt(world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public static void revertToBasalt(WorldAccess world, BlockPos pos) {
        world.setBlockState(pos, Blocks.BASALT.getDefaultState(), NOTIFY_LISTENERS);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        if (!blockState.isIn(ModTags.Blocks.WHITE_ASH)) {
            AshyBasaltBlock.revertToBasalt(ctx.getWorld(), ctx.getBlockPos());
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(Items.BASALT);
    }
}
