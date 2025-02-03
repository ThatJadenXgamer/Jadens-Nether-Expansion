package net.jadenxgamer.netherexp.registry.effect.custom;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ImmunityEffect extends MobEffect {
    private final ResourceLocation effectResourceLocation;

    public ImmunityEffect(MobEffectCategory category, int color, ResourceLocation mobEffect) {
        super(category, color);
        this.effectResourceLocation = mobEffect;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int i) {
        super.applyEffectTick(entity, i);
        MobEffect mobEffect = BuiltInRegistries.MOB_EFFECT.get(this.effectResourceLocation);
        if (mobEffect != null && entity.hasEffect(mobEffect)) {
            int duration = entity.getEffect(this).getDuration();
            int amplifier = JNEConfigs.AMPLIFIER_IMMUNITY_REDUCTION.get() ? 0 : entity.getEffect(mobEffect).getAmplifier();
            entity.playSound(JNESoundEvents.ANTIDOTE_NEGATE.get(), 1, 1);
            entity.removeEffect(mobEffect);
            entity.removeEffect(this);
            entity.addEffect(new MobEffectInstance(this, (duration - (600 * amplifier)), 0));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
