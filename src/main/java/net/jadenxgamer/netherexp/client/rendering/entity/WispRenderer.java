package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.Wisp;
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

public class WispRenderer extends MobRenderer<Wisp, WispRenderer.WispModel<Wisp>> {

    public WispRenderer(EntityRendererProvider.Context context) {
        super(context, new WispModel<>(context.bakeLayer(WispModel.LAYER)), 0.2f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Wisp entity) {
        return NetherExp.netherexpPath("textures/entity/wisp.png");
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Wisp entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return JNERenderType.noShadeEntityCutoutNoCull(getTextureLocation(entity));
    }

    @Override
    protected int getBlockLightLevel(Wisp entity, BlockPos pos) {
        return 15;
    }

    public static class WispModel<T extends Wisp> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("wisp"), "main");
        private final ModelPart wisp;
        private final ModelPart body;
        private final ModelPart eyes;
        private final ModelPart tail;

        public WispModel(ModelPart root) {
            this.wisp = root.getChild("wisp");
            this.body = this.wisp.getChild("body");
            this.eyes = this.body.getChild("eyes");
            this.tail = this.body.getChild("tail");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition wisp = partdefinition.addOrReplaceChild("wisp", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition body = wisp.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.5F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 0.0F));

            PartDefinition eyes = body.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.1F));

            PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 0.0F));

            return LayerDefinition.create(meshdefinition, 32, 32);
        }

        @Override
        public void setupAnim(Wisp entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);

            this.animate(entity.idleAnimation, Animation.IDLE, ageInTicks);
            this.animateWalk(Animation.MOVE, limbSwing, limbSwingAmount, 2.0f, 2.5f);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            wisp.render(poseStack, buffer, packedLight, packedOverlay, color);
        }

        @Override
        public ModelPart root() {
            return this.wisp;
        }

        public ModelPart body() {
            return this.body;
        }

        public ModelPart eyes() {
            return eyes;
        }

        public ModelPart tail() {
            return tail;
        }
    }

    private static class Animation {

        public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(4f).looping()
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(10f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-10f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(-10f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(10f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(10f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.5f, KeyframeAnimations.degreeVec(-10f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3f, KeyframeAnimations.degreeVec(-10f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.5f, KeyframeAnimations.degreeVec(10f, 0f, 10f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(4f, KeyframeAnimations.degreeVec(10f, 0f, -10f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.scaleVec(0.9f, 1f, 0.9f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.scaleVec(0.9f, 1f, 0.9f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.25f, KeyframeAnimations.scaleVec(0.9f, 1f, 0.9f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.5f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.25f, KeyframeAnimations.scaleVec(0.9f, 1f, 0.9f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.5f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(4f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("tail",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.5f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.5f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(4f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0.08333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.scaleVec(1.2f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.41667f, KeyframeAnimations.scaleVec(0.9f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.58333f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.16667f, KeyframeAnimations.scaleVec(1.2f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.33333f, KeyframeAnimations.scaleVec(0.9f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.16667f, KeyframeAnimations.scaleVec(1.2f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.33333f, KeyframeAnimations.scaleVec(0.9f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.16667f, KeyframeAnimations.scaleVec(1.2f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.33333f, KeyframeAnimations.scaleVec(0.9f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(3.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
        public static final AnimationDefinition MOVE = AnimationDefinition.Builder.withLength(2f).looping()
                .addAnimation("wisp",
                        new AnimationChannel(AnimationChannel.Targets.POSITION,
                                new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("wisp",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(25f, 0f, 0f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
                                        AnimationChannel.Interpolations.LINEAR)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.ROTATION,
                                new Keyframe(0f, KeyframeAnimations.degreeVec(12.5f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-12.5f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.degreeVec(12.5f, 0f, -12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-12.5f, 0f, -12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.degreeVec(12.5f, 0f, 12.5f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("body",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1.1f, 0.7f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1.1f, 0.7f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1.1f, 0.7f, 1.1f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("tail",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.75f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(2f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM)))
                .addAnimation("eyes",
                        new AnimationChannel(AnimationChannel.Targets.SCALE,
                                new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.16667f, KeyframeAnimations.scaleVec(1.2f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.33333f, KeyframeAnimations.scaleVec(0.9f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.16667f, KeyframeAnimations.scaleVec(1.2f, 0.8f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.33333f, KeyframeAnimations.scaleVec(0.9f, 1.2f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM),
                                new Keyframe(1.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                        AnimationChannel.Interpolations.CATMULLROM))).build();
    }
}