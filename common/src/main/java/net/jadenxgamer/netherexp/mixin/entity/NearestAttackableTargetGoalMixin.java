package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetGoalMixin {

    @Shadow @Nullable
    protected LivingEntity target;

    @Inject(
            method = "canUse",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void netherexp$canUse(CallbackInfoReturnable<Boolean> cir) {
        if (this.target != null && this.target.hasEffect(JNEMobEffects.BETRAYED.get())) {
            cir.setReturnValue(false);
        }
    }
}
