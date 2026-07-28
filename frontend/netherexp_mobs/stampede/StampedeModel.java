// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class StampedeModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "stampedemodel"), "main");
	private final ModelPart stampede;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart right_ear;
	private final ModelPart left_ear;
	private final ModelPart left_leg;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_foot;

	public StampedeModel(ModelPart root) {
		this.stampede = root.getChild("stampede");
		this.head = this.stampede.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.right_ear = this.jaw.getChild("right_ear");
		this.left_ear = this.jaw.getChild("left_ear");
		this.left_leg = this.stampede.getChild("left_leg");
		this.left_foot = this.left_leg.getChild("left_foot");
		this.right_leg = this.stampede.getChild("right_leg");
		this.right_foot = this.right_leg.getChild("right_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition stampede = partdefinition.addOrReplaceChild("stampede", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head = stampede.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 37).addBox(-12.0F, -6.5F, -12.0917F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(0, 87).addBox(-12.0F, -6.5F, -11.8417F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -28.5F, 0.0917F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -6.5F, -6.025F, 24.0F, 13.0F, 24.0F, new CubeDeformation(0.15F))
		.texOffs(0, 74).addBox(-12.0F, -6.5F, -5.975F, 24.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0667F));

		PartDefinition right_ear = jaw.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 0.5F, 5.975F));

		PartDefinition left_ear = jaw.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -3.0F, 0.0F, 6.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, 0.5F, 5.975F));

		PartDefinition left_leg = stampede.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 74).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -21.0F, 0.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(64, 74).mirror().addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 18.5F, 0.0F));

		PartDefinition right_leg = stampede.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 74).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -21.0F, 0.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(64, 74).addBox(-4.0F, -0.5F, -4.0F, 8.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		stampede.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}