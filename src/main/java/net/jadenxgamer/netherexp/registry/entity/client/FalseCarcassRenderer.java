package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.client.layer.CarcassGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.client.layer.FalseCarcassGlowlayer;
import net.jadenxgamer.netherexp.registry.entity.custom.Carcass;
import net.jadenxgamer.netherexp.registry.entity.custom.FalseCarcass;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FalseCarcassRenderer extends MobRenderer<FalseCarcass, FalseCarcassModel<FalseCarcass>> {
    public FalseCarcassRenderer(EntityRendererProvider.Context context) {
        super(context, new FalseCarcassModel<>(context.bakeLayer(JNEModelLayers.FALSE_CARCASS_LAYER)), 0.5f);
        this.addLayer(new FalseCarcassGlowlayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(FalseCarcass entity) {
        if (entity.getIsReanimated()) {
            return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/carcass/false_carcass_reanimated.png");
        }
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/carcass/false_carcass.png");
    }
}
