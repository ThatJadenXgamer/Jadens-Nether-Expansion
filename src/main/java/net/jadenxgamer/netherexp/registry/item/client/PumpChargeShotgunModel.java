package net.jadenxgamer.netherexp.registry.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.elysium_api.api.keyframe.NonEntityHierarchicalModel;
import net.jadenxgamer.netherexp.registry.item.custom.PumpChargeShotgunItem;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEAnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class PumpChargeShotgunModel extends NonEntityHierarchicalModel {
	private ModelPart shotgun;

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
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
		super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
	}

	@Override
	public void setupAnim(Entity entity, ItemStack stack, float ageInTicks) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		PumpChargeShotgunItem item = (PumpChargeShotgunItem) stack.getItem();
		this.animate(entity, (item).fireAnimationState, JNEAnimationDefinition.PUMP_CHARGE_SHOTGUN_FIRE, ageInTicks);
		this.animate(entity, (item).explodeAnimationState, JNEAnimationDefinition.PUMP_CHARGE_SHOTGUN_EXPLODE, ageInTicks);
		this.animate(entity, (item).pumpAnimationState, JNEAnimationDefinition.PUMP_CHARGE_SHOTGUN_PUMP, ageInTicks);
		this.animate(entity, (item).overpumpAnimationState, JNEAnimationDefinition.PUMP_CHARGE_SHOTGUN_OVERPUMPED, ageInTicks);
	}


    public ModelPart root() {
        return shotgun;
    }
}