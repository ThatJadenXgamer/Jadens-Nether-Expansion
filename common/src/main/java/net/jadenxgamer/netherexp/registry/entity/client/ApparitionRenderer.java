package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionModel<Apparition>> {
    public ApparitionRenderer(EntityRendererProvider.Context context) {
        super(context, new ApparitionModel<>(context.bakeLayer(JNEModelLayers.APPARITION_LAYER)), 0.8f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Apparition entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/apparition.png");
    }
}
