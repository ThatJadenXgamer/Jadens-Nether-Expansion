package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.client.layer.VesselGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.custom.Vessel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VesselRenderer extends MobRenderer<Vessel, VesselModel<Vessel>> {
    public VesselRenderer(EntityRendererProvider.Context context) {
        super(context, new VesselModel<>(context.bakeLayer(JNEModelLayers.VESSEL_LAYER)), 0.6f);
        this.addLayer(new VesselGlowlayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Vessel entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/vessel.png");
    }
}
