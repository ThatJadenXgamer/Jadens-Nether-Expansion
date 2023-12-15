package net.jadenxgamer.netherexp.mixin.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.effect.ModStatusEffects;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin{

    @Inject(
            method = "applyFog",
            at = @At(value = "TAIL")
    )
    private static void netherexp$applyCustomFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        /*
         * Increases Nether Fog & Underlava Visibility when the player has FogSight Effect
         * Does not work if the player has Blindness or Darkness or in the Overworld/End
         */
        if (((LivingEntity) entity).hasStatusEffect(ModStatusEffects.FOGSIGHT) && !((LivingEntity) entity).hasStatusEffect(StatusEffects.BLINDNESS) && !((LivingEntity) entity).hasStatusEffect(StatusEffects.DARKNESS)) {
            if (thickFog && cameraSubmersionType == CameraSubmersionType.NONE) {
                RenderSystem.setShaderFogStart(0.0F);
                RenderSystem.setShaderFogEnd(viewDistance);
            }
            else if (cameraSubmersionType == CameraSubmersionType.LAVA && !entity.isSpectator()) {
                RenderSystem.setShaderFogStart(-8.0F);
                RenderSystem.setShaderFogEnd(viewDistance * 0.65F);
            }
        }
        /*
         * Adds a Fog when submerged inside Soul Glass
         */
        if (NetherExpClient.INSIDE_SOUL_GLASS) {
            RenderSystem.setShaderFogStart(2.0F);
            RenderSystem.setShaderFogEnd(6.0F);
            RenderSystem.setShaderFogColor(0.118f, 0.231f, 0.243f);
        }
        /*
         * Adds a Fog when submerged inside Ectoplasm
         */
        if (NetherExpClient.INSIDE_ECTOPLASM) {
            RenderSystem.setShaderFogStart(-8.0F);
            RenderSystem.setShaderFogEnd(8.0F);
            RenderSystem.setShaderFogColor(0.02f, 0.333f, 0.357f);
        }
    }
}
