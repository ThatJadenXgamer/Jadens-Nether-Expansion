package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.registry.entity.custom.MistChargeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MistChargeRenderer extends GeoEntityRenderer<MistChargeEntity> {

    public MistChargeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MistChargeModel());
    }

    @Override
    public RenderLayer getRenderType(MistChargeEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
