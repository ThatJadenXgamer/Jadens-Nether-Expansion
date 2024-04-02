package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class IgneousReeds extends TallFlowerBlock {
    public IgneousReeds(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return floor.is(JNETags.Blocks.IGNEOUS_REEDS_PLANTABLE_ON) || super.mayPlaceOn(floor, level, pos);
    }
}
