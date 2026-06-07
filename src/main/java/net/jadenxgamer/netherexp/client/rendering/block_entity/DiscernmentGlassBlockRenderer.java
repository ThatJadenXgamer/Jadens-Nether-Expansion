package net.jadenxgamer.netherexp.client.rendering.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.core.block.entity.DiscernmentGlassBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DiscernmentGlassBlockRenderer implements BlockEntityRenderer<DiscernmentGlassBlockEntity> {
    private final ItemRenderer itemRenderer;

    public DiscernmentGlassBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(DiscernmentGlassBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemStack filterItem = entity.getFilterItem();
        if (filterItem.isEmpty()) return;
        float tick = entity.getLevel() != null ? entity.getLevel().getGameTime() / 10.0f : 0;

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.scale(0.55f, 0.55f, 0.55f);
        poseStack.mulPose(Axis.YP.rotation(tick % 360.0f));
        this.itemRenderer.renderStatic(entity.getFilterItem(), ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.getLevel(), packedOverlay);
        poseStack.popPose();
    }
}
