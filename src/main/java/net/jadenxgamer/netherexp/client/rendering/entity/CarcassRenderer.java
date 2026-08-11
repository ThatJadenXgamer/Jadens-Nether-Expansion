package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.Carcass;
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
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CarcassRenderer extends MobRenderer<Carcass, CarcassRenderer.CarcassModel<Carcass>> {

    public CarcassRenderer(EntityRendererProvider.Context context) {
        super(context, new CarcassModel<>(context.bakeLayer(CarcassModel.LAYER)), 0.5f);
        this.addLayer(new CarcassGlowLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Carcass entity) {
        if (entity.isReanimated()) return NetherExp.netherexpPath("textures/entity/carcass/carcass_reanimated.png");
        return NetherExp.netherexpPath("textures/entity/carcass/carcass.png");
    }

    public static class CarcassModel<T extends Carcass> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("carcass"), "main");

        private final ModelPart main;
        private final ModelPart head;

        public CarcassModel(ModelPart root) {
            this.main = root.getChild("main");
            ModelPart carcass = main.getChild("carcass");
            this.head = carcass.getChild("head");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition carcass = main.addOrReplaceChild("carcass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition head = carcass.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 0).addBox(-2.0F, -16.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

            PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(30, 9).addBox(-3.0F, -0.5F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, -5.25F));

            PartDefinition fire = head.addOrReplaceChild("fire", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, -11.0F, -0.5F, 8.0F, 12.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, -14.0F, 0.0F));

            PartDefinition body = carcass.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 22).addBox(-5.0F, -0.5F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 39).addBox(-3.0F, 6.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.5F, 0.0F));

            PartDefinition left_arm = carcass.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 50).addBox(0.25F, -2.5F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 60).addBox(3.25F, -0.5F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.75F, -14.5F, 0.0F));

            PartDefinition right_arm = carcass.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-3.25F, -2.5F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(0, 60).mirror().addBox(-17.25F, -0.5F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.75F, -14.5F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            this.applyHeadRotation(netHeadYaw, headPitch);

            this.animate(entity.idleAnimationState, Animation.CARCASS_IDLE, ageInTicks);
            this.animate(entity.moveAnimationState, Animation.CARCASS_MOVE, ageInTicks);
            this.animate(entity.reanimateAnimationState, Animation.CARCASS_REANIMATE, ageInTicks);
            this.animate(entity.deactivateAnimationState, Animation.CARCASS_DEACTIVATE, ageInTicks);
            this.animate(entity.attackAnimationState, Animation.CARCASS_ATTACK, ageInTicks);
        }

        private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
            pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
            pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

            this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
            this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            this.main.render(poseStack, buffer, packedLight, packedOverlay, color);
        }

        @Override
        public @NotNull ModelPart root() {
            return this.main;
        }
    }


    private static class CarcassGlowLayer<T extends Carcass> extends RenderLayer<T, CarcassModel<T>> {

        public CarcassGlowLayer(RenderLayerParent<T, CarcassModel<T>> renderer) {
            super(renderer);
        }
        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entity.isReanimated()) return;

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(NetherExp.netherexpPath("textures/entity/carcass/carcass_glow.png")));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
        }
    }

    private static class Animation {
        public static final AnimationDefinition CARCASS_IDLE = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -5f, 5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-12.45f, -1.08f, -4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-12.45f, 1.08f, 4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(-12.45f, -1.08f, -4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.0834333f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5834333f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(12.45f, 1.08f, -4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.0834333f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5834333f, KeyframeAnimations.degreeVec(12.45f, -1.08f, 4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(14.84f, 58.71f, 19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(22.41f, 57.11f, 28.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(14.84f, 58.71f, 19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(22.41f, 57.11f, 28.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(14.84f, 58.71f, 19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(14.84f, -58.71f, -19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(22.41f, -57.11f, -28.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(14.84f, -58.71f, -19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(22.41f, -57.11f, -28.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(14.84f, -58.71f, -19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -13.44f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 13.97f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, -13.44f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 0f, 13.97f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1.4f, 0.9f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1.4f, 0.9f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.08343333f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.16766666f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3433333f, KeyframeAnimations.posVec(-0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.4167667f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.6766666f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8343334f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.9167666f, KeyframeAnimations.posVec(-0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.0834333f, KeyframeAnimations.posVec(0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.1676667f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.25f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.3433333f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.4167667f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(-0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5834333f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.6766667f, KeyframeAnimations.posVec(0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.75f, KeyframeAnimations.posVec(-0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.8343333f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.9167667f, KeyframeAnimations.posVec(0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
        public static final AnimationDefinition CARCASS_DEACTIVATE = AnimationDefinition.Builder.withLength(1.25f)
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -5f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, -2f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0.29f, 4.37f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.0834333f, KeyframeAnimations.posVec(0f, -4f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-12.45f, -1.08f, -4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-57.06f, 15.55f, 9.09f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-69.95f, -1.08f, -4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5834334f, KeyframeAnimations.degreeVec(-13.3f, -6.81f, 19.07f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(62.69f, -36.72f, -57.49f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.875f, KeyframeAnimations.degreeVec(49.02f, -70.68f, -55.66f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.0834333f, KeyframeAnimations.degreeVec(69.32f, -55.7f, -86.43f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.19f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, -4f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(-17.83f, 2.45f, 0.46f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(6.83f, 18.56f, 3.49f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 4.19f, -1.02f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 5.12f, -2.8f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, -4f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(14.84f, 58.71f, 19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(29.27f, 47.45f, 44f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(-63.79f, 34.74f, -49.17f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, 3.91f, -0.44f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 4.19f, -1.02f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 2.19f, -1.02f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8343334f, KeyframeAnimations.posVec(0f, -1.19f, -1.02f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, -4f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(14.84f, -58.71f, -19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.375f, KeyframeAnimations.degreeVec(29.27f, -47.45f, -44f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(5.69f, -53.64f, -13.78f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-63.79f, -34.74f, 49.17f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.degreeVec(32.5f, 0f, -9.38f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-26.2f, 0f, 10.16f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 27.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(0.95f, -0.21f, 59.99f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 55f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, 55f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.scaleVec(1.25f, 1.84f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1.9f, 1.27f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.scaleVec(0f, 0.2f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -0.64f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.77f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.3433333f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 1.36f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5416766f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(-1f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.875f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.3433333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.scaleVec(1f, 4.3f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5416766f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(1f, 1.3f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.scaleVec(1f, 0f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition CARCASS_REANIMATE = AnimationDefinition.Builder.withLength(1.0834333f)
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -2f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -5f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -4f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.0416767f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(69.32f, -55.7f, -86.43f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(82.94f, -26.27f, -36.04f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(57.79f, 2.69f, 12.21f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(37.26f, 2.22f, 5.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.8343334f, KeyframeAnimations.degreeVec(24.76f, 2.22f, 5.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.0416767f, KeyframeAnimations.degreeVec(-12.45f, -1.08f, -4.88f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.9167666f, KeyframeAnimations.scaleVec(1f, 2.6f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1.06f, 0.46f, 1.06f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.0834333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 2f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 1.13f, -1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-106.41f, -0.34f, 29.58f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-47.5f, 0f, 27.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(10.01f, 5.41f, 21.67f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.scaleVec(0.7f, 0.3f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1.1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -4f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(20.45f, -11.74f, -4.34f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(21.5f, 21.08f, 8.06f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -4f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 4.19f, 1.98f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 4.19f, 1.98f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-63.79f, 34.74f, -49.17f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(24.21f, 49.7f, 37.25f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.375f, KeyframeAnimations.degreeVec(24.21f, 49.7f, 37.25f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(14.84f, 58.71f, 19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -4f, -5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 4.19f, 1.98f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 4.19f, 1.98f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.875f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-63.79f, -34.74f, 49.17f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(24.21f, -49.7f, -37.25f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.degreeVec(24.21f, -49.7f, -37.25f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.875f, KeyframeAnimations.degreeVec(14.84f, -58.71f, -19.23f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition CARCASS_MOVE = AnimationDefinition.Builder.withLength(1f).looping()
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, -5f, 5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("carcass",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(5.41f, -3.21f, -14.66f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-12.09f, -3.21f, -14.66f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(5.41f, 3.21f, 14.66f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-12.09f, 3.21f, 14.66f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(5.41f, -3.21f, -14.66f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.08343333f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.16766666f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.3433333f, KeyframeAnimations.posVec(-0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.4167667f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5834334f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.6766666f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.8343334f, KeyframeAnimations.posVec(0.25f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.9167666f, KeyframeAnimations.posVec(-0.25f, 0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.posVec(-0.25f, -0.25f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 25f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 25f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.scaleVec(1.6f, 0.9f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(1f, 1.5f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1.4f, 1.3f, 1.2f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(21.5f, 21.08f, 8.06f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(21.5f, -21.08f, -8.06f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(21.5f, 21.08f, 8.06f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0.44f, -3.37f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(6.59f, 59.64f, 9.63f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(24.8f, 48.24f, 30.74f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-20.82f, 53.2f, -24.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(6.59f, 59.64f, 9.63f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, 3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0.44f, -3.37f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 1f, 3f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(24.8f, -48.24f, -30.74f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-20.82f, -53.2f, 24.2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(6.59f, -59.64f, -9.63f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(24.8f, -48.24f, -30.74f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition CARCASS_ATTACK = AnimationDefinition.Builder.withLength(1f)
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 0f, -4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("head",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.degreeVec(10f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(36.74f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-10f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("fire",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(30f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.degreeVec(-7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, 0f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.2916767f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(3f, 0f, -7.52f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, -4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.08343333f, KeyframeAnimations.degreeVec(0f, 0f, -75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(0f, 67.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.3433333f, KeyframeAnimations.degreeVec(0f, 67.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(-3f, 0f, -7.52f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, 0f, -4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(0f, -67.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, -67.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
    }
}