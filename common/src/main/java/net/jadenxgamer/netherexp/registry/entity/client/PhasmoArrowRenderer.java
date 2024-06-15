package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.PhasmoArrow;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
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
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/projectiles/phasmo_arrow.png");
    }
}
