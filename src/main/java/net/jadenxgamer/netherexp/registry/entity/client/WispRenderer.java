package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.registry.entity.custom.WispEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WispRenderer extends GeoEntityRenderer<WispEntity> {
    public WispRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WispModel());
        this.shadowRadius = 0.2f;
    }
    @Override
    public RenderLayer getRenderType(WispEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    protected int getBlockLight(WispEntity entity, BlockPos pos) {
        return 15;
    }
}
