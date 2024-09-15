package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.client.layer.CarcassGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.client.layer.VesselGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.custom.Banshee;
import net.jadenxgamer.netherexp.registry.entity.custom.Carcass;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CarcassRenderer extends MobRenderer<Carcass, CarcassModel<Carcass>> {
    public CarcassRenderer(EntityRendererProvider.Context context) {
        super(context, new CarcassModel<>(context.bakeLayer(JNEModelLayers.CARCASS_LAYER)), 0.5f);
        this.addLayer(new CarcassGlowlayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Carcass entity) {
        if (entity.getIsReanimated()) {
            return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/carcass/carcass_reanimated.png");
        }
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/carcass/carcass.png");
    }
}
