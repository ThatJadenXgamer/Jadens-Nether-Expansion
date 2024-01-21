package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.WarphopperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class WarphopperModel extends DefaultedEntityGeoModel<WarphopperEntity> {
    public WarphopperModel() {
        super(new Identifier(NetherExp.MOD_ID, "warphopper"), true);
    }

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
