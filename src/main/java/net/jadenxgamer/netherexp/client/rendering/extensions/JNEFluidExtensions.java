package net.jadenxgamer.netherexp.client.rendering.extensions;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class JNEFluidExtensions {

    public static final IClientFluidTypeExtensions ectoplasmExt = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return NetherExp.id("block/ectoplasm_still");
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return NetherExp.id("block/ectoplasm_flow");
        }

        @Override
        public @NotNull ResourceLocation getOverlayTexture() {
            return NetherExp.id("block/underectoplasm");
        }

        @Override
        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
            RenderSystem.setShaderFogStart(-0.8f);
            RenderSystem.setShaderFogEnd(8.0f);
        }

        @Override
        public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
            return new Vector3f(0.02f, 0.333f, 0.357f);
        }
    };
}
