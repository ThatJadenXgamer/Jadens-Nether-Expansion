package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.util.CameraSubmersionType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.Camera;
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
            at = @At(value = "TAIL"),
            cancellable = true
    )
    private void netherexp$getCustomSubmersionType(CallbackInfoReturnable<CameraSubmersionType> cir) {
        BlockState blockState = this.area.getBlockState(blockPos);
        if (this.ready && blockState.isOf(ModBlocks.SOUL_GLASS)) {
            cir.setReturnValue(CameraSubmersionType.SOUL_GLASS);
        }
    }
}
