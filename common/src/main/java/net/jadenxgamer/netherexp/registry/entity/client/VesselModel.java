package net.jadenxgamer.netherexp.registry.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.registry.entity.animation.JNEAnimationDefinition;
import net.jadenxgamer.netherexp.registry.entity.custom.Vessel;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class VesselModel<T extends Entity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "vesselmodel"), "main");
	private final ModelPart vessel;
	private final ModelPart head;

	public VesselModel(ModelPart root) {
		this.vessel = root.getChild("vessel");
		this.head = vessel.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition vessel = partdefinition.addOrReplaceChild("vessel", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = vessel.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(0.0F, -9.0F, -5.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -35.0F, 0.0F));

		PartDefinition body = vessel.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 47).addBox(-3.0F, 6.25F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 27).addBox(-4.0F, 6.25F, -3.0F, 8.0F, 14.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -35.25F, 0.0F));

		PartDefinition collar = body.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(22, 8).addBox(-8.0F, -3.0F, -5.0F, 16.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.25F, 0.0F));

		PartDefinition eyes = collar.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(24, 0).addBox(-6.0F, -26.0F, -5.1F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_arm = vessel.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 35).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(36, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -29.0F, 0.0F));

		PartDefinition right_arm = vessel.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(28, 35).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -29.0F, 0.0F));

		PartDefinition left_leg = vessel.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(8, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -14.5F, 0.0F));

		PartDefinition right_leg = vessel.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -14.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animate(((Vessel) entity).idleAnimationState, JNEAnimationDefinition.VESSEL_IDLE, ageInTicks);
		this.animate(((Vessel) entity).walkAnimationState, JNEAnimationDefinition.VESSEL_WALK, ageInTicks);
		this.animate(((Vessel) entity).aimAnimationState, JNEAnimationDefinition.VESSEL_AIM, ageInTicks);
		this.animate(((Vessel) entity).aimIdleAnimationState, JNEAnimationDefinition.VESSEL_AIM_IDLE, ageInTicks);
		this.animate(((Vessel) entity).shootAnimationState, JNEAnimationDefinition.VESSEL_SHOOT, ageInTicks);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		vessel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return vessel;
	}
}