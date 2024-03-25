package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RootsBlock.class)
public class RootsBlockMixin {

    @Inject(
            method = "mayPlaceOn",
            at = @At(value = "TAIL"),
            cancellable = true
    )
    private void netherexp$changeCanPlantOnTop(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(JNETags.Blocks.ROOTS_PLANTABLE_ON)) {
            cir.setReturnValue(true);
        }
    }
}
