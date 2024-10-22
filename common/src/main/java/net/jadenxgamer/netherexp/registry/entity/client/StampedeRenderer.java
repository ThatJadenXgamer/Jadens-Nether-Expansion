package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.OldStampede;
import net.jadenxgamer.netherexp.registry.entity.custom.Stampede;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StampedeRenderer extends MobRenderer<Stampede, StampedeModel<Stampede>> {
    public StampedeRenderer(EntityRendererProvider.Context context) {
        super(context, new StampedeModel<>(context.bakeLayer(JNEModelLayers.STAMPEDE_LAYER)), 1.0f);
        this.addLayer(new SaddleLayer(this, new StampedeModel(context.bakeLayer(JNEModelLayers.STAMPEDE_SADDLE_LAYER)), new ResourceLocation(NetherExp.MOD_ID, "textures/entity/stampede_saddle.png")));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Stampede entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/stampede.png");
    }
}
