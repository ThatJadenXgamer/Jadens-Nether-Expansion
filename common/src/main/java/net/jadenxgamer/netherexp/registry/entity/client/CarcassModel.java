package net.jadenxgamer.netherexp.registry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.registry.entity.animation.JNEAnimationDefinition;
import net.jadenxgamer.netherexp.registry.entity.custom.Carcass;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class CarcassModel<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "carcass"), "main");
    private final ModelPart main;
    private final ModelPart head;

    public CarcassModel(ModelPart root) {
        this.main = root.getChild("main");
        ModelPart carcass = main.getChild("carcass");
        this.head = carcass.getChild("head");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition carcass = main.addOrReplaceChild("carcass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = carcass.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -16.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(30, 9).addBox(-3.0F, -0.5F, 0.0F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, -5.25F));

        PartDefinition fire = head.addOrReplaceChild("fire", CubeListBuilder.create().texOffs(30, 0).addBox(-3.0F, -7.0F, -0.5F, 6.0F, 8.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, -14.0F, 0.0F));

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
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animate(((Carcass) entity).idleAnimationState, JNEAnimationDefinition.CARCASS_IDLE, ageInTicks);
        this.animate(((Carcass) entity).moveAnimationState, JNEAnimationDefinition.CARCASS_MOVE, ageInTicks);
        this.animate(((Carcass) entity).reanimateAnimationState, JNEAnimationDefinition.CARCASS_REANIMATE, ageInTicks);
        this.animate(((Carcass) entity).deactivateAnimationState, JNEAnimationDefinition.CARCASS_DEACTIVATE, ageInTicks);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return main;
    }
}