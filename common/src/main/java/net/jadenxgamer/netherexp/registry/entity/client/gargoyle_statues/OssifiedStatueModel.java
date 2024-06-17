package net.jadenxgamer.netherexp.registry.entity.client.gargoyle_statues;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class OssifiedStatueModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NetherExp.MOD_ID, "ossified_statue"), "main");
	private final ModelPart bone;

	public OssifiedStatueModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(16, 26).addBox(-6.0F, -15.0F, 8.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-20.0F, -6.0F, 0.0F, 20.0F, 6.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(-18.0F, -15.0F, 8.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 39).addBox(-16.0F, -21.0F, 9.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 59).addBox(-16.0F, -27.0F, 4.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 47).addBox(-14.0F, -21.0F, 8.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 77).addBox(-14.0F, -35.0F, 6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-10.0F, -37.0F, 5.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(8, 39).addBox(-6.0F, -21.0F, 9.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 24.0F, -10.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return bone;
	}
}