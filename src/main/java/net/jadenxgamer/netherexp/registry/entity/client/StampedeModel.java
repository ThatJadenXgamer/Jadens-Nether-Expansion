package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.StampedeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class StampedeModel extends DefaultedEntityGeoModel<StampedeEntity> {
    public StampedeModel() {
        super(new Identifier(NetherExp.MOD_ID, "stampede"), true);
    }

    @Override
    public Identifier getModelResource(StampedeEntity object) {
        return new Identifier(NetherExp.MOD_ID, "geo/stampede.geo.json");
    }
    @Override
    public Identifier getTextureResource(StampedeEntity object) {
        return new Identifier(NetherExp.MOD_ID, "textures/entity/stampede.png");
    }
    @Override
    public Identifier getAnimationResource(StampedeEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "animations/stampede.animation.json");
    }
}
