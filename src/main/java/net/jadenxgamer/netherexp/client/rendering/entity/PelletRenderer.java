package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.AbstractPellet;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class PelletRenderer<T extends AbstractPellet> extends EntityRenderer<T> {
    private final ShotgunPelletModel<ShotgunPellet> model;

    public PelletRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ShotgunPelletModel<>(context.bakeLayer(ShotgunPelletModel.LAYER));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0f, 1.65f, 0.0f);

        poseStack.mulPose(Axis.XP.rotationDegrees(-180));

        Vec3 velocity = entity.getDeltaMovement();
        float yaw, pitch;
        if (velocity.lengthSqr() < 1.0e-4) {
            yaw = entity.getYRot();
            pitch = entity.getXRot();
        } else {
            double vx = velocity.x;
            double vy = velocity.y;
            double vz = velocity.z;
            double horizontal = Math.sqrt(vx * vx + vz * vz);

            if (horizontal < 1.0e-7) yaw = 0.0f;
            else yaw = (float) Math.toDegrees(Math.atan2(-vx, vz));

            pitch = (float) -Math.toDegrees(Math.atan2(vy, horizontal));
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        VertexConsumer vertexConsumer = buffer.getBuffer(JNERenderType.noShadeEntityCutoutNoCull(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        poseStack.popPose();
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return 15;
    }

    public static class ShotgunPelletModel<T extends AbstractPellet> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.id("pellet"), "main");
        private final ModelPart main;

        public ShotgunPelletModel(ModelPart root) {
            this.main = root.getChild("main");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -8.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 15).addBox(-1.5F, -1.5F, 4.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.5F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            main.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
