package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private BlockGetter level;

    @Shadow @Final private BlockPos.MutableBlockPos blockPosition;

    @Shadow private boolean initialized;

    @Inject(
            method = "getFluidInCamera",
            at = @At(value = "TAIL")
    )
    private void netherexp$getCustomSubmersionType(CallbackInfoReturnable<FogType> cir) {
        BlockState blockState = this.level.getBlockState(this.blockPosition);
        FluidState fluidState = this.level.getFluidState(this.blockPosition);
        NetherExpClient.INSIDE_SOUL_GLASS = this.initialized && blockState.is(JNEBlocks.SOUL_GLASS.get());
        NetherExpClient.INSIDE_MAGMA_CREAM_BLOCK = this.initialized && blockState.is(JNEBlocks.MAGMA_CREAM_BLOCK.get());
        NetherExpClient.INSIDE_ECTOPLASM = this.initialized && fluidState.is(JNETags.Fluids.ECTOPLASM);
    }
}
