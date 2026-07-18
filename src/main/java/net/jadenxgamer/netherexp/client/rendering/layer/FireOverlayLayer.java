package net.jadenxgamer.netherexp.client.rendering.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jadenxgamer.netherexp.client.assetdriven.managers.BurnPalettesManager;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEAttachmentTypes;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import java.awt.*;

public class FireOverlayLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public FireOverlayLayer(LivingEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getType().is(JNETags.EntityTypes.NO_BURNING_FILTER) || !entity.displayFireAnimation() || entity.isSpectator()) return;

        ResourceLocation fireBlock = entity.getData(JNEAttachmentTypes.LAST_FIRE);
        int row = BurnPalettesManager.getRowForBlock(fireBlock);
        int rgb = BurnPalettesManager.getPaletteColor(row, 2).getRGB() & 0x00FFFFFF;

        int packedColor = getGlow(entity, partialTick, rgb);
        this.getParentModel().renderToBuffer(
                poseStack, bufferSource.getBuffer(JNERenderType.fireOverlay(this.getTextureLocation(entity))),
                packedLight, OverlayTexture.NO_OVERLAY, packedColor
        );
    }

    private static <T extends LivingEntity> int getGlow(T entity, float partialTick, int rgb) {
        float minAlpha = 0.05f;
        float maxAlpha = 0.5f;
        float speed = 0.4f;
        float frameRate = 20.0f / 12.0f;

        float quantizedTicks = Mth.floor((entity.tickCount + partialTick) / frameRate) * frameRate;

        float offset = (minAlpha + maxAlpha) / 2.0f;
        float amplitude = (maxAlpha - minAlpha) / 2.0f;
        float alpha = offset + amplitude * Mth.sin(quantizedTicks * speed);
        alpha = Mth.clamp(alpha, minAlpha, maxAlpha);
        return ((int) (alpha * 255) << 24) | rgb;
    }
}