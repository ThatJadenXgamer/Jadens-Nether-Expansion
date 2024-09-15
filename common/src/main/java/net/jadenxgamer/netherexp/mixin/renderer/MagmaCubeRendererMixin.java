package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.MagmaCube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagmaCubeRenderer.class)
public abstract class MagmaCubeRendererMixin {

    @Inject(
            method = "getTextureLocation(Lnet/minecraft/world/entity/monster/MagmaCube;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void netherexp$getTextureLocation(MagmaCube entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (JNEConfigs.PIXEL_CONSISTENT_MAGMA_CUBES.get()) {
            int size = Math.min(entity.getSize(), 4);
            cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/magma_cube/magmacube_" + size + ".png"));
        }
    }
}
