package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
    private void netherexp$canPlantOnSoul(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (this == Blocks.PUMPKIN_STEM && state.is(JNETags.Blocks.SOUL_SAND_BLOCKS)) {
            cir.setReturnValue(true);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        BlockState floor = level.getBlockState(pos.below());
        if (floor.is(JNETags.Blocks.SOUL_SAND_BLOCKS) && state.is(Blocks.PUMPKIN_STEM)) {
            level.setBlock(pos, JNEBlocks.SORROWSQUASH_STEM.get().defaultBlockState(), UPDATE_ALL);
        }
    }
}
