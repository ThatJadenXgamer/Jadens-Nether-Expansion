package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StemBlock.class)
public abstract class StemBlockMixin extends BushBlock {
    public StemBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "mayPlaceOn",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$canPlantOnSoul(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (this == Blocks.PUMPKIN_STEM && blockState.is(JNETags.Blocks.SOUL_SAND_BLOCKS)) {
            cir.setReturnValue(true);
        }
    }

    //TODO: Reimplement Sorrowsquash
//    @SuppressWarnings("deprecation")
//    @Override
//    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
//        BlockState floor = world.getBlockState(pos.down());
//        if (floor.isIn(JNETags.Blocks.SOUL_SAND_BLOCKS) && state.isOf(Blocks.PUMPKIN_STEM)) {
//            world.setBlockState(pos, JNEBlocks.SORROWSQUASH_STEM.getDefaultState(), NOTIFY_LISTENERS);
//        }
//    }
}
