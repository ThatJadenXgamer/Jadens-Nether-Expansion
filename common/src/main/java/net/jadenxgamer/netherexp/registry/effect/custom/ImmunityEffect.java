package net.jadenxgamer.netherexp.registry.effect.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ImmunityEffect extends MobEffect {
    private final MobEffect mobEffect;

    public ImmunityEffect(MobEffectCategory category, int color, MobEffect mobEffect) {
        super(category, color);
        this.mobEffect = mobEffect;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int i) {
        super.applyEffectTick(entity, i);
        if (entity.hasEffect(this.mobEffect)) {
            int duration = entity.getEffect(this).getDuration();
            entity.playSound(JNESoundEvents.ANTIDOTE_NEGATE.get(), 1, 1);
            entity.removeEffect(mobEffect);
            entity.removeEffect(this);
            entity.addEffect(new MobEffectInstance(this, (duration - 600), 0));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
