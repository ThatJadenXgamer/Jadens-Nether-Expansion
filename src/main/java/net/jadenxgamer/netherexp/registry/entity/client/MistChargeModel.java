package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.MistChargeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MistChargeModel extends GeoModel<MistChargeEntity> {
    @Override
    public Identifier getModelResource(MistChargeEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "geo/mist_charge.geo.json");
    }

    @Override
    public Identifier getTextureResource(MistChargeEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "textures/entity/mist_charge.png");
    }

    @Override
    public Identifier getAnimationResource(MistChargeEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "animations/mist_charge.animation.json");
    }
}
