package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.WitherSkull;
import org.jetbrains.annotations.NotNull;

public class WispRenderer extends MobRenderer<Wisp, WispModel<Wisp>> {
    public WispRenderer(EntityRendererProvider.Context context) {
        super(context, new WispModel<>(context.bakeLayer(JNEModelLayers.WISP_LAYER)), 0.4f);
    }

    @Override
    protected int getBlockLightLevel(Wisp entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Wisp entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/wisp.png");
    }
}
