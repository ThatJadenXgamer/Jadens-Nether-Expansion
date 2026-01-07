package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.core.block.AncientFireBlock;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {

    @Inject(
            method = "getState",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$getState(BlockGetter reader, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = reader.getBlockState(pos.below());
        if (AncientFireBlock.canSurviveOnBlock(state)) cir.setReturnValue(JNEBlocks.ANCIENT_FIRE.get().defaultBlockState());
    }
}
