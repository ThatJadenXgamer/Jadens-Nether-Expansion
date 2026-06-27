package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StemBlock.class)
public abstract class StemBlockMixin {

    @Inject(
            method = "mayPlaceOn",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$allowSoulSandPlacement(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(JNETags.Blocks.SOUL_CROP_MUTATION_BLOCKS)) cir.setReturnValue(true);
    }
}
