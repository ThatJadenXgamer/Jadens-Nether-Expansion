package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.BlackIcicle;
import net.jadenxgamer.netherexp.core.entity.PhasmoPellet;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlackIcicleRenderer extends ArrowRenderer<BlackIcicle> {

    public BlackIcicleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackIcicle entity) {
        return NetherExp.netherexpPath("textures/entity/projectiles/black_icicle.png");
    }
}