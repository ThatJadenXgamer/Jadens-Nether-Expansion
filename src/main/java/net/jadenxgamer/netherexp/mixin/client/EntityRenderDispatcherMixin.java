package net.jadenxgamer.netherexp.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Unique
    private static final RenderType LIGHT_RENDER_TYPE = RenderType.entityShadow(NetherExp.netherexpPath("textures/misc/light.png"));

//    @Inject(
//            method = "renderFlame",
//            at = @At(value = "HEAD"),
//            cancellable = true
//    )
//    private void netherexp$improvedBurn(PoseStack poseStack, MultiBufferSource buffer, Entity entity, Quaternionf quaternion, CallbackInfo ci) {
//        CommonParticles.burnParticle(entity.level(), entity.level().random, entity);
//        poseStack.pushPose();
//        poseStack.popPose();
//        ci.cancel();
//    }

//    @WrapOperation(
//            method = "renderShadow",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;")
//    )
//    private static VertexConsumer netherexp$renderCustomShadow(MultiBufferSource instance, RenderType renderType, Operation<VertexConsumer> original, @Local(name = {"entity"}) Entity entity) {
//        if (entity.getType().is(JNETags.EntityTypes.HAS_LIGHT_SHADOW) || entity.isCurrentlyGlowing() || entity.displayFireAnimation()) return original.call(instance, LIGHT_RENDER_TYPE);
//        return original.call(instance, renderType);
//    }
}
