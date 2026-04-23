package net.jadenxgamer.netherexp.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

public class JNERenderType {

    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_NO_SHADE_ENTITY_CUTOUT = new RenderStateShard.ShaderStateShard(JNERenderStateShard::getRenderTypeNoShadeEntityCutout);
    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_NO_SHADE_ENTITY_CUTOUT_NO_CULL = new RenderStateShard.ShaderStateShard(JNERenderStateShard::getRenderTypeNoShadeEntityCutoutNoCull);
    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_ADDITIVE = new RenderStateShard.ShaderStateShard(JNERenderStateShard::getRenderTypeEntityAdditive);

    protected static final RenderStateShard.WriteMaskStateShard NO_DEPTH_WRITE = new RenderStateShard.WriteMaskStateShard(true, false);

    protected static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
            "additive_transparency",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            },
            () -> {
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            }
    );

    public static final RenderTypeProvider NO_SHADE_ENTITY_CUTOUT = new RenderTypeProvider(token ->
            LodestoneRenderTypes.createGenericRenderType(token, "no_shade_entity_cutout", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, LodestoneRenderTypes.builder()
                    .setShaderState(RENDERTYPE_NO_SHADE_ENTITY_CUTOUT)
                    .setCullState(RenderStateShard.CULL)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(token)
                    .setOverlayState(RenderStateShard.OVERLAY)
            )
    );

    public static final RenderTypeProvider NO_SHADE_ENTITY_CUTOUT_NO_CULL = new RenderTypeProvider(token ->
            LodestoneRenderTypes.createGenericRenderType(token, "no_shade_entity_cutout_no_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, LodestoneRenderTypes.builder()
                    .setShaderState(RENDERTYPE_NO_SHADE_ENTITY_CUTOUT_NO_CULL)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(token)
                    .setOverlayState(RenderStateShard.OVERLAY)
            )
    );

    public static final RenderTypeProvider ENTITY_ADDITIVE = new RenderTypeProvider(token ->
            LodestoneRenderTypes.createGenericRenderType(token, "entity_additive", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, LodestoneRenderTypes.builder()
                    .setShaderState(RENDERTYPE_ENTITY_ADDITIVE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setWriteMaskState(NO_DEPTH_WRITE)
                    .setTextureState(token)
                    .setOverlayState(RenderStateShard.OVERLAY)
            )
    );

    public static LodestoneRenderType noShadeEntityCutout(ResourceLocation texture) {
        return NO_SHADE_ENTITY_CUTOUT.apply(RenderTypeToken.createToken(texture)).getRenderType();
    }

    public static LodestoneRenderType noShadeEntityCutoutNoCull(ResourceLocation texture) {
        return NO_SHADE_ENTITY_CUTOUT_NO_CULL.apply(RenderTypeToken.createToken(texture)).getRenderType();
    }

    public static LodestoneRenderType entityAdditive(ResourceLocation texture) {
        return ENTITY_ADDITIVE.apply(RenderTypeToken.createToken(texture)).getRenderType();
    }
}