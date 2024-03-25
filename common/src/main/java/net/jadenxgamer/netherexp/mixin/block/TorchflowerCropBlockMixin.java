package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
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
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(JNETags.Blocks.SOUL_SAND_BLOCKS) || super.mayPlaceOn(blockState, blockGetter, blockPos);
    }

    //TODO: Reimplement Soul Torchflower
//    @SuppressWarnings("deprecation")
//    @Override
//    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
//        BlockState floor = world.getBlockState(pos.down());
//        if (floor.isIn(JNETags.Blocks.SOUL_SAND_BLOCKS) && state.isOf(Blocks.TORCHFLOWER_CROP)) {
//            world.setBlockState(pos, JNEBlocks.SOUL_TORCHFLOWER_CROP.getDefaultState(), NOTIFY_LISTENERS);
//        }
//    }
}
