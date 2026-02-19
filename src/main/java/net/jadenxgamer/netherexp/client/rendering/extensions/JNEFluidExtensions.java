package net.jadenxgamer.netherexp.client.rendering.extensions;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.NotNull;
import org.joml.SimplexNoise;
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

    public static final IClientFluidTypeExtensions lavaExt = new IClientFluidTypeExtensions() {
        private static final int colorA = 0xFFFFFFFF;
        private static final int colorB = 0xFFE28001;
        private static final double scale = 14.0;

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
            int colorA = hexToArgb(JNEConfigs.LAVA_GRADIENT_COLOR_A.get());
            int colorB = hexToArgb(JNEConfigs.LAVA_GRADIENT_COLOR_B.get());
            double scale = JNEConfigs.LAVA_GRADIENT_NOISE_SCALE.get();
            double noise = SimplexNoise.noise(
                    (float) (pos.getX() / scale),
                    (float) (pos.getY() / scale),
                    (float) (pos.getZ() / scale)
            );

            float factor = (float) ((noise + 1.0) * 0.5);
            factor = Math.max(0.0f, Math.min(1.0f, factor));

            int a1 = (colorA >> 24) & 0xFF;
            int r1 = (colorA >> 16) & 0xFF;
            int g1 = (colorA >>  8) & 0xFF;
            int b1 = colorA & 0xFF;

            int a2 = (colorB >> 24) & 0xFF;
            int r2 = (colorB >> 16) & 0xFF;
            int g2 = (colorB >>  8) & 0xFF;
            int b2 = colorB & 0xFF;

            int alpha = (int) (a1 * (1.0f - factor) + a2 * factor);
            int red   = (int) (r1 * (1.0f - factor) + r2 * factor);
            int green = (int) (g1 * (1.0f - factor) + g2 * factor);
            int blue  = (int) (b1 * (1.0f - factor) + b2 * factor);

            return (alpha << 24) | (red << 16) | (green << 8) | blue;
        }

        private static int hexToArgb(String hex) {
            if (hex.startsWith("#")) hex = hex.substring(1);
            if (hex.length() == 6) hex = "FF" + hex;
            if (hex.length() != 8) return 0xFFFFFFFF;
            try {
                return (int) Long.parseLong(hex, 16);
            } catch (NumberFormatException e) {
                return 0xFFFFFFFF;
            }
        }
    };
}