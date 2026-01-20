package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.client.rendering.keyframe.ItemHierarchicalModel;
import net.jadenxgamer.netherexp.core.entity.WillOWisp;
import net.jadenxgamer.netherexp.core.item.WillOWispItem;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WillOWispRenderer extends EntityRenderer<WillOWisp> {
    private final WillOWispModel<WillOWisp> model;

    public WillOWispRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.2F;
        this.model = new WillOWispModel<>(context.bakeLayer(WillOWispModel.LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(WillOWisp entity) {
        return NetherExp.id("textures/entity/will_o_wisp.png");
    }

    @Override
    public void render(WillOWisp entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0f, 0.0f, 0.0f);

        float yRot = entity.getYRot() + partialTicks * (entity.getYRot() - entity.yRotO);
        float xRot = entity.getXRot() + partialTicks * (entity.getXRot() - entity.xRotO);

        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-180));

        this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = buffer.getBuffer(JNERenderType.noShadeEntityCutoutNoCull(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public static class WillOWispModel<T extends WillOWisp> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.id("will_o_wisp"), "main");
        private final ModelPart main;
        private final ModelPart wisp;
        private final ModelPart pivot;

        public WillOWispModel(ModelPart root) {
            this.main = root.getChild("main");
            this.wisp = this.main.getChild("wisp");
            this.pivot = this.wisp.getChild("pivot");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

            PartDefinition wisp = main.addOrReplaceChild("wisp", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition pivot = wisp.addOrReplaceChild("pivot", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 32, 32);
        }


        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);

            this.animate(entity.loopAnimation, Animation.LOOP, ageInTicks);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            wisp.render(poseStack, buffer, packedLight, packedOverlay);
        }

        @Override
        public ModelPart root() {
            return main;
        }
    }

    public static class WillOWispItemModel extends ItemHierarchicalModel<WillOWispItem> {
        public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.id("will_o_wisp_item"), "main");
        public static final ModelLayerLocation LAYER_HAND = new ModelLayerLocation(NetherExp.id("will_o_wisp_item"), "hand");
        private final ModelPart main;
        private final ModelPart wisp;
        private final ModelPart pivot;

        public WillOWispItemModel(ModelPart root) {
            super(JNERenderType::noShadeEntityCutout);
            this.main = root.getChild("main");
            this.wisp = this.main.getChild("wisp");
            this.pivot = this.wisp.getChild("pivot");
        }

        public static LayerDefinition createHandLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition rightArm = partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 27.0F, 8.0F, -1.5708F, 0.0F, -1.5708F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        public static LayerDefinition createOrbLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

            PartDefinition wisp = main.addOrReplaceChild("wisp", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition pivot = wisp.addOrReplaceChild("pivot", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 32, 32);
        }

        @Override
        public void setupAnim(Entity entity, WillOWispItem item, ItemStack stack, ItemDisplayContext displayContext, float ageInTicks) {
            this.root().getAllParts().forEach(ModelPart::resetPose);

            this.animate(entity, WillOWispItem.held, Animation.HELD, ageInTicks);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            wisp.render(poseStack, buffer, packedLight, packedOverlay);
        }

        @Override
        public ModelPart root() {
            return main;
        }
    }

    private static class Animation {
        public static final AnimationDefinition LOOP = AnimationDefinition.Builder.withLength(1.0F).looping()
                .addAnimation("wisp", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 360.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("pivot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(1.56F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition HELD = AnimationDefinition.Builder.withLength(2.0408F).looping()
                .addAnimation("wisp", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7653F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0204F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.7007F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0408F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("wisp", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0204F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0408F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("pivot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5102F, KeyframeAnimations.degreeVec(2.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0204F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5306F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.0408F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("RightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.6963F, 0.1944F, 29.609F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0204F, KeyframeAnimations.degreeVec(-0.5393F, -1.9791F, 29.6282F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0408F, KeyframeAnimations.degreeVec(0.6963F, 0.1944F, 29.609F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();
    }
}