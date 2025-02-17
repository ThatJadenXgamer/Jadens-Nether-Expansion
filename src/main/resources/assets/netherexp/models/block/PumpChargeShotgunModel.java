// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class PumpChargeShotgunModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "pumpchargeshotgunmodel"), "main");
	private final ModelPart shotgun;

	public PumpChargeShotgunModel(ModelPart root) {
		this.shotgun = root.getChild("shotgun");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition shotgun = partdefinition.addOrReplaceChild("shotgun", CubeListBuilder.create().texOffs(7, 36).addBox(-3.0F, -3.5F, -10.25F, 6.0F, 7.0F, 8.0F, new CubeDeformation(-0.05F))
		.texOffs(0, 51).addBox(-4.0F, -4.5F, 4.75F, 8.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.5F, 0.25F));

		PartDefinition skull = shotgun.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, 0.75F, -7.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(32, 50).addBox(-4.0F, -5.25F, -7.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.25F, -4.25F));

		PartDefinition jaw = shotgun.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(29, 16).addBox(-3.0F, -2.5F, -7.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -4.25F));

		PartDefinition trigger = shotgun.addOrReplaceChild("trigger", CubeListBuilder.create().texOffs(22, 51).addBox(-1.0F, -0.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 3.25F));

		PartDefinition barrel = shotgun.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(38, 36).addBox(-3.0F, -3.5F, -3.5F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.02F, -0.02F, 1.25F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		shotgun.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}