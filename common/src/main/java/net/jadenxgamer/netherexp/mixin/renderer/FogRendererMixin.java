package net.jadenxgamer.netherexp.mixin.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {

    @Inject(
            method = "setupFog",
            at = @At(value = "TAIL")
    )
    private static void netherexp$applyCustomFog(Camera camera, FogRenderer.FogMode fogMode, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getEntity();
        FogType fogType = camera.getFluidInCamera();
        /*
         * Increases Nether Fog & Underlava Visibility when the player has FogSight Effect
         * Does not work if the player has Blindness or Darkness or in the Overworld/End
         */
        if (((LivingEntity) entity).hasEffect(JNEMobEffects.FOGSIGHT.get()) && !((LivingEntity) entity).hasEffect(MobEffects.BLINDNESS) && !((LivingEntity) entity).hasEffect(MobEffects.DARKNESS)) {
            if (thickFog && fogType == FogType.NONE) {
                RenderSystem.setShaderFogStart(0.0F);
                RenderSystem.setShaderFogEnd(viewDistance);
            }
            else if (fogType == FogType.LAVA && !entity.isSpectator()) {
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
         * Adds a Fog when submerged inside Magma Cream Block
         */
        if (NetherExpClient.INSIDE_MAGMA_CREAM_BLOCK) {
            RenderSystem.setShaderFogStart(2.0F);
            RenderSystem.setShaderFogEnd(6.0F);
            RenderSystem.setShaderFogColor(1.0f, 0.4f, 0.0f);
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
