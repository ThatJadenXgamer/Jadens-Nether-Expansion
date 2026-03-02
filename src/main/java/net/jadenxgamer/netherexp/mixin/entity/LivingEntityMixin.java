package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
        if (entity.getType().is((JNETags.EntityTypes.IGNORES_SOUL_SAND_SLOWNESS)) ||
                (entity.hasEffect(JNEMobEffects.UNBOUNDED_SPEED) && entity.getBlockStateOn().is(JNETags.Blocks.UNBOUNDED_SPEED_BLOCKS))) cir.setReturnValue(1.0f);
    }
}
