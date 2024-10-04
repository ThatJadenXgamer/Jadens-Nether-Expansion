package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.client.layer.BansheeGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.client.layer.CarcassGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.client.layer.WispGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.custom.Banshee;
import net.jadenxgamer.netherexp.registry.entity.custom.Wisp;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BansheeRenderer extends MobRenderer<Banshee, BansheeModel<Banshee>> {
    public BansheeRenderer(EntityRendererProvider.Context context) {
        super(context, new BansheeModel<>(context.bakeLayer(JNEModelLayers.BANSHEE_LAYER)), 0.5f);
        this.addLayer(new BansheeGlowlayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Banshee entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/banshee.png");
    }

    protected int getBlockLightLevel(Banshee entity, BlockPos pos) {
        return 15;
    }
}
