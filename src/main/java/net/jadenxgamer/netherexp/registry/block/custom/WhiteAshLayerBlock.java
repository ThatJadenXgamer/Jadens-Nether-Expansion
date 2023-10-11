package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class WhiteAshLayerBlock extends LayerBlock{
    public WhiteAshLayerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isIn(ModTags.Blocks.ASH_CANNOT_SURVIVE_ON)) {
            return false;
        }
        if (blockState.isIn(ModTags.Blocks.ASH_CAN_SURVIVE_ON)) {
            return true;
        }
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos.down()), Direction.UP) || blockState.isOf(this) && blockState.get(LAYERS) == 8;
    }

    /*
    While also doing LayerBlock stuff it checks for blocks underneath
    If it happens to be Basalt then it changes it to Ashy Basalt
    */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (direction == Direction.DOWN && state.isOf(Blocks.BASALT)) {
            WhiteAshLayerBlock.ashyBasalt(world, pos);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // This does the changing magic
    public static void ashyBasalt(WorldAccess world, BlockPos pos) {
        world.setBlockState(pos.down(), ModBlocks.ASHY_BASALT.getDefaultState(), NOTIFY_LISTENERS);
    }

    /*
    While also doing LayerBlock stuff it checks for blocks underneath
    If it happens to be Basalt then it changes it to Ashy Basalt
    but this time as a placement state!
    */
    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        BlockState blockState2 = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (blockState.isOf(this)) {
            int i = blockState.get(LAYERS);
            return blockState.with(LAYERS, Math.min(8, i + 1));
        }
        if (blockState2.isOf(Blocks.BASALT)) {
            WhiteAshLayerBlock.ashyBasalt(ctx.getWorld(), ctx.getBlockPos());
        }
        return super.getPlacementState(ctx);
    }
}
