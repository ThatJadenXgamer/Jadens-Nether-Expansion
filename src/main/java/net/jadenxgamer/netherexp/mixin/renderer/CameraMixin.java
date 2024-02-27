package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private boolean ready;

    @Shadow private BlockView area;

    @Shadow @Final private BlockPos.Mutable blockPos;

    @Inject(
            method = "getSubmersionType",
            at = @At(value = "TAIL")
    )
    private void netherexp$getCustomSubmersionType(CallbackInfoReturnable<CameraSubmersionType> cir) {
        BlockState blockState = this.area.getBlockState(blockPos);
        FluidState fluidState = this.area.getFluidState(this.blockPos);
        NetherExpClient.INSIDE_SOUL_GLASS = this.ready && blockState.isOf(JNEBlocks.SOUL_GLASS);
        NetherExpClient.INSIDE_ECTOPLASM = this.ready && fluidState.isIn(JNETags.Fluids.ECTOPLASM);
    }
}
