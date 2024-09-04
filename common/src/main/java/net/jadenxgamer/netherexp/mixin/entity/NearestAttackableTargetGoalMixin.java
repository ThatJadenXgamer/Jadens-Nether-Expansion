package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
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
        // makes it so mobs do not target entities with the betrayed effect and instead focus on the candle
        if (!mob.getType().is(JNETags.EntityTypes.TARGETS_REGARDLESS_OF_BETRAYED) && this.target != null && this.target.hasEffect(JNEMobEffects.BETRAYED.get())) {
            cir.setReturnValue(false);
        }
    }
}
