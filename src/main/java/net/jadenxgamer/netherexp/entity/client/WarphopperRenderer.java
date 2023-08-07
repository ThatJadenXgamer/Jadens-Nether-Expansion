package net.jadenxgamer.netherexp.entity.client;

import net.jadenxgamer.netherexp.entity.custom.WarphopperEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WarphopperRenderer extends GeoEntityRenderer<WarphopperEntity> {
    public WarphopperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WarphopperModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public RenderLayer getRenderType(WarphopperEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
