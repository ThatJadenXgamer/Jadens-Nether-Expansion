package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.MistCharge;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MistChargeRenderer extends EntityRenderer<MistCharge> {

    private final MistChargeModel<MistCharge> model;

    public MistChargeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MistChargeModel<>(context.bakeLayer(JNEModelLayers.MIST_CHARGE_LAYER));
    }

    public MistChargeModel<MistCharge> getModel() {
        return model;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(MistCharge entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/mist_charge.png");
    }
}
