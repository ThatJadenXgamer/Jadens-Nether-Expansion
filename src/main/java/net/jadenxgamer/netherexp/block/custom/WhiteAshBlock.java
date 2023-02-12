package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.misc_registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class WhiteAshBlock extends LayerBlock{
    public WhiteAshBlock(Settings settings) {
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

}