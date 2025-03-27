package net.jadenxgamer.netherexp.registry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.registry.entity.custom.Carcass;
import net.jadenxgamer.netherexp.registry.entity.custom.FalseCarcass;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEAnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
public class FalseCarcassModel<T extends Entity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "falsecarcassmodel"), "main");
	private final ModelPart main;
	private final ModelPart head;

	public FalseCarcassModel(ModelPart root) {
		this.main = root.getChild("main");
		ModelPart carcass = main.getChild("carcass");
		this.head = carcass.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition carcass = main.addOrReplaceChild("carcass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = carcass.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -12.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition fire = head.addOrReplaceChild("fire", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, -11.0F, -0.5F, 8.0F, 12.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(24, 5).addBox(-4.0F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, -4.25F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(24, 7).addBox(-4.0F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -4.25F));

		PartDefinition body = carcass.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

		PartDefinition left_arm = carcass.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(2.0F, -0.75F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 30).mirror().addBox(0.0F, -1.75F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, -9.25F, 0.0F));

		PartDefinition right_arm = carcass.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -1.75F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 35).addBox(-12.0F, -0.75F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -9.25F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animate(((FalseCarcass) entity).idleAnimationState, JNEAnimationDefinition.FALSE_CARCASS_IDLE, ageInTicks);
		this.animate(((FalseCarcass) entity).moveAnimationState, JNEAnimationDefinition.FALSE_CARCASS_MOVE, ageInTicks);
		this.animate(((FalseCarcass) entity).reanimateAnimationState, JNEAnimationDefinition.FALSE_CARCASS_REANIMATE, ageInTicks);
		this.animate(((FalseCarcass) entity).deactivateAnimationState, JNEAnimationDefinition.FALSE_CARCASS_DEACTIVATE, ageInTicks);
		this.animate(((FalseCarcass) entity).attackAnimationState, JNEAnimationDefinition.FALSE_CARCASS_ATTACK, ageInTicks);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
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
	public ModelPart root() {
		return main;
	}
}