package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.Stampede;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StampedeRenderer extends MobRenderer<Stampede, StampedeModel<Stampede>> {
    public StampedeRenderer(EntityRendererProvider.Context context) {
        super(context, new StampedeModel<>(context.bakeLayer(JNEModelLayers.STAMPEDE_LAYER)), 1.0f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Stampede entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/stampede.png");
    }
}
