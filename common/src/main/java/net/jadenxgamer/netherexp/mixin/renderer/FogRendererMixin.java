package net.jadenxgamer.netherexp.mixin.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.enums.NetherFogDistance;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {

    // Deals with adding and changing fog based stuff for JNE

    @Inject(
            method = "setupFog",
            at = @At(value = "TAIL")
    )
    private static void netherexp$applyCustomFog(Camera camera, FogRenderer.FogMode fogMode, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getEntity();
        FogType fogType = camera.getFluidInCamera();
        /*
         * Adds a Fog when submerged inside Soul Glass
         */
        if (NetherExpClient.INSIDE_SOUL_GLASS) {
            RenderSystem.setShaderFogStart(2.0F);
            RenderSystem.setShaderFogEnd(6.0F);
            RenderSystem.setShaderFogColor(0.106f, 0.278f, 0.271f);
        }
        /*
         * Adds a Fog when submerged inside Magma Cream Block
         */
        else if (NetherExpClient.INSIDE_MAGMA_CREAM_BLOCK) {
            RenderSystem.setShaderFogStart(2.0F);
            RenderSystem.setShaderFogEnd(6.0F);
            RenderSystem.setShaderFogColor(1.0f, 0.4f, 0.0f);
        }
        /*
         * Adds a Fog when submerged inside Ectoplasm
         */
        else if (NetherExpClient.INSIDE_ECTOPLASM) {
            RenderSystem.setShaderFogStart(-8.0F);
            RenderSystem.setShaderFogEnd(8.0F);
            RenderSystem.setShaderFogColor(0.02f, 0.333f, 0.357f);
        }
        else if (entity instanceof LivingEntity livingEntity) {
            /*
             * Increases Default Nether Fog distance if enabled in configs
             */
            if (livingEntity.hasEffect(JNEMobEffects.BETRAYED.get())) {
                if (fogType == FogType.NONE) {
                    RenderSystem.setShaderFogStart(0.0f);
                    RenderSystem.setShaderFogEnd(viewDistance / 2);
                    RenderSystem.setShaderFogColor(0.69f, 0.067f, 0.067f);
                }
            }
            /*
             * Increases Default Nether Fog distance if enabled in configs
             */
            else if (JNEConfigs.NETHER_FOG_DISTANCE.get() != NetherFogDistance.VANILLA && !livingEntity.hasEffect(MobEffects.BLINDNESS) && !livingEntity.hasEffect(MobEffects.DARKNESS) && thickFog) {
                if (fogType == FogType.NONE) {
                    RenderSystem.setShaderFogStart(netherexp$getFogStart(JNEConfigs.NETHER_FOG_DISTANCE.get()));
                    RenderSystem.setShaderFogEnd(netherexp$getFogEnd(JNEConfigs.NETHER_FOG_DISTANCE.get(), viewDistance));
                }
            }
        }
    }

    @Unique
    private static float netherexp$getFogStart(NetherFogDistance config) {
        if (config == NetherFogDistance.DISABLED) {
            return -8.0f;
        }
        return 0.0f;
    }

    @Unique
    private static float netherexp$getFogEnd(NetherFogDistance config, float viewDistance) {
        switch (config) {
            case FAR -> {
                return viewDistance * 1.5f;
            }
            case DISABLED -> {
                return 1_000_000.0f;
            }
        }
        return viewDistance;
    }
}
