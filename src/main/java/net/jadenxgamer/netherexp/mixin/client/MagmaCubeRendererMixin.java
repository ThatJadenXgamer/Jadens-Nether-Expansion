package net.jadenxgamer.netherexp.mixin.client;

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
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$pixelConsistentTextureLocation(MagmaCube entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (!JNEConfigs.PIXEL_CONSISTENT_MAGMA_CUBES.get()) return;
        var size = entity.getSize();
        cir.setReturnValue(NetherExp.netherexpPath("textures/entity/magma_cube/magmacube_" + size + ".png"));
    }
}