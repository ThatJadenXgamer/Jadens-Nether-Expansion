package net.jadenxgamer.netherexp.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.jadenxgamer.netherexp.client.rendering.particle.BurnPaletteWorldParticleRenderType;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.registry.client.LodestoneShaders;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.PARTICLE;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypes.createGenericRenderType;

public class JNERenderType {

    protected static final RenderStateShard.WriteMaskStateShard NO_DEPTH_WRITE = new RenderStateShard.WriteMaskStateShard(true, false);

    protected static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
            "additive_transparency",
            () -> { RenderSystem.enableBlend(); RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE); },
            () -> { RenderSystem.disableBlend(); RenderSystem.defaultBlendFunc(); }
    );

    public static final RenderTypeProvider NO_SHADE_ENTITY_CUTOUT = new RenderTypeProvider(token ->
            createGenericRenderType(token, "no_shade_entity_cutout", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, LodestoneRenderTypes.builder()
                    .setShaderState(JNEShaders.NO_SHADE_ENTITY_CUTOUT)
                    .setCullState(RenderStateShard.CULL)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(token)
                    .setOverlayState(RenderStateShard.OVERLAY)
            )
    );

    public static final RenderTypeProvider NO_SHADE_ENTITY_CUTOUT_NO_CULL = new RenderTypeProvider(token ->
            createGenericRenderType(token, "no_shade_entity_cutout_no_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, LodestoneRenderTypes.builder()
                    .setShaderState(JNEShaders.NO_SHADE_ENTITY_CUTOUT_NO_CULL)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(token)
                    .setOverlayState(RenderStateShard.OVERLAY)
            )
    );

    public static final RenderTypeProvider ENTITY_ADDITIVE = new RenderTypeProvider(token ->
            createGenericRenderType(token, "entity_additive", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, LodestoneRenderTypes.builder()
                    .setShaderState(JNEShaders.ENTITY_ADDITIVE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setWriteMaskState(NO_DEPTH_WRITE)
                    .setTextureState(token)
                    .setOverlayState(RenderStateShard.OVERLAY)
            )
    );

    public static final RenderTypeProvider FIRE_OVERLAY = new RenderTypeProvider(token ->
            createGenericRenderType(token, "fire_overlay", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, LodestoneRenderTypes.builder()
                    .setShaderState(JNEShaders.FIRE_OVERLAY)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(token)
                    .setOverlayState(RenderStateShard.OVERLAY)
            )
    );

    public static final LodestoneRenderType PARTICLE_OVERLAY = createGenericRenderType(
            RenderTypeToken.createToken(TextureAtlas.LOCATION_PARTICLES), "particle_overlay", PARTICLE, QUADS, b -> b.setStateShards(
                    StateShards.NORMAL_TRANSPARENCY, JNEShaders.PARTICLE_OVERLAY, LodestoneRenderTypes.NO_CULL, LodestoneRenderTypes.LIGHTMAP, LodestoneRenderTypes.COLOR_WRITE)
    );

    public static final LodestoneRenderType TRANSPARENT_BURN_PALETTE_RENDER_TYPE = createGenericRenderType(
            RenderTypeToken.createToken(TextureAtlas.LOCATION_PARTICLES), "transparent_burn_palette", PARTICLE, QUADS, b -> b.setStateShards(
                    StateShards.NORMAL_TRANSPARENCY, JNEShaders.BURN_PALETTE, LodestoneRenderTypes.NO_CULL, LodestoneRenderTypes.LIGHTMAP, LodestoneRenderTypes.COLOR_WRITE)
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

    public static LodestoneRenderType fireOverlay(ResourceLocation texture) {
        return FIRE_OVERLAY.apply(RenderTypeToken.createToken(texture)).getRenderType();
    }

    public static final LodestoneWorldParticleRenderType ADDITIVE_OVERLAY = new LodestoneWorldParticleRenderType(
            LodestoneRenderTypes.ADDITIVE_PARTICLE, JNEShaders.PARTICLE_OVERLAY, TextureAtlas.LOCATION_PARTICLES,
            LodestoneRenderTypes.ADDITIVE_FUNCTION);

    public static final LodestoneWorldParticleRenderType TRANSPARENT_OVERLAY = new LodestoneWorldParticleRenderType(
            LodestoneRenderTypes.TRANSPARENT_PARTICLE, JNEShaders.PARTICLE_OVERLAY, TextureAtlas.LOCATION_PARTICLES,
            LodestoneRenderTypes.TRANSPARENT_FUNCTION);

    public static final LodestoneWorldParticleRenderType TRANSPARENT_BURN_PALETTE = new BurnPaletteWorldParticleRenderType(
            TRANSPARENT_BURN_PALETTE_RENDER_TYPE, JNEShaders.BURN_PALETTE, TextureAtlas.LOCATION_PARTICLES,
            LodestoneRenderTypes.TRANSPARENT_FUNCTION);
}