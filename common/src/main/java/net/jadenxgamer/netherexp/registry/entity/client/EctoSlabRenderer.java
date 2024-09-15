package net.jadenxgamer.netherexp.registry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.EctoSlab;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class EctoSlabRenderer extends MobRenderer<EctoSlab, EctoSlabModel<EctoSlab>> {
    public EctoSlabRenderer(EntityRendererProvider.Context context) {
        super(context, new EctoSlabModel<>(context.bakeLayer(JNEModelLayers.ECTO_SLAB_LAYER)), 0.25f);
    }

    @Override
    protected int getBlockLightLevel(EctoSlab entity, BlockPos pos) {
        return 15;
    }

    @Override
    public void render(EctoSlab ectoSlab, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (!ectoSlab.getIsUnderground()) {
            this.shadowRadius = 0.25f * (float)ectoSlab.getSize();
        }
        else this.shadowRadius = 0.0f;
        super.render(ectoSlab, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    protected void scale(EctoSlab ectoSlab, PoseStack poseStack, float f) {
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.001F, 0.0F);
        float size = (float)ectoSlab.getSize();
        float squish = Mth.lerp(f, ectoSlab.oSquish, ectoSlab.squish) / (size * 0.5F + 1.0F);
        float j = 1.0F / (squish + 1.0F);
        poseStack.scale(j * size, 1.0F / j * size, j * size);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(EctoSlab entity) {
        int size = Math.min(entity.getSize(), 4);
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/ecto_slab/ecto_slab_" + size + ".png");
    }
}
