package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ShotgunPelletRenderer extends PelletRenderer<ShotgunPellet> {

    public ShotgunPelletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ShotgunPellet entity) {
        return NetherExp.id("textures/entity/projectiles/shotgun_pellet.png");
    }
}
