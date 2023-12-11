package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.ApparitionEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ApparitionModel extends GeoModel<ApparitionEntity> {
    @Override
    public Identifier getModelResource(ApparitionEntity object) {
        switch (object.stage()) {
            default: {
                return new Identifier(NetherExp.MOD_ID, "geo/apparition.geo.json");
            }
            case 2: {
                return new Identifier(NetherExp.MOD_ID, "geo/apparition_stage2.geo.json");
            }
            case 3: {
                return new Identifier(NetherExp.MOD_ID, "geo/apparition_stage3.geo.json");
            }
            case 4:
        }
        return new Identifier(NetherExp.MOD_ID, "geo/apparition_stage4.geo.json");
    }
    @Override
    public Identifier getTextureResource(ApparitionEntity object) {

        switch (object.stage()) {
            default: {
                return new Identifier(NetherExp.MOD_ID, "textures/entity/apparition/stage1.png");
            }
            case 2: {
                return new Identifier(NetherExp.MOD_ID, "textures/entity/apparition/stage2.png");
            }
            case 3: {
                return new Identifier(NetherExp.MOD_ID, "textures/entity/apparition/stage3.png");
            }
            case 4:
        }
        return new Identifier(NetherExp.MOD_ID, "textures/entity/apparition/stage4.png");
    }
    @Override
    public Identifier getAnimationResource(ApparitionEntity animatable) {
        switch (animatable.stage()) {
            default: {
                return new Identifier(NetherExp.MOD_ID, "animations/apparition.animation.json");
            }
            case 2: {
                return new Identifier(NetherExp.MOD_ID, "animations/apparition_stage2.animation.json");
            }
            case 3: {
                return new Identifier(NetherExp.MOD_ID, "animations/apparition_stage3.animation.json");
            }
            case 4:
        }
        return new Identifier(NetherExp.MOD_ID, "animations/apparition_stage4.animation.json");
    }
}
