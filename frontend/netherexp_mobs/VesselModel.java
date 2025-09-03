// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class VesselModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "vesselmodel"), "main");
	private final ModelPart vessel;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart waist;
	private final ModelPart collar;
	private final ModelPart eyes;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public VesselModel(ModelPart root) {
		this.vessel = root.getChild("vessel");
		this.head = this.vessel.getChild("head");
		this.body = this.vessel.getChild("body");
		this.waist = this.body.getChild("waist");
		this.collar = this.waist.getChild("collar");
		this.eyes = this.collar.getChild("eyes");
		this.left_arm = this.waist.getChild("left_arm");
		this.right_arm = this.waist.getChild("right_arm");
		this.left_leg = this.vessel.getChild("left_leg");
		this.right_leg = this.vessel.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition vessel = partdefinition.addOrReplaceChild("vessel", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = vessel.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(0.0F, -9.0F, -5.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -35.0F, 0.0F));

		PartDefinition body = vessel.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -35.25F, 0.0F));

		PartDefinition waist = body.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(0, 47).addBox(-3.0F, -13.0F, -2.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 27).addBox(-4.0F, -13.0F, -3.0F, 8.0F, 14.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 19.25F, 0.0F));

		PartDefinition collar = waist.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(22, 8).addBox(-8.0F, -3.0F, -5.0F, 16.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

		PartDefinition eyes = collar.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(24, 0).addBox(-6.0F, -2.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.1F));

		PartDefinition left_arm = waist.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 35).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(36, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -13.0F, 0.0F));

		PartDefinition right_arm = waist.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(28, 35).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -13.0F, 0.0F));

		PartDefinition left_leg = vessel.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(8, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -14.5F, 0.0F));

		PartDefinition right_leg = vessel.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -14.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		vessel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}