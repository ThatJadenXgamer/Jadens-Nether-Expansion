package net.jadenxgamer.netherexp.mixin.entity.ai;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetGoalMixin extends TargetGoal {

    @Shadow @Nullable
    protected LivingEntity target;

    public NearestAttackableTargetGoalMixin(Mob mob, boolean bl) {
        super(mob, bl);
    }

    @Inject(
            method = "canUse",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void netherexp$canUse(CallbackInfoReturnable<Boolean> cir) {
        if (target == null) return;
        if (!mob.getType().is(JNETags.EntityTypes.TARGETS_REGARDLESS_OF_BETRAYED) && this.target.hasEffect(JNEMobEffects.BETRAYED)) cir.setReturnValue(false);
    }
}
