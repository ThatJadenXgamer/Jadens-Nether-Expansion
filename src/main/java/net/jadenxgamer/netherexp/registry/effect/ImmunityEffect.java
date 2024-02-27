package net.jadenxgamer.netherexp.registry.effect;

import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ImmunityEffect extends StatusEffect {
    private final StatusEffect statusEffect;

    protected ImmunityEffect(StatusEffectCategory category, int color, StatusEffect statusEffect) {
        super(category, color);
        this.statusEffect = statusEffect;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        if (entity.hasStatusEffect(this.statusEffect)) {
            int duration = entity.getStatusEffect(this).getDuration();
            entity.playSound(JNESoundEvents.ANTIDOTE_NEGATE, 1, 1);
            entity.removeStatusEffect(statusEffect);
            entity.removeStatusEffect(this);
            entity.addStatusEffect(new StatusEffectInstance(this, (duration - 400), 0));
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
