package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SoulSoilLayerBlock extends SnowLayerBlock {
    public SoulSoilLayerBlock (Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return false;
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState blockState2 = levelReader.getBlockState(blockPos.below());
        if (blockState2.is(JNETags.Blocks.SOUL_LAYER_CANNOT_SURVIVE_ON)) {
            return false;
        } else if (blockState2.is(JNETags.Blocks.SOUL_LAYER_CAN_SURVIVE_ON)) {
            return true;
        } else {
            return Block.isFaceFull(blockState2.getCollisionShape(levelReader, blockPos.below()), Direction.UP) || blockState2.is(this) && (Integer)blockState2.getValue(LAYERS) == 8;
        }
    }
}