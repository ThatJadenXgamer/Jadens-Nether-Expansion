package net.jadenxgamer.netherexp.client.rendering.item;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.elysium_api.api.util.ClientItemData;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.keyframe.ItemHierarchicalModel;
import net.jadenxgamer.netherexp.core.item.ShotgunFistItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ShotgunFistModel extends ItemHierarchicalModel<ShotgunFistItem> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("shotgun_fist"), "main");
    private final ModelPart shotgun;
    private final ModelPart skull;
    private final ModelPart jaw;
    private final ModelPart trigger;
    private final ModelPart anchor;

    public ShotgunFistModel(ModelPart root) {
        this.shotgun = root.getChild("shotgun");
        this.skull = this.shotgun.getChild("skull");
        this.jaw = this.shotgun.getChild("jaw");
        this.trigger = this.shotgun.getChild("trigger");
        this.anchor = this.shotgun.getChild("anchor");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition shotgun = partdefinition.addOrReplaceChild("shotgun", CubeListBuilder.create().texOffs(0, 27).addBox(-11.0F, -10.0F, 3.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(-0.05F))
                .texOffs(0, 49).addBox(-12.0F, -11.0F, 13.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition skull = shotgun.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, 0.5F, -6.5F, 6.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -4.5F, -6.5F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -10.5F, 8.5F));

        PartDefinition jaw = shotgun.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(26, 16).addBox(-3.0F, -2.0F, -6.5F, 6.0F, 4.0F, 7.0F, new CubeDeformation(-0.02F)), PartPose.offset(-8.0F, -4.0F, 8.5F));

        PartDefinition trigger = shotgun.addOrReplaceChild("trigger", CubeListBuilder.create().texOffs(0, 43).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -4.0F, 12.0F));

        PartDefinition anchor = shotgun.addOrReplaceChild("anchor", CubeListBuilder.create(), PartPose.offset(-8.0F, -7.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void renderSmokeParticles(LivingEntity entity, PoseStack poseStack, ItemDisplayContext context) {
        if (entity.tickCount % 2 != 0) return;

        if (context != ItemDisplayContext.FIRST_PERSON_RIGHT_HAND && context != ItemDisplayContext.FIRST_PERSON_LEFT_HAND &&
                context != ItemDisplayContext.THIRD_PERSON_RIGHT_HAND && context != ItemDisplayContext.THIRD_PERSON_LEFT_HAND) return;

        poseStack.pushPose();
        this.shotgun.translateAndRotate(poseStack);
        this.anchor.translateAndRotate(poseStack);
        Vector3f positionVector = poseStack.last().pose().transformPosition(new Vector3f(0.0F, 0.0F, 0.0F));
        poseStack.popPose();

        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double x = cameraPos.x + positionVector.x();
        double y = cameraPos.y + positionVector.y();
        double z = cameraPos.z + positionVector.z();

        ShotgunFistItem.Client.cooldownParticle(entity, entity.level(), entity.getRandom(), x, y, z);
    }

    public void renderFlashParticles(ItemStack stack, LivingEntity entity, PoseStack poseStack, ItemDisplayContext context) {
        if (context != ItemDisplayContext.FIRST_PERSON_RIGHT_HAND && context != ItemDisplayContext.FIRST_PERSON_LEFT_HAND &&
                context != ItemDisplayContext.THIRD_PERSON_RIGHT_HAND && context != ItemDisplayContext.THIRD_PERSON_LEFT_HAND) return;

        poseStack.pushPose();
        this.shotgun.translateAndRotate(poseStack);
        this.anchor.translateAndRotate(poseStack);
        Vector3f positionVector = poseStack.last().pose().transformPosition(new Vector3f(0.0F, 0.0F, 0.0F));
        poseStack.popPose();

        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double x = cameraPos.x + positionVector.x();
        double y = cameraPos.y + positionVector.y();
        double z = cameraPos.z + positionVector.z();

        ShotgunFistItem.Client.shotgunFlashParticle(entity.level(), entity.getRandom(), x, y, z);
        ClientItemData.getOrCreate(stack).remove("shootFlash");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        shotgun.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setupAnim(Entity entity, ShotgunFistItem item, ItemStack stack, ItemDisplayContext displayContext, float ageInTicks) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity, ShotgunFistItem.fire, Animation.FIRE, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return shotgun;
    }

    private static class Animation {
        public static final AnimationDefinition FIRE = AnimationDefinition.Builder.withLength(1.0F)
                .addAnimation("shotgun", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("skull", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.0417F, KeyframeAnimations.degreeVec(16.88F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-52.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("skull", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.67F, 0.33F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, -2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.0417F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.1667F, KeyframeAnimations.degreeVec(50.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.2917F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5833F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("trigger", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.0417F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4167F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .build();
    }
}