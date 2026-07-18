package net.jadenxgamer.netherexp.client.rendering;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.jadenxgamer.netherexp.NetherExp;
import team.lodestar.lodestone.systems.rendering.shader.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

public class JNEShaders {
    public static final LodestoneShaderRegistry SHADERS = new LodestoneShaderRegistry(NetherExp.MOD_ID);

    public static final ShaderHolder NO_SHADE_ENTITY_CUTOUT = SHADERS.register(new ShaderHolder(NetherExp.idPath(NetherExp.MOD_ID, "rendertype_no_shade_entity_cutout"), DefaultVertexFormat.NEW_ENTITY));
    public static final ShaderHolder NO_SHADE_ENTITY_CUTOUT_NO_CULL = SHADERS.register(new ShaderHolder(NetherExp.idPath(NetherExp.MOD_ID, "rendertype_no_shade_entity_cutout_no_cull"), DefaultVertexFormat.NEW_ENTITY));
    public static final ShaderHolder ENTITY_ADDITIVE = SHADERS.register(new ShaderHolder(NetherExp.idPath(NetherExp.MOD_ID, "rendertype_entity_additive"), DefaultVertexFormat.NEW_ENTITY));
    public static final ShaderHolder FIRE_OVERLAY = SHADERS.register(new ShaderHolder(NetherExp.idPath(NetherExp.MOD_ID, "rendertype_fire_overlay"), DefaultVertexFormat.NEW_ENTITY));

    public static final ShaderHolder PARTICLE_OVERLAY = SHADERS.register(new ShaderHolder(NetherExp.idPath(NetherExp.MOD_ID, "rendertype_particle_overlay"), DefaultVertexFormat.PARTICLE));
    public static final ShaderHolder BURN_PALETTE = SHADERS.register(new ShaderHolder(NetherExp.idPath(NetherExp.MOD_ID, "rendertype_burn_palette"), DefaultVertexFormat.PARTICLE));
}
