package net.jadenxgamer.netherexp.registry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.animation.JNEAnimationDefinition;
import net.jadenxgamer.netherexp.registry.entity.custom.Banshee;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BansheeModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NetherExp.MOD_ID, "banshee"), "main");
	private final ModelPart banshee;
	private final ModelPart body;

	public BansheeModel(ModelPart root) {
		this.banshee = root.getChild("banshee");
		this.body = banshee.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition banshee = partdefinition.addOrReplaceChild("banshee", CubeListBuilder.create(), PartPose.offset(8.0F, 6.0F, -3.5F));

		PartDefinition body = banshee.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -9.0F, -3.5F, 14.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 25).addBox(-6.0F, -1.0F, -10.5F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 1.0F, 8.0F));

		PartDefinition rod_orbit = banshee.addOrReplaceChild("rod_orbit", CubeListBuilder.create(), PartPose.offset(-8.0F, -1.0F, 4.5F));

		PartDefinition rod1 = rod_orbit.addOrReplaceChild("rod1", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(64, 6).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, 0.0F, -1.0F));

		PartDefinition rod2 = rod_orbit.addOrReplaceChild("rod2", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(64, 6).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.0F, -1.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition rod4 = rod_orbit.addOrReplaceChild("rod4", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(64, 6).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -1.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition rod3 = rod_orbit.addOrReplaceChild("rod3", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(64, 6).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, 0.0F, -1.0F, 0.0F, 0.0F, -3.1416F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animate(((Banshee) entity).idleAnimationState, JNEAnimationDefinition.BANSHEE_IDLE, ageInTicks);
		this.animate(((Banshee) entity).walkAnimationState, JNEAnimationDefinition.BANSHEE_WALK, ageInTicks);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.body.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.body.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		banshee.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return banshee;
	}
}