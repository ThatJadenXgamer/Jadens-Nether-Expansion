package net.jadenxgamer.netherexp.mixin.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.NetherExp;
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
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.LargeFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownItemRenderer.class)
public abstract class ThrownItemRendererMixin <T extends Entity & ItemSupplier> extends EntityRenderer<T> {

    private final GhastFireBallModel<T> largeFireballModel;

    protected ThrownItemRendererMixin(EntityRendererProvider.Context context) {
        super(context);
        this.largeFireballModel = new GhastFireBallModel<>(context.bakeLayer(JNEModelLayers.GHAST_FIREBALL_LAYER));
    }

    @Inject(
            method = "render",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$render(T entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo cir) {
        if (entity instanceof LargeFireball) {
            cir.cancel();

            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
            this.largeFireballModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            poseStack.popPose();
        }
    }

    @Inject(
            method = "getTextureLocation",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getTextureLocation(Entity entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (entity instanceof LargeFireball) {
            cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "textures/entity/ghast_fireball.png"));
        }
    }
}
