package net.jadenxgamer.netherexp.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.entity.custom.WarphopperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WarphopperModel extends GeoModel<WarphopperEntity> {

    @Override
    public Identifier getModelResource(WarphopperEntity object) {
        return new Identifier(NetherExp.MOD_ID, "geo/warphopper.geo.json");
    }
    @Override
    public Identifier getTextureResource(WarphopperEntity object) {
        return new Identifier(NetherExp.MOD_ID, "textures/entity/warphopper.png");
    }
    @Override
    public Identifier getAnimationResource(WarphopperEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "animations/warphopper.animation.json");
    }
}
