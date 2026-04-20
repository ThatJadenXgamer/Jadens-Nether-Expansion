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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PortalGlowRenderer extends EntityRenderer<PortalGlow> {
    private final PortalGlowRenderer.PortalGlowModel<PortalGlow> model;

    protected PortalGlowRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PortalGlowRenderer.PortalGlowModel<>(context.bakeLayer(PortalGlowRenderer.PortalGlowModel.LAYER));
    }

    @Override
    public void render(PortalGlow entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.2f, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(-180));
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.scale(0.5f, 0.5f, 0.5f);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        poseStack.pushPose();
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

            PartDefinition glow = partdefinition.addOrReplaceChild("glow", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -18.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 0).addBox(-8.0F, -8.0F, 2.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }


        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            glow.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
