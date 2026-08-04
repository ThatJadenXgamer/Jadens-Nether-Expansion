package net.jadenxgamer.netherexp.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.jadenxgamer.elysium_api.api.util.ClientItemData;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.client.rendering.entity.WillOWispRenderer;
import net.jadenxgamer.netherexp.client.rendering.item.PumpChargeShotgunModel;
import net.jadenxgamer.netherexp.client.rendering.item.ShotgunFistModel;
import net.jadenxgamer.netherexp.core.item.PumpChargeShotgunItem;
import net.jadenxgamer.netherexp.core.item.ShotgunFistItem;
import net.jadenxgamer.netherexp.core.item.WillOWispItem;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class JNEItemRenderer extends BlockEntityWithoutLevelRenderer {

    public static final WillOWispRenderer.WillOWispItemModel WILL_O_WISP_MODEL = new WillOWispRenderer.WillOWispItemModel(Minecraft.getInstance().getEntityModels().bakeLayer(WillOWispRenderer.WillOWispItemModel.LAYER));
    public static final ShotgunFistModel SHOTGUN_FIST_MODEL = new ShotgunFistModel(Minecraft.getInstance().getEntityModels().bakeLayer(ShotgunFistModel.LAYER));
    public static final PumpChargeShotgunModel PUMP_CHARGE_SHOTGUN_MODEL = new PumpChargeShotgunModel(Minecraft.getInstance().getEntityModels().bakeLayer(PumpChargeShotgunModel.LAYER));

    public JNEItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        LocalPlayer player = Minecraft.getInstance().player;
        float ageInTicks = player == null ? 0f : player.tickCount + partialTick;
        if (stack.is(JNEItems.WILL_O_WISP.get())) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.2f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-180));
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1.0F, 1.0F, 1.0F);
            ResourceLocation texture = NetherExp.netherexpPath("textures/entity/will_o_wisp.png");
            VertexConsumer consumer = buffer.getBuffer(JNERenderType.noShadeEntityCutoutNoCull(texture));
            WILL_O_WISP_MODEL.setupAnim(player, (WillOWispItem) stack.getItem(), stack, displayContext, ageInTicks);
            WILL_O_WISP_MODEL.renderToBuffer(poseStack, consumer, LightTexture.FULL_BRIGHT, packedOverlay, 0xFFFFFF);
            poseStack.popPose();
        }
        if (stack.is(JNEItems.SHOTGUN_FIST.get())) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.5f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-180));
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1.0F, 1.0F, 1.0F);
            ResourceLocation texture = NetherExp.netherexpPath("textures/entity/shotgun_fist/base.png");
            ResourceLocation glow = NetherExp.netherexpPath("textures/entity/shotgun_fist/glow.png");
            SHOTGUN_FIST_MODEL.setupAnim(player, (ShotgunFistItem) stack.getItem(), stack, displayContext, ageInTicks);
            SHOTGUN_FIST_MODEL.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(texture)), packedLight, packedOverlay, 0xFFFFFF);
            SHOTGUN_FIST_MODEL.renderToBuffer(poseStack, buffer.getBuffer(JNERenderType.noShadeEntityCutoutNoCull(glow)), LightTexture.FULL_BRIGHT, packedOverlay, 0xFFFFFF);
            if (stack.hasFoil()) SHOTGUN_FIST_MODEL.renderToBuffer(poseStack, ItemRenderer.getFoilBuffer(buffer, RenderType.entityCutoutNoCull(texture), false, true), packedLight, packedOverlay, 0xFFFFFF);
            if (player != null) {
                if (ClientItemData.has(stack, "isSmoking")) SHOTGUN_FIST_MODEL.renderSmokeParticles(player, poseStack, displayContext);
                if (ClientItemData.has(stack, "shootFlash")) SHOTGUN_FIST_MODEL.renderFlashParticles(stack, player, poseStack, displayContext);
            }
            poseStack.popPose();
        }
        if (stack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get())) {
            var pumps = PumpChargeShotgunItem.getPumps(stack);
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.5f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-180));
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1.0F, 1.0F, 1.0F);
            ResourceLocation texture = NetherExp.netherexpPath("textures/entity/pump_charge_shotgun/base_" + pumps + ".png");
            ResourceLocation glow = NetherExp.netherexpPath("textures/entity/pump_charge_shotgun/glow_" + pumps + ".png");
            PUMP_CHARGE_SHOTGUN_MODEL.setupAnim(player, (PumpChargeShotgunItem) stack.getItem(), stack, displayContext, ageInTicks);
            PUMP_CHARGE_SHOTGUN_MODEL.renderToBuffer(poseStack, buffer.getBuffer(RenderType.entityCutoutNoCull(texture)), packedLight, packedOverlay, 0xFFFFFF);
            PUMP_CHARGE_SHOTGUN_MODEL.renderToBuffer(poseStack, buffer.getBuffer(JNERenderType.noShadeEntityCutoutNoCull(glow)), LightTexture.FULL_BRIGHT, packedOverlay, 0xFFFFFF);
            if (stack.hasFoil()) PUMP_CHARGE_SHOTGUN_MODEL.renderToBuffer(poseStack, ItemRenderer.getFoilBuffer(buffer, RenderType.entityCutoutNoCull(texture), false, true), packedLight, packedOverlay, 0xFFFFFF);
            if (player != null) {
                if (ClientItemData.has(stack, "isSmoking")) PUMP_CHARGE_SHOTGUN_MODEL.renderSmokeParticles(player, poseStack, displayContext);
                if (ClientItemData.has(stack, "shootFlash")) PUMP_CHARGE_SHOTGUN_MODEL.renderFlashParticles(stack, player, poseStack, displayContext);
            }
            poseStack.popPose();
        }
    }
}
