package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.registry.entity.custom.GraspEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class GraspRenderer extends GeoEntityRenderer<GraspEntity> {
    public GraspRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GraspModel());
        this.shadowRadius = 0.6f;
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public RenderLayer getRenderType(GraspEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
