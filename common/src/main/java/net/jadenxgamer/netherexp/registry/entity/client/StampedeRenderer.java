package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.OldStampede;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StampedeRenderer extends MobRenderer<OldStampede, StampedeModel<OldStampede>> {
    public StampedeRenderer(EntityRendererProvider.Context context) {
        super(context, new StampedeModel<>(context.bakeLayer(JNEModelLayers.STAMPEDE_LAYER)), 1.0f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(OldStampede entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/stampede.png");
    }
}
