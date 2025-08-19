package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import org.jetbrains.annotations.Nullable;

public class JNEBlazeRenderer extends BlazeRenderer {

    private static final ResourceLocation LIT = NetherExp.id("textures/entity/blaze/lit.png");

    private static final ResourceLocation DIM = NetherExp.id("textures/entity/blaze/dim.png");

    private static final ResourceLocation DULL = NetherExp.id("textures/entity/blaze/dull.png");

    private static final ResourceLocation FADING = NetherExp.id("textures/entity/blaze/fading.png");
    
    public JNEBlazeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Blaze entity) {
        if (JNEConfigs.DIMINISHING_BLAZES.get()) {
            float health = entity.getHealth();
            if (health >= 20) {
                return LIT;
            } else if (health >= 15.0F) {
                return DIM;
            } else if (health >= 6.0F) {
                return DULL;
            } else if (health >= 0.0F) {
                return FADING;
            }
        } return super.getTextureLocation(entity);
    }

    @Override
    protected int getBlockLightLevel(Blaze entity, BlockPos pos) {
        if (JNEConfigs.DIMINISHING_BLAZES.get()) {
            float health = entity.getHealth();
            if (health >= 20) {
                return 15;
            } else if (health >= 15.0F) {
                return 10;
            } else if (health >= 6.0F) {
                return 5;
            } else if (health >= 0.0F) {
                return 0;
            }
        } return super.getBlockLightLevel(entity, pos);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Blaze entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return JNERenderType.noShadeEntityCutoutNoCull(getTextureLocation(entity));
    }
}
