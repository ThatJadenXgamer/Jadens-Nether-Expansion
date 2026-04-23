package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.PortalGlow;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class PortalGlowRenderer extends EntityRenderer<PortalGlow> {
    private final PortalGlowModel model;

    public PortalGlowRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PortalGlowModel(context.bakeLayer(PortalGlowModel.LAYER));
    }

    @Override
    public void render(PortalGlow entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        int width = entity.getPortalWidth();
        int height = entity.getPortalHeight();
        Direction.Axis axis = entity.getPortalAxis();

        poseStack.pushPose();

        float age = entity.tickCount + partialTick;
        float scaleX = width - 0.025f;
        float scaleY = height - 0.025f;
        float scaleZ = 1.25f + 0.25f * (float) Math.sin(age * 0.05);

        if (axis == Direction.Axis.X) {
            poseStack.scale(scaleX, scaleY, scaleZ);
        } else if (axis == Direction.Axis.Z) {
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0f));
            poseStack.scale(scaleX, scaleY, scaleZ);
        }

        VertexConsumer vertexConsumer = buffer.getBuffer(JNERenderType.entityAdditive(getTextureLocation(entity)));
        model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(PortalGlow entity) {
        return NetherExp.netherexpPath("textures/entity/portal_glow.png");
    }

    public static class PortalGlowModel<T extends PortalGlow> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("portal_glow"), "main");
        private final ModelPart glow;

        public PortalGlowModel(ModelPart root) {
            this.glow = root.getChild("glow");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition glow = partdefinition.addOrReplaceChild("glow",
                    CubeListBuilder.create()
                            .texOffs(0, 32).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                            .texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
                    PartPose.offset(0.0F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            glow.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}