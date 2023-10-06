package net.jadenxgamer.netherexp.mixin.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.misc_registry.ModTags;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {

    @Inject(
            method = "applyFog",
            at = @At(value = "TAIL")
    )
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)entity;
        RegistryEntry<Biome> registryEntry = clientPlayerEntity.world.getBiome(clientPlayerEntity.getBlockPos());
        if (((LivingEntity) entity).hasStatusEffect(StatusEffects.GLOWING)) {
            if (thickFog && cameraSubmersionType == CameraSubmersionType.NONE) {
                RenderSystem.setShaderFogStart(0.0F);
                RenderSystem.setShaderFogEnd(viewDistance);
            }
            else if (cameraSubmersionType == CameraSubmersionType.LAVA && !entity.isSpectator()) {
                RenderSystem.setShaderFogStart(-8.0F);
                RenderSystem.setShaderFogEnd(viewDistance * 0.5F);
            }
        }
        else if (thickFog && registryEntry.isIn(ModTags.Biomes.HAS_FURTHER_NETHER_FOG)) {
            RenderSystem.setShaderFogStart(viewDistance * 0.05F);
            RenderSystem.setShaderFogEnd(Math.min(viewDistance, 192.0F) * 0.75F);
        }
    }
}
