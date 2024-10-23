package net.jadenxgamer.netherexp.mixin.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.architectury.platform.Platform;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.entity.client.FireballModel;
import net.jadenxgamer.netherexp.registry.entity.client.GhastFireBallModel;
import net.jadenxgamer.netherexp.registry.entity.client.JNEModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.LargeFireball;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(ThrownItemRenderer.class)
public abstract class ThrownItemRendererMixin <T extends Entity & ItemSupplier> extends EntityRenderer<T> {
    @Unique
    private GhastFireBallModel<T> largeFireballModel;

    @Unique
    private FireballModel<T> fireballModel;

    @Unique
    private boolean isConfigEnabled = JNEConfigs.REDESIGNED_FIREBALLS.get();

    protected ThrownItemRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Unique
    private boolean noEntityTextureModelFeature() {
        // turns off redesigned fireballs if either one of these mods are loaded
        return !Platform.isModLoaded("entity_model_features") && !Platform.isModLoaded("entity_texture_features");
    }

    @Inject(
            method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V",
            at = @At(value = "TAIL")
    )
    private void netherexp$init(EntityRendererProvider.Context context, CallbackInfo ci) {
        if (!noEntityTextureModelFeature()) {
            return;
        }
        if (isConfigEnabled) {
            this.largeFireballModel = new GhastFireBallModel<>(context.bakeLayer(JNEModelLayers.GHAST_FIREBALL_LAYER));
            this.fireballModel = new FireballModel<>(context.bakeLayer(JNEModelLayers.FIREBALL_LAYER));
        }
    }

    @Inject(
            method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;FZ)V",
            at = @At(value = "TAIL")
    )
    private void netherexp$initTwo(EntityRendererProvider.Context context, float f, boolean bl, CallbackInfo ci) {
        if (!noEntityTextureModelFeature()) {
            return;
        }
        if (isConfigEnabled) {
            this.largeFireballModel = new GhastFireBallModel<>(context.bakeLayer(JNEModelLayers.GHAST_FIREBALL_LAYER));
            this.fireballModel = new FireballModel<>(context.bakeLayer(JNEModelLayers.FIREBALL_LAYER));
        }
    }

    @Inject(
            method = "render",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$render(T entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo cir) {
        if (isConfigEnabled) {
            if (entity instanceof LargeFireball && largeFireballModel != null) {
                cir.cancel();

                poseStack.pushPose();
                poseStack.scale(1.5f, 1.5f, 1.5f);
                poseStack.translate(0.0, -0.8, 0.0);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
                this.largeFireballModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

                poseStack.popPose();
            }
            else if (entity instanceof Fireball && fireballModel != null) {
                cir.cancel();

                poseStack.pushPose();
                poseStack.translate(0.0, -1.0, 0.0);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
                this.fireballModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

                poseStack.popPose();
            }
        }
    }

    @Inject(
            method = "getTextureLocation",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getTextureLocation(Entity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (isConfigEnabled) {
            if (entity instanceof LargeFireball) {
                cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/ghast_fireball.png"));
            }
            else if (entity instanceof Fireball) {
                cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/fireball.png"));
            }
        }
    }
}
