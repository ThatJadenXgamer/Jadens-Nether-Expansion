package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.PhasmoArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PhasmoArrowRenderer extends ArrowRenderer<PhasmoArrow> {
    public PhasmoArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(PhasmoArrow entity) {
        return NetherExp.id("textures/entity/projectiles/phasmo_arrow.png");
    }
}
