package net.jadenxgamer.netherexp.client.rendering.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class FireOverlayLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public FireOverlayLayer(LivingEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getType().is(JNETags.EntityTypes.NO_BURNING_FILTER) || !entity.displayFireAnimation() || entity.isSpectator()) return;
        float minAlpha = 0.05f;
        float maxAlpha = 0.5f;
        float speed = 0.4f;

        float offset = (minAlpha + maxAlpha) / 2.0f;
        float amplitude = (maxAlpha - minAlpha) / 2.0f;
        float alpha = offset + amplitude * Mth.sin((entity.tickCount + partialTick) * speed);
        alpha = Mth.clamp(alpha, minAlpha, maxAlpha);
        int packedColor = ((int) (alpha * 255) << 24) | 0xFF8000;
        this.getParentModel().renderToBuffer(
                poseStack,
                bufferSource.getBuffer(JNERenderType.fireOverlay(this.getTextureLocation(entity))),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                packedColor
        );
    }
}