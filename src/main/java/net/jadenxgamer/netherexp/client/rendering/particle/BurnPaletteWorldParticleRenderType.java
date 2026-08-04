package net.jadenxgamer.netherexp.client.rendering.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import net.jadenxgamer.netherexp.client.assetdriven.managers.BurnPalettesManager;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

public class BurnPaletteWorldParticleRenderType extends LodestoneWorldParticleRenderType {

    public BurnPaletteWorldParticleRenderType(LodestoneRenderType renderType, ShaderHolder shader, ResourceLocation texture, Runnable blendFunction) {
        super(renderType, shader, texture, blendFunction);
    }

    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager manager) {
        BufferBuilder builder = super.begin(tesselator, manager);
        ShaderInstance shader = RenderSystem.getShader();
        if (shader != null) {
            var paletteTextureId = BurnPalettesManager.getPaletteTexture();
            shader.setSampler("PaletteSampler", paletteTextureId);
        }
        return builder;
    }
}