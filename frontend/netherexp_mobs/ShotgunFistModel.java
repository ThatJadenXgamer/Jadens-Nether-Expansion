// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class ShotgunFistModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "shotgunfistmodel"), "main");
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

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		shotgun.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}