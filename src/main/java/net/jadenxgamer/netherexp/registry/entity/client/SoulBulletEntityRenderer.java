package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBulletEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class SoulBulletEntityRenderer extends ProjectileEntityRenderer<SoulBulletEntity> {
    public SoulBulletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SoulBulletEntity entity) {
        return new Identifier(NetherExp.MOD_ID, "textures/entity/projectiles/soul_bullet.png");
    }
}
