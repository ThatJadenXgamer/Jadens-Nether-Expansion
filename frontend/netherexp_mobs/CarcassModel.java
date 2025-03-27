// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class carcass - Converted<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "carcass_- converted"), "main");
	private final ModelPart main;

	public carcass - Converted(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition carcass = main.addOrReplaceChild("carcass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = carcass.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -16.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(30, 9).addBox(-3.0F, -0.5F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, -5.25F));

		PartDefinition fire = head.addOrReplaceChild("fire", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, -11.0F, -0.5F, 8.0F, 12.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, -14.0F, 0.0F));

		PartDefinition body = carcass.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 22).addBox(-5.0F, -0.5F, -5.0F, 10.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 39).addBox(-3.0F, 6.5F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.5F, 0.0F));

		PartDefinition left_arm = carcass.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 50).addBox(0.25F, -2.5F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 60).addBox(3.25F, -0.5F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.75F, -14.5F, 0.0F));

		PartDefinition right_arm = carcass.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-3.25F, -2.5F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 60).mirror().addBox(-17.25F, -0.5F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.75F, -14.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}