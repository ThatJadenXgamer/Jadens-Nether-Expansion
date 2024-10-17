package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TorchflowerCropBlock.class)
public abstract class TorchflowerCropBlockMixin extends CropBlock {
    public TorchflowerCropBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(JNETags.Blocks.SOUL_SAND_BLOCKS) || super.mayPlaceOn(state, level, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        BlockState floor = level.getBlockState(pos.below());
        if (state.is(Blocks.TORCHFLOWER_CROP) && floor.is(JNETags.Blocks.SOUL_SAND_BLOCKS)) {
            level.setBlock(pos, JNEBlocks.SOUL_TORCHFLOWER_CROP.get().defaultBlockState(), UPDATE_ALL);
        }
    }
}
