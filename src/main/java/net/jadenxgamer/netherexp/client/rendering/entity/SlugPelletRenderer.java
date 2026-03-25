package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.jadenxgamer.netherexp.core.entity.SlugPellet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SlugPelletRenderer extends PelletRenderer<SlugPellet> {

    public SlugPelletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SlugPellet entity) {
        return NetherExp.id("textures/entity/projectiles/slug_pellet.png");
    }
}
