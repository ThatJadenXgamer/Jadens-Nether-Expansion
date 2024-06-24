package net.jadenxgamer.netherexp.registry.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.animation.JNEAnimationDefinition;
import net.jadenxgamer.netherexp.registry.entity.custom.Stampede;
import net.jadenxgamer.netherexp.registry.entity.custom.Vessel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class StampedeModel<T extends Entity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NetherExp.MOD_ID, "stampede"), "main");

	private final ModelPart main;
	private final ModelPart head;

	public StampedeModel(ModelPart root) {
		this.main = root.getChild("main");
		this.head = main.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_leg = main.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 37).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -50.0F, 0.0F));

		PartDefinition right_lower = right_leg.addOrReplaceChild("right_lower", CubeListBuilder.create().texOffs(16, 37).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.5F, 0.0F));

		PartDefinition left_leg = main.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 62).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -50.0F, 0.0F));

		PartDefinition left_lower = left_leg.addOrReplaceChild("left_lower", CubeListBuilder.create().texOffs(48, 37).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.5F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -11.5F, -12.0F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.04F))
		.texOffs(72, -24).addBox(0.0F, -24.5F, -12.0F, 0.0F, 13.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -53.5F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(12, 0).addBox(0.0F, -2.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, -5.5F, 0.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -2.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -5.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animate(((Stampede) entity).idleAnimationState, JNEAnimationDefinition.STAMPEDE_IDLE, ageInTicks);
		this.animate(((Stampede) entity).walkAnimationState, JNEAnimationDefinition.STAMPEDE_WALK, ageInTicks);
		this.animate(((Stampede) entity).swimAnimationState, JNEAnimationDefinition.STAMPEDE_SWIMMING, ageInTicks);
		this.animate(((Stampede) entity).runAnimationState, JNEAnimationDefinition.STAMPEDE_RUN, ageInTicks);
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
	public @NotNull ModelPart root() {
		return main;
	}
}