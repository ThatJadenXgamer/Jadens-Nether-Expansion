package net.jadenxgamer.netherexp.mixin.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.entity.client.GhastFireBallModel;
import net.jadenxgamer.netherexp.registry.entity.client.JNEModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DragonFireballRenderer.class)
public abstract class DragonFireballRendererMixin extends EntityRenderer<DragonFireball> {

    @Unique
    private GhastFireBallModel<DragonFireball> largeFireballModel;

    protected DragonFireballRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void netherexp$init(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.largeFireballModel = new GhastFireBallModel<>(context.bakeLayer(JNEModelLayers.GHAST_FIREBALL_LAYER));
    }

    @Inject(
            method = "render(Lnet/minecraft/world/entity/projectile/DragonFireball;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$render(DragonFireball dragonFireball, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo cir) {
        if (JNEConfigs.REDESIGNED_FIREBALLS.get()) {
            cir.cancel();

            poseStack.pushPose();
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.translate(0.0, -0.8, 0.0);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(dragonFireball)));
            this.largeFireballModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            poseStack.popPose();
        }
    }

    @Inject(
            method = "getTextureLocation(Lnet/minecraft/world/entity/projectile/DragonFireball;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getTextureLocation(DragonFireball dragonFireball, CallbackInfoReturnable<ResourceLocation> cir) {
        if (JNEConfigs.REDESIGNED_FIREBALLS.get()) {
            cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/dragon_fireball.png"));
        }
    }
}
