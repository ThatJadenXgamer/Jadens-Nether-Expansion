package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class AshyBasaltBlock extends PillarBlock {
    public AshyBasaltBlock(Settings settings) {
        super(settings);
    }

    //Checks if the above block is not White Ash it will revert to Basalt
    @SuppressWarnings("deprecation")
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        BlockState up = world.getBlockState(pos.up());
        if (up.isIn(ModTags.Blocks.WHITE_ASH)) {
            AshyBasaltBlock.revertToBasalt(world, pos);
        }
    }

    /*
    Checks if the above block is not White Ash it will revert to Basalt
    but this time as a Neighbour Update
    */
    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !neighborState.isIn(ModTags.Blocks.WHITE_ASH)) {
            AshyBasaltBlock.revertToBasalt(world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    //does the reverting magic!
    private static void revertToBasalt(WorldAccess world, BlockPos pos) {
        world.setBlockState(pos, Blocks.BASALT.getDefaultState(), NOTIFY_LISTENERS);
    }

    /*
    Checks if the above block is not White Ash it will revert to Basalt
    but this time as a Placement State
    */

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        if (!blockState.isIn(ModTags.Blocks.WHITE_ASH)) {
            AshyBasaltBlock.revertToBasalt(ctx.getWorld(), ctx.getBlockPos());
        }
        return super.getPlacementState(ctx);
    }

    /*
    You aren't supposed to hold this item, sort of like moving pistons
    This prevents that from happening and also
    tricks you into thinking that it's just normal basalt
    */
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(Items.BASALT);
    }
}
