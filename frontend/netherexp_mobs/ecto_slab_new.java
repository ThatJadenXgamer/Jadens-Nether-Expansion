// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class ecto_slab_new<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "ecto_slab_new"), "main");
	private final ModelPart ecto_slab;
	private final ModelPart body;
	private final ModelPart light;

	public ecto_slab_new(ModelPart root) {
		this.ecto_slab = root.getChild("ecto_slab");
		this.body = this.ecto_slab.getChild("body");
		this.light = this.ecto_slab.getChild("light");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition ecto_slab = partdefinition.addOrReplaceChild("ecto_slab", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition body = ecto_slab.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -6.0F, -11.0F, 22.0F, 12.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition light = ecto_slab.addOrReplaceChild("light", CubeListBuilder.create().texOffs(0, 34).addBox(-9.0F, -36.0F, -9.0F, 18.0F, 36.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		ecto_slab.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}