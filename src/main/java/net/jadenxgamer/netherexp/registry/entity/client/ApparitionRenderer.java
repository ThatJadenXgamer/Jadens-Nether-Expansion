package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.registry.entity.custom.ApparitionEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ApparitionRenderer extends GeoEntityRenderer<ApparitionEntity> {
    public ApparitionRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ApparitionModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public RenderLayer getRenderType(ApparitionEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    protected int getBlockLight(ApparitionEntity entity, BlockPos pos) {
        return 15;
    }
}
