package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WhiteAshBlock extends LayerBlock {
    public WhiteAshBlock(Properties properties) {
        super(properties, 0);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.is(JNETags.Blocks.ASH_CANNOT_SURVIVE_ON)) {
            return false;
        } else if (belowState.is(JNETags.Blocks.ASH_CAN_SURVIVE_ON)) {
            return true;
        } else {
            return Block.isFaceFull(belowState.getCollisionShape(level, pos.below()), Direction.UP) || belowState.is(this) && (Integer)belowState.getValue(LAYERS) == 8;
        }
    }
}
