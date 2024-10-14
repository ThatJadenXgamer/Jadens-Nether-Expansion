package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SoulSoilLayerBlock extends LayerBlock {
    
    public SoulSoilLayerBlock(Properties properties) {
        super(properties, 0);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.is(JNETags.Blocks.SOUL_LAYER_CANNOT_SURVIVE_ON)) {
            return false;
        } else if (belowState.is(JNETags.Blocks.SOUL_LAYER_CAN_SURVIVE_ON)) {
            return true;
        } else {
            return Block.isFaceFull(belowState.getCollisionShape(level, pos.below()), Direction.UP) || belowState.is(this) && belowState.getValue(LAYERS) == 8;
        }
    }
}
