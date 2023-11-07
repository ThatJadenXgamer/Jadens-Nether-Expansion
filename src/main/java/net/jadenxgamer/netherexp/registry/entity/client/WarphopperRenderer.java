package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.registry.entity.custom.WarphopperEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WarphopperRenderer extends GeoEntityRenderer<WarphopperEntity> {
    public WarphopperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WarphopperModel());
        this.shadowRadius = 0.6f;
    }

    @Override
    public RenderLayer getRenderType(WarphopperEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
