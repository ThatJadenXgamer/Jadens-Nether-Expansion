package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SoulTorchflowerCropBlock extends TorchflowerCropBlock {

    public SoulTorchflowerCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForAge(int age) {
        return age == 2 ? JNEBlocks.SOUL_TORCHFLOWER.get().defaultBlockState() : this.defaultBlockState().setValue(this.getAgeProperty(), age);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(JNETags.Blocks.SOUL_CROP_MUTATION_BLOCKS);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return this.mayPlaceOn(belowState, level, belowPos);
    }
}
