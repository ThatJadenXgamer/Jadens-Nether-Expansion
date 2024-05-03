package net.jadenxgamer.netherexp.registry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.MistCharge;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MistChargeRenderer extends EntityRenderer<MistCharge> {

    private final MistChargeModel<MistCharge> model;

    public MistChargeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MistChargeModel<>(context.bakeLayer(JNEModelLayers.MIST_CHARGE_LAYER));
    }

    @Override
    public void render(MistCharge entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.setupAnim(entity, 0f, 0f, entity.tickCount,0f, 0f);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(MistCharge entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/mist_charge.png");
    }
}
