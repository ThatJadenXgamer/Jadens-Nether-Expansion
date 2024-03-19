package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.ApparitionEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class ApparitionModel extends DefaultedEntityGeoModel<ApparitionEntity> {
    public ApparitionModel() {
        super(new Identifier(NetherExp.MOD_ID, "apparition"), true);
    }

    @Override
    public Identifier getModelResource(ApparitionEntity object) {
        return new Identifier(NetherExp.MOD_ID, "geo/apparition.geo.json");
    }
    @Override
    public Identifier getTextureResource(ApparitionEntity object) {
        return new Identifier(NetherExp.MOD_ID, "textures/entity/apparition.png");
    }
    @Override
    public Identifier getAnimationResource(ApparitionEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "animations/apparition.animation.json");
    }
}
