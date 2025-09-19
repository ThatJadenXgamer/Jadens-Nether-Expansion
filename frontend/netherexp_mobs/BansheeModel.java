// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class BansheeModel<T extends Banshee> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "bansheemodel"), "main");
	private final ModelPart banshee;
	private final ModelPart body;
	private final ModelPart jaw;
	private final ModelPart rod_orbit;
	private final ModelPart rod1;
	private final ModelPart rod2;
	private final ModelPart rod4;
	private final ModelPart rod3;

	public BansheeModel(ModelPart root) {
		this.banshee = root.getChild("banshee");
		this.body = this.banshee.getChild("body");
		this.jaw = this.body.getChild("jaw");
		this.rod_orbit = this.banshee.getChild("rod_orbit");
		this.rod1 = this.rod_orbit.getChild("rod1");
		this.rod2 = this.rod_orbit.getChild("rod2");
		this.rod4 = this.rod_orbit.getChild("rod4");
		this.rod3 = this.rod_orbit.getChild("rod3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition banshee = partdefinition.addOrReplaceChild("banshee", CubeListBuilder.create(), PartPose.offset(8.0F, 6.0F, -3.5F));

		PartDefinition body = banshee.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -5.5F, -7.0F, 14.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -3.5F, 3.5F));

		PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 25).addBox(-6.0F, -1.0F, -10.5F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 4.5F));

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
	public void setupAnim(Banshee entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		banshee.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}