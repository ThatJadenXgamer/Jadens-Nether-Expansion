package net.jadenxgamer.netherexp.registry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.registry.entity.animation.JNEAnimationDefinition;
import net.jadenxgamer.netherexp.registry.entity.custom.Stampede;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class StampedeModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart stampede;
	private final ModelPart head;

	public StampedeModel(ModelPart root) {
		this.stampede = root.getChild("stampede");
		this.head = stampede.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stampede = partdefinition.addOrReplaceChild("stampede", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head = stampede.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 37).addBox(-12.0F, -6.5F, -6.125F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(0, 87).addBox(-12.0F, -6.5F, -5.875F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -28.5F, -5.875F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -6.5F, -6.025F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.15F))
				.texOffs(0, 74).addBox(-12.0F, -6.5F, -5.975F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.1F));

		PartDefinition right_ear = jaw.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 0.5F, 5.975F));

		PartDefinition left_ear = jaw.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, 0.5F, 5.975F));

		PartDefinition left_leg = stampede.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 74).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -21.0F, 0.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(64, 74).mirror().addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 18.5F, 0.0F));

		PartDefinition right_leg = stampede.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 74).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -21.0F, 0.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(64, 74).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animateWalk(JNEAnimationDefinition.STAMPEDE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((Stampede) entity).idleAnimationState, JNEAnimationDefinition.STAMPEDE_IDLE, ageInTicks, 1f);
		this.animate(((Stampede) entity).grinAnimationState, JNEAnimationDefinition.STAMPEDE_GRIN, ageInTicks);
		this.animate(((Stampede) entity).chewAnimationState, JNEAnimationDefinition.STAMPEDE_CHEW, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		stampede.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public @NotNull ModelPart root() {
		return stampede;
	}
}