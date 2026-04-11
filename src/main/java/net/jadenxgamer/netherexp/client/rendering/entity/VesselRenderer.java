package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Vessel;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VesselRenderer extends MobRenderer<Vessel, VesselRenderer.VesselModel<Vessel>> {

    public VesselRenderer(EntityRendererProvider.Context context) {
        super(context, new VesselModel<>(context.bakeLayer(VesselModel.LAYER)), 0.8f);
        this.addLayer(new VesselGlowLayer(this));
    }

    @Override
    public void render(Vessel entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        if (entity.isDoom()) {
            Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            double dx = cameraPos.x - entity.getX();
            double dz = cameraPos.z - entity.getZ();
            float yRot = (float) (Math.atan2(dx, dz) * 180.0 / Math.PI);
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            poseStack.scale(1.0F, 1.0F, 0.01F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();

        if (entity.armSmoke && !entity.isDoom()) renderSmokeParticles(entity, partialTicks);
        if (entity.armFlash && !entity.isDoom()) renderFlashParticles(entity, partialTicks);
    }

    private void renderSmokeParticles(Vessel entity, float partialTicks) {
        if (entity.tickCount % 2 != 0) return;

        VesselModel<Vessel> model = this.getModel();

        Vec3 leftPos = ParticleHelper.calculateBoneWorldPosition(entity, partialTicks, model.vessel, model.body, model.waist, model.left_arm, model.left_arm_anchor);
        Vessel.cooldownParticle(entity.level(), entity.getRandom(), leftPos.x, leftPos.y, leftPos.z);

        Vec3 rightPos = ParticleHelper.calculateBoneWorldPosition(entity, partialTicks, model.vessel, model.body, model.waist, model.right_arm, model.right_arm_anchor);
        Vessel.cooldownParticle(entity.level(), entity.getRandom(), rightPos.x, rightPos.y, rightPos.z);
    }

    private void renderFlashParticles(Vessel entity, float partialTicks) {
        VesselModel<Vessel> model = this.getModel();

        Vec3 leftPos = ParticleHelper.calculateBoneWorldPosition(entity, partialTicks, model.vessel, model.body, model.waist, model.left_arm, model.left_arm_anchor);
        Vessel.shotgunFlashParticle(entity.level(), entity.getRandom(), leftPos.x, leftPos.y, leftPos.z);

        Vec3 rightPos = ParticleHelper.calculateBoneWorldPosition(entity, partialTicks, model.vessel, model.body, model.waist, model.right_arm, model.right_arm_anchor);
        Vessel.shotgunFlashParticle(entity.level(), entity.getRandom(), rightPos.x, rightPos.y, rightPos.z);

        entity.armFlash = false;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Vessel entity) {
        return NetherExp.netherexpPath("textures/entity/vessel.png");
    }

    public static class VesselModel<T extends Vessel> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("vessel"), "main");
        private final ModelPart vessel;
        private final ModelPart head;
        private final ModelPart body;
        private final ModelPart waist;
        private final ModelPart collar;
        private final ModelPart eyes;
        private final ModelPart left_arm;
        private final ModelPart left_arm_anchor;
        private final ModelPart right_arm;
        private final ModelPart right_arm_anchor;
        private final ModelPart left_leg;
        private final ModelPart right_leg;

        public VesselModel(ModelPart root) {
            this.vessel = root.getChild("vessel");
            this.head = this.vessel.getChild("head");
            this.body = this.vessel.getChild("body");
            this.waist = this.body.getChild("waist");
            this.collar = this.waist.getChild("collar");
            this.eyes = this.collar.getChild("eyes");
            this.left_arm = this.waist.getChild("left_arm");
            this.left_arm_anchor = this.left_arm.getChild("left_arm_anchor");
            this.right_arm = this.waist.getChild("right_arm");
            this.right_arm_anchor = this.right_arm.getChild("right_arm_anchor");
            this.left_leg = this.vessel.getChild("left_leg");
            this.right_leg = this.vessel.getChild("right_leg");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition vessel = partdefinition.addOrReplaceChild("vessel", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition head = vessel.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 16).addBox(0.0F, -9.0F, -5.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -35.0F, 0.0F));

            PartDefinition body = vessel.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -35.25F, 0.0F));

            PartDefinition waist = body.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(0, 47).addBox(-3.0F, -13.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 27).addBox(-4.0F, -13.0F, -3.0F, 8.0F, 14.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 19.25F, 0.0F));

            PartDefinition collar = waist.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(22, 8).addBox(-8.0F, -3.0F, -5.0F, 16.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

            PartDefinition eyes = collar.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(24, 0).addBox(-6.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.1F));

            PartDefinition left_arm = waist.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 35).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(36, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -13.0F, 0.0F));

            PartDefinition left_arm_anchor = left_arm.addOrReplaceChild("left_arm_anchor", CubeListBuilder.create(), PartPose.offset(1.0F, 17.0F, 0.0F));

            PartDefinition right_arm = waist.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(28, 35).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(28, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -13.0F, 0.0F));

            PartDefinition right_arm_anchor = right_arm.addOrReplaceChild("right_arm_anchor", CubeListBuilder.create(), PartPose.offset(-1.0F, 17.0F, 0.0F));

            PartDefinition left_leg = vessel.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(8, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -14.5F, 0.0F));

            PartDefinition right_leg = vessel.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -14.5F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(Vessel entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            this.applyHeadRotation(netHeadYaw, headPitch);

            this.animate(entity.idleAnimation, Animation.IDLE, ageInTicks);
            this.animate(entity.prepareAimAnimation, Animation.PREPARE_AIM, ageInTicks);
            this.animate(entity.aimAnimation, Animation.AIM, ageInTicks);
            this.animate(entity.shootAnimation, Animation.SHOOT, ageInTicks);
            this.animate(entity.blinkAnimation, Animation.BLINK, ageInTicks);
            this.animateWalk(Animation.MOVE, limbSwing, limbSwingAmount, 2.0f, 2.5f);
        }

        private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
            pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
            pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

            this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
            this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            vessel.render(poseStack, buffer, packedLight, packedOverlay, color);
        }

        @Override
        public ModelPart root() {
            return this.vessel;
        }
    }

    public static class VesselGlowLayer extends EyesLayer<Vessel, VesselModel<Vessel>> {
        public VesselGlowLayer(RenderLayerParent<Vessel, VesselModel<Vessel>> renderLayerParent) {
            super(renderLayerParent);
        }

        @Override
        public @NotNull RenderType renderType() {
            return RenderType.eyes(NetherExp.netherexpPath("textures/entity/vessel_glow.png"));
        }
    }

    private static class Animation {

        public static final AnimationDefinition PREPARE_AIM = AnimationDefinition.Builder.withLength(1f)
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 0f, 3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 1f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, -4f, -12f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, -0.03f, -1.87f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -2f, 6f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-41.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(-51.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(63.75f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.875f, KeyframeAnimations.degreeVec(-75f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("waist",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(35f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0.71f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.375f, KeyframeAnimations.degreeVec(50f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-127.92f, -7.92f, 6.13f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-69.81f, -9.76f, -2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 0.71f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.7083434f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(50f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(-127.92f, 7.92f, -6.13f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-67.31f, 9.76f, 2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition AIM = AnimationDefinition.Builder.withLength(1f).looping()
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -2f, 6f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-57.1f, -8.42f, -5.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-57.1f, 8.42f, 5.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("waist",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-15.01f, 2.41f, -0.65f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-15.01f, -2.41f, 0.65f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, -0.5f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, 0.25f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.5f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-69.81f, -9.76f, -2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-64.81f, -9.76f, -2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-64.36f, -14.28f, -4.38f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-69.36f, -14.28f, -4.38f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-69.81f, -9.76f, -2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, 0.25f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.7083434f, KeyframeAnimations.posVec(0f, -0.5f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, -0.5f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-69.36f, 14.28f, 4.38f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-69.81f, 9.76f, 2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-69.81f, 9.76f, 2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-64.81f, 9.76f, 2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-69.36f, 14.28f, 4.38f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition SHOOT = AnimationDefinition.Builder.withLength(2f)
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -2f, 6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -6f, 15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 1f, 8f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, -1f, 6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, -1f, -8f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -6f, -15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, -10f, -17f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.4167667f, KeyframeAnimations.posVec(0f, -10f, -17f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.625f, KeyframeAnimations.posVec(1f, 1f, 2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.posVec(1f, 0f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-57.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-125f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(-125f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(-82.42f, 7.37f, -4.77f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(65f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(105f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(105f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-32.5f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("waist",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-42.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(45f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(45f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.625f, KeyframeAnimations.degreeVec(-10f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.8343333f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-69.81f, -9.76f, -2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(-136.86f, 14.28f, 4.38f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-130.79f, 15.63f, 4.75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(-28.11f, 5.04f, -0.93f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-59.96f, 5.27f, -0.97f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(-47.7f, 5.03f, -0.91f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.3433333f, KeyframeAnimations.degreeVec(-42.46f, 5.27f, -0.97f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-47.7f, 5.03f, -0.91f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.875f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5416767f, KeyframeAnimations.posVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.8343333f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-69.81f, -9.76f, -2.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-136.86f, 14.28f, 4.38f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.degreeVec(-130.79f, 15.63f, 4.75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.875f, KeyframeAnimations.degreeVec(-28.11f, 5.04f, -0.93f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-59.96f, 5.27f, -0.97f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(-47.7f, 5.03f, -0.91f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.375f, KeyframeAnimations.degreeVec(-42.46f, 5.27f, -0.97f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5416767f, KeyframeAnimations.degreeVec(-47.7f, 5.03f, -0.91f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.6766667f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1.2f, 0.3f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.6766667f, KeyframeAnimations.scaleVec(1.2f, 0.3f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.scaleVec(1f, 1.5f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition MOVE = AnimationDefinition.Builder.withLength(1f).looping()
                .addAnimation("vessel",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, -5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(-1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.posVec(-1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.degreeVec(0f, 0f, 17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0f, 0f, -17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(-1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(12.5f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(21.62f, 0f, -7.4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(-5.93f, 0f, -6.63f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(12.5f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(12.5f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(21.62f, 0f, 7.4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(-5.93f, 0f, 6.63f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(12.5f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_leg",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.43f, -0.3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, -0.6f, 0.03f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0.69f, 0.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0.43f, -0.3f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_leg",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(12.46f, 0.4f, 0.52f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(19.04f, 0.23f, 0.29f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_leg",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.69f, 0.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0.43f, -0.3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, -0.6f, 0.03f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, -1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_leg",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(12.46f, 0.4f, 0.52f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(19.04f, 0.23f, 0.29f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(12.46f, 0.4f, 0.52f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("waist",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 12.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(4.96f, 10.01f, -0.22f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, -12.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(4.96f, -10.01f, 0.22f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 12.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(-1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.posVec(-1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.625f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, -10f, 15.63f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, -17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 7.5f, -20.31f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(-1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.9583434f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(14.98f, 0.44f, -12.17f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5416767f, KeyframeAnimations.degreeVec(-15.1f, 0.43f, -11.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4583433f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.9583434f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.4583433f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-15.1f, -0.43f, 11.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(4.98f, -0.44f, 12.17f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(14.98f, -0.44f, 12.17f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(-15.1f, -0.43f, 11.88f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_leg",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
        public static final AnimationDefinition BLINK = AnimationDefinition.Builder.withLength(0.5f)
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.scaleVec(1.2f, 0.3f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.scaleVec(1.2f, 0.3f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.3433333f, KeyframeAnimations.scaleVec(0.9f, 1.6f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
    }
}
