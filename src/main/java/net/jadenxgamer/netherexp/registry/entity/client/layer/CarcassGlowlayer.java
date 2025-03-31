package net.jadenxgamer.netherexp.registry.entity.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.client.BansheeModel;
import net.jadenxgamer.netherexp.registry.entity.client.CarcassModel;
import net.jadenxgamer.netherexp.registry.entity.custom.Carcass;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class CarcassGlowlayer<T extends LivingEntity> extends EyesLayer<T, CarcassModel<T>> {
    public CarcassGlowlayer(RenderLayerParent<T, CarcassModel<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        ResourceLocation texture = new ResourceLocation(NetherExp.MOD_ID, "textures/entity/carcass/carcass_glow.png");
        if (pLivingEntity instanceof Carcass carcass && carcass.getIsImmortal()) {
            texture = new ResourceLocation(NetherExp.MOD_ID, "textures/entity/carcass/carcass_immortal_glow.png");
        }

        VertexConsumer consumer = pBuffer.getBuffer(RenderType.eyes(texture));
        this.getParentModel().renderToBuffer(pMatrixStack, consumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public @NotNull RenderType renderType() {
        return RenderType.eyes(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/carcass/carcass_glow.png"));
    }
}
