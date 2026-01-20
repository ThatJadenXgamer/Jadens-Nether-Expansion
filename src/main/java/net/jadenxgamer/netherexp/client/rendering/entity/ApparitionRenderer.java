package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.Apparition;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApparitionRenderer extends MobRenderer<Apparition, ApparitionRenderer.ApparitionModel<Apparition>> {

    public ApparitionRenderer(EntityRendererProvider.Context context) {
        super(context, new ApparitionModel<>(context.bakeLayer(ApparitionModel.LAYER)), 0.8f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Apparition entity) {
        return NetherExp.id("textures/entity/apparition.png");
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Apparition entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return JNERenderType.noShadeEntityCutoutNoCull(getTextureLocation(entity));
    }

    @Override
    protected int getBlockLightLevel(Apparition entity, BlockPos pos) {
        return 15;
    }

    public static class ApparitionModel<T extends Apparition> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.id("apparition"), "main");
        private final ModelPart main;
        private final ModelPart right_arm;
        private final ModelPart right_chain;
        private final ModelPart lower_right_chain;
        private final ModelPart left_arm;
        private final ModelPart left_chain;
        private final ModelPart lower_left_chain;
        private final ModelPart head;
        private final ModelPart body;
        private final ModelPart flame;
        private final ModelPart leg;

        public ApparitionModel(ModelPart root) {
            this.main = root.getChild("main");
            this.right_arm = this.main.getChild("right_arm");
            this.right_chain = this.right_arm.getChild("right_chain");
            this.lower_right_chain = this.right_chain.getChild("lower_right_chain");
            this.left_arm = this.main.getChild("left_arm");
            this.left_chain = this.left_arm.getChild("left_chain");
            this.lower_left_chain = this.left_chain.getChild("lower_left_chain");
            this.head = this.main.getChild("head");
            this.body = this.head.getChild("body");
            this.flame = this.body.getChild("flame");
            this.leg = this.body.getChild("leg");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition right_arm = main.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(56, 39).addBox(-13.25F, -3.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                    .texOffs(56, 23).addBox(-18.25F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.75F, -21.0F, 0.0F));

            PartDefinition right_chain = right_arm.addOrReplaceChild("right_chain", CubeListBuilder.create().texOffs(84, 36).addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.25F, 2.0F, 0.0F));

            PartDefinition lower_right_chain = right_chain.addOrReplaceChild("lower_right_chain", CubeListBuilder.create().texOffs(84, 44).addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

            PartDefinition left_arm = main.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(56, 51).mirror().addBox(5.25F, -3.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(56, 31).mirror().addBox(-0.75F, -2.0F, -2.0F, 19.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.75F, -21.0F, 0.0F));

            PartDefinition left_chain = left_arm.addOrReplaceChild("left_chain", CubeListBuilder.create().texOffs(84, 36).mirror().addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(14.25F, 2.0F, 0.0F));

            PartDefinition lower_left_chain = left_chain.addOrReplaceChild("lower_left_chain", CubeListBuilder.create().texOffs(84, 44).mirror().addBox(0.0F, 0.0F, -1.5F, 0.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

            PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -19.5F, 0.0F));

            PartDefinition body = head.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -11.5F, -7.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition flame = body.addOrReplaceChild("flame", CubeListBuilder.create().texOffs(56, 0).addBox(-7.0F, -8.5F, -7.0F, 14.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

            PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(0, 37).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.5F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(Apparition entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);

            this.animate(entity.attackAnimation, Animation.ATTACK, ageInTicks);
            this.animate(entity.idleAnimation, animationForPersonality(entity), ageInTicks);
            this.animateWalk(Animation.MOVE, limbSwing, limbSwingAmount, 2.0f, 2.5f);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            main.render(poseStack, buffer, packedLight, packedOverlay, color);
        }

        @Override
        public ModelPart root() {
            return this.main;
        }

        public static AnimationDefinition animationForPersonality(Apparition entity) {
            return switch (entity.getPersonality()) {
                case 2 -> Animation.IDLE2;
                case 3 -> Animation.IDLE3;
                case 4 -> Animation.IDLE4;
                default -> Animation.IDLE1;
            };
        }
    }

    private static class Animation {

        public static final AnimationDefinition IDLE1 = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(2f, -3f, -7f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(2f, 0f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(2f, -3f, -7f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(41.67f, 65.8f, 46.66f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-32.82f, 59.81f, -36.73f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(41.67f, 65.8f, 46.66f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(-2f, 0f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(-2f, -3f, -7f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(-2f, 0f, -3f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(-32.82f, -59.81f, 36.73f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(41.67f, -65.8f, -46.66f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(-32.82f, -59.81f, 36.73f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.41667f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("flame",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("leg",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -15f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -47.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.25f, KeyframeAnimations.degreeVec(0f, 0f, -46.25f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, -35f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 10f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 29.38f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -15f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -47.5f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, -35f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
        public static final AnimationDefinition MOVE = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 3f, 2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 3f, 2f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.4167667f, KeyframeAnimations.posVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 3f, 2f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("flame",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("leg",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
        public static final AnimationDefinition IDLE2 = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.41667f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 5f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(-2f, 0f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(-2f, -2f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(-2f, 0f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(-2f, -2f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(-2f, 0f, -6f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, -55f, -90f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(63.97f, -50.33f, -57.6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(90f, -55f, -90f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(116.03f, -50.33f, -122.4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(90f, -55f, -90f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(2f, 0f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(2f, -2f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(2f, 0f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(2f, -2f, -6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(2f, 0f, -6f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 55f, 90f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(116.03f, 50.33f, 122.4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(90f, 55f, 90f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(63.97f, 50.33f, 57.6f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(90f, 55f, 90f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("flame",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("leg",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.54167f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.04167f, KeyframeAnimations.degreeVec(-5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.54167f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(9.28f, -8.41f, 41.82f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.58333f, KeyframeAnimations.degreeVec(-36.55f, -9.07f, -12f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.08333f, KeyframeAnimations.degreeVec(-3.97f, 3.04f, 37.39f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.58333f, KeyframeAnimations.degreeVec(36.55f, 9.07f, -12f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(9.28f, -8.41f, 41.82f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.54167f, KeyframeAnimations.degreeVec(22.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.04167f, KeyframeAnimations.degreeVec(5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.54167f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(9.28f, 8.41f, -41.82f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.625f, KeyframeAnimations.degreeVec(36.55f, -9.07f, 12f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.08333f, KeyframeAnimations.degreeVec(3.97f, 3.04f, -37.39f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.58333f, KeyframeAnimations.degreeVec(-36.55f, 9.07f, 12f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(9.28f, 8.41f, -41.82f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
        public static final AnimationDefinition IDLE3 = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.41667f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("flame",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("leg",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(-1f, 0f, -7f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -57.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-45.49f, -63.09f, 54.75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, -57.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-45.49f, -63.09f, 54.75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, -57.5f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(1f, 0f, -7f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 57.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-45.49f, 63.09f, -54.75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 57.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-45.49f, 63.09f, -54.75f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 57.5f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(17.5f, 0f, -17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(15.27f, 8.65f, -46.34f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(17.5f, 0f, -17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -40f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.58333f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.08333f, KeyframeAnimations.degreeVec(0f, 0f, -40f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.58333f, KeyframeAnimations.degreeVec(0f, 0f, 52.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, -40f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(17.5f, 0f, 17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(15.27f, -8.65f, 46.34f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(17.5f, 0f, 17.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 40f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.58333f, KeyframeAnimations.degreeVec(0f, 0f, -52.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.08333f, KeyframeAnimations.degreeVec(0f, 0f, 40f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.58333f, KeyframeAnimations.degreeVec(0f, 0f, -52.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 40f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
        public static final AnimationDefinition IDLE4 = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 8f, 4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 5f, 4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 8f, 4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.41667f, KeyframeAnimations.posVec(0f, 5f, 4f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 8f, 4f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("main",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(35f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(7.5f, 0f, 7.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(7.5f, 0f, -7.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("flame",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.4f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("leg",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.LINEAR),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(-3f, 0f, -3f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(15.41f, -40.11f, -73.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(24.62f, -35.81f, -88.06f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(15.41f, -40.11f, -73.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(24.62f, -35.81f, -88.06f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(15.41f, -40.11f, -73.15f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(3f, 0f, -3f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(15.41f, 40.11f, 73.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(24.62f, 35.81f, 88.06f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(15.41f, 40.11f, 73.15f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(24.62f, 35.81f, 88.06f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(15.41f, 40.11f, 73.15f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_chain",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(1f, 2f, 2f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, -45f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(90f, -22.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(90f, -45f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(90f, -22.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(90f, -45f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_right_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.58333f, KeyframeAnimations.degreeVec(-24.25f, -6.28f, 31.35f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.08333f, KeyframeAnimations.degreeVec(17.5f, 0f, -27.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.58333f, KeyframeAnimations.degreeVec(-24.25f, -6.28f, 31.35f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_chain",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(-1f, 2f, 2f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 45f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(90f, 22.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(90f, 45f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(90f, 22.5f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(90f, 45f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("lower_left_chain",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 27.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.58333f, KeyframeAnimations.degreeVec(-24.25f, 6.28f, -31.35f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.08333f, KeyframeAnimations.degreeVec(17.5f, 0f, 27.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.58333f, KeyframeAnimations.degreeVec(-24.25f, 6.28f, -31.35f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 27.5f),
                                        AnimationChannel.Interpolations.LINEAR))).build();
        public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(0.45045f)
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.11261f, KeyframeAnimations.posVec(0f, -3f, 7f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.22523f, KeyframeAnimations.posVec(0f, -3f, 7f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.33784f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("right_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.05631f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16892f, KeyframeAnimations.degreeVec(-19.72f, -3.4f, -9.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.22523f, KeyframeAnimations.degreeVec(-17.71f, 4.8f, 14.42f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.33784f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.11261f, KeyframeAnimations.posVec(0f, -3f, 7f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.22523f, KeyframeAnimations.posVec(0f, -3f, 7f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.33784f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("left_arm",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.05631f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16892f, KeyframeAnimations.degreeVec(-19.72f, 3.4f, 9.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.22523f, KeyframeAnimations.degreeVec(-17.71f, -4.8f, -14.42f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.33784f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.11261f, KeyframeAnimations.posVec(0f, 3f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.22523f, KeyframeAnimations.posVec(0f, 3f, -3f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.45045f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.05631f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16892f, KeyframeAnimations.degreeVec(-19.72f, 3.4f, 9.41f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.22523f, KeyframeAnimations.degreeVec(-17.71f, -4.8f, -14.42f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.33784f, KeyframeAnimations.degreeVec(-9.1f, -1.36f, 1.19f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.45045f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
    }
}
