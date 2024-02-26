package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.WispEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class WispModel extends DefaultedEntityGeoModel<WispEntity> {
    public WispModel() {
        super(new Identifier(NetherExp.MOD_ID, "wisp"), true);
    }

    @Override
    public Identifier getModelResource(WispEntity object) {
        return new Identifier(NetherExp.MOD_ID, "geo/wisp.geo.json");
    }
    @Override
    public Identifier getTextureResource(WispEntity object) {
        return new Identifier(NetherExp.MOD_ID, "textures/entity/wisp.png");
    }
    @Override
    public Identifier getAnimationResource(WispEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "animations/wisp.animation.json");
    }
}
