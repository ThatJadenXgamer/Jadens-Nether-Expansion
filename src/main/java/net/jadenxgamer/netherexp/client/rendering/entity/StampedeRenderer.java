package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.jadenxgamer.netherexp.core.entity.Vessel;
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
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StampedeRenderer extends MobRenderer<Stampede, StampedeRenderer.StampedeModel<Stampede>> {

    public StampedeRenderer(EntityRendererProvider.Context context) {
        super(context, new StampedeModel<>(context.bakeLayer(StampedeModel.LAYER)), 1.0f);
        this.addLayer(new StampedeGlowLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Stampede entity) {
        return NetherExp.netherexpPath("textures/entity/stampede/stampede.png");
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Stampede entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutout(getTextureLocation(entity));
    }

    public static class StampedeModel<T extends Stampede> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("stampede"), "main");
        private final ModelPart stampede;
        private final ModelPart head;
        private final ModelPart jaw;
        private final ModelPart right_ear;
        private final ModelPart left_ear;
        private final ModelPart left_leg;
        private final ModelPart left_foot;
        private final ModelPart right_leg;
        private final ModelPart right_foot;

        public StampedeModel(ModelPart root) {
            this.stampede = root.getChild("stampede");
            this.head = this.stampede.getChild("head");
            this.jaw = this.head.getChild("jaw");
            this.right_ear = this.jaw.getChild("right_ear");
            this.left_ear = this.jaw.getChild("left_ear");
            this.left_leg = this.stampede.getChild("left_leg");
            this.left_foot = this.left_leg.getChild("left_foot");
            this.right_leg = this.stampede.getChild("right_leg");
            this.right_foot = this.right_leg.getChild("right_foot");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition stampede = partdefinition.addOrReplaceChild("stampede", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

            PartDefinition head = stampede.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 37).addBox(-12.0F, -6.5F, -12.0917F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 87).addBox(-12.0F, -6.5F, -11.8417F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -28.5F, 0.0917F));

            PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -6.5F, -6.025F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.15F))
                    .texOffs(0, 74).addBox(-12.0F, -6.5F, -5.975F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0667F));

            PartDefinition right_ear = jaw.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 0.5F, 5.975F));

            PartDefinition left_ear = jaw.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, 0.5F, 5.975F));

            PartDefinition left_leg = stampede.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 74).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -21.0F, 0.0F));

            PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(64, 74).mirror().addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 18.5F, 0.0F));

            PartDefinition right_leg = stampede.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 74).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -21.0F, 0.0F));

            PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(64, 74).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 256);
        }

        @Override
        public void setupAnim(Stampede entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);

            this.animate(entity.idleAnimation, Animation.IDLE, ageInTicks);
            this.animate(entity.grinAnimation, Animation.GRIN, ageInTicks);
            this.animate(entity.chewAnimation, Animation.CHEW, ageInTicks);
            this.animateWalk(entity.isInLava() ? Animation.STRIDE : Animation.WALK, limbSwing, limbSwingAmount, 2.0f, 2.5f);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            stampede.render(poseStack, buffer, packedLight, packedOverlay, color);
        }

        @Override
        public ModelPart root() {
            return this.stampede;
        }
    }

    public static class StampedeGlowLayer extends EyesLayer<Stampede, StampedeModel<Stampede>> {
        public StampedeGlowLayer(RenderLayerParent<Stampede, StampedeModel<Stampede>> renderLayerParent) {
            super(renderLayerParent);
        }

        @Override
        public @NotNull RenderType renderType() {
            return RenderType.eyes(NetherExp.netherexpPath("textures/entity/stampede/stampede_glow.png"));
        }
    }

    private static class Animation {
        public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1.579F).looping()
                .addAnimation("stampede", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2632F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3947F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5263F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7895F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0526F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.1842F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3158F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("stampede", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3947F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1842F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2632F, KeyframeAnimations.degreeVec(15.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5263F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0526F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.3158F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.6216F, 4.9809F, -8.6822F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3947F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.8772F, KeyframeAnimations.degreeVec(31.8803F, -6.6782F, 10.5906F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2719F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(29.6216F, 4.9809F, -8.6822F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(29.6216F, -4.9809F, 8.6822F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.307F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7017F, KeyframeAnimations.degreeVec(31.8803F, 6.6782F, -10.5906F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.1842F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(29.6216F, -4.9809F, 8.6822F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2632F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7895F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2281F, KeyframeAnimations.degreeVec(31.56F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2632F, KeyframeAnimations.posVec(0.0F, -4.0F, -1.52F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0088F, KeyframeAnimations.posVec(0.0F, -2.0F, 1.54F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2281F, KeyframeAnimations.posVec(0.0F, 3.21F, 0.49F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4386F, KeyframeAnimations.degreeVec(31.56F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7895F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0526F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2193F, KeyframeAnimations.posVec(0.0F, -2.0F, 1.54F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4386F, KeyframeAnimations.posVec(0.0F, 3.21F, 0.49F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0526F, KeyframeAnimations.posVec(0.0F, -4.0F, -1.52F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.38F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4386F, KeyframeAnimations.degreeVec(20.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6579F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9211F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.1842F, KeyframeAnimations.degreeVec(8.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(4.38F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2632F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5263F, KeyframeAnimations.degreeVec(8.75F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9211F, KeyframeAnimations.degreeVec(4.38F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.3597F, KeyframeAnimations.degreeVec(20.63F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition STRIDE = AnimationDefinition.Builder.withLength(1.579F).looping()
                .addAnimation("stampede", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3947F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7895F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1842F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(0.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("stampede", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3947F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7895F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1842F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, -3.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2632F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5263F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7895F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0526F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.3158F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-33.8271F, 12.0992F, 1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.307F, KeyframeAnimations.degreeVec(30.9593F, 31.8996F, 13.3308F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4386F, KeyframeAnimations.degreeVec(28.6729F, 12.0992F, 1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7895F, KeyframeAnimations.degreeVec(-33.8271F, 12.0992F, 1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0965F, KeyframeAnimations.degreeVec(30.9593F, 31.8996F, 13.3308F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2281F, KeyframeAnimations.degreeVec(28.6729F, 12.0992F, 1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(-33.8271F, 12.0992F, 1.7157F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-33.8271F, -12.0992F, -1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.307F, KeyframeAnimations.degreeVec(30.9593F, -31.8996F, -13.3308F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4386F, KeyframeAnimations.degreeVec(28.6729F, -12.0992F, -1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7895F, KeyframeAnimations.degreeVec(-33.8271F, -12.0992F, -1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0965F, KeyframeAnimations.degreeVec(30.9593F, -31.8996F, -13.3308F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2281F, KeyframeAnimations.degreeVec(28.6729F, -12.0992F, -1.7157F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(-33.8271F, -12.0992F, -1.7157F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3947F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9211F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2719F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.0877F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3947F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9211F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0526F, KeyframeAnimations.posVec(0.0F, -3.91F, 2.24F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.2719F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3509F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6579F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0526F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.1316F, KeyframeAnimations.posVec(0.0F, -3.91F, 2.24F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3509F, KeyframeAnimations.posVec(0.0F, 3.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6579F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7456F, KeyframeAnimations.posVec(0.0F, 2.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0526F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.579F, KeyframeAnimations.posVec(0.0F, -3.0F, 7.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition GRIN = AnimationDefinition.Builder.withLength(1.0F)
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4583F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6667F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6667F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.875F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5833F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition CHEW = AnimationDefinition.Builder.withLength(1.0F).looping()
                .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 13.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 13.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 6.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.0F).looping()
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-2.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-2.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .build();
    }
}