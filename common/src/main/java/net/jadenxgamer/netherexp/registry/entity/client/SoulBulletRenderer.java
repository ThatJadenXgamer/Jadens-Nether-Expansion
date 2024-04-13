package net.jadenxgamer.netherexp.registry.entity.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SoulBulletRenderer extends ArrowRenderer<SoulBullet> {
    public SoulBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SoulBullet entity) {
        return new ResourceLocation(NetherExp.MOD_ID, "textures/entity/projectiles/soul_bullet.png");
    }
}
