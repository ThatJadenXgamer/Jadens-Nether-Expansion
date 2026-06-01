package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.core.effect.JNEMobEffect;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    LivingEntity entity = ((LivingEntity) (Object) this);

    @Inject(
            method = "getBlockSpeedFactor",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$applyBlockSpeedFactorModifications(CallbackInfoReturnable<Float> cir) {
        if (entity.getType().is((JNETags.EntityTypes.IGNORES_SOUL_SAND_SLOWNESS))) cir.setReturnValue(1.0f);
    }

    @Inject(
            method = "onEffectRemoved",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;removeAttributeModifiers(Lnet/minecraft/world/entity/ai/attributes/AttributeMap;)V")
    )
    private void netherexp$onEffectRemoved(MobEffectInstance effectInstance, CallbackInfo ci) {
        if (effectInstance.getEffect().value() instanceof JNEMobEffect jneMobEffect) jneMobEffect.onEffectRemoved(((LivingEntity) (Object) this), effectInstance.amplifier);
    }
}
