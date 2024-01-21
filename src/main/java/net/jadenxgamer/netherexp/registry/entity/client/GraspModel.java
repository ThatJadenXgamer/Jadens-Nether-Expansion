package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.GraspEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GraspModel extends DefaultedEntityGeoModel<GraspEntity> {
    public GraspModel() {
        super(new Identifier(NetherExp.MOD_ID, "grasp"), true);
    }

    @Override
    public Identifier getModelResource(GraspEntity object) {
        return new Identifier(NetherExp.MOD_ID, "geo/grasp.geo.json");
    }
    @Override
    public Identifier getTextureResource(GraspEntity object) {
        return new Identifier(NetherExp.MOD_ID, "textures/entity/grasp.png");
    }
    @Override
    public Identifier getAnimationResource(GraspEntity animatable) {
        return new Identifier(NetherExp.MOD_ID, "animations/grasp.animation.json");
    }
}
