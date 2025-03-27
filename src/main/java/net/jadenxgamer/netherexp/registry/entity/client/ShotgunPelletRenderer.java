package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.ShotgunPellet;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ShotgunPelletRenderer extends ArrowRenderer<ShotgunPellet> {
    public ShotgunPelletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ShotgunPellet entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/projectiles/shotgun_pellet.png");
    }
}
