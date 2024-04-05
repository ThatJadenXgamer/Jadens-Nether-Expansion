package net.jadenxgamer.netherexp.registry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.animation.JNEAnimationDefinition;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ApparitionModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NetherExp.MOD_ID, "apparition"), "main");
	private final ModelPart main;
	private final ModelPart head;

	public ApparitionModel(ModelPart root) {
		this.main = root.getChild("main");
		this.head = main.getChild("head");
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
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		//this.animateWalk(JNEAnimationDefinition.APPARITION_WALK1, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((Apparition) entity).idleAnimationState, JNEAnimationDefinition.APPARITION_IDLE1, ageInTicks);
		this.animate(((Apparition) entity).walkAnimationState, JNEAnimationDefinition.APPARITION_WALK1, ageInTicks);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return main;
	}
}