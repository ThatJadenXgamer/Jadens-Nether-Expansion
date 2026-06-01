package net.jadenxgamer.netherexp.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class JNEMobEffect extends MobEffect {
    public JNEMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public void onEffectRemoved(LivingEntity livingEntity, int amplifier) {
    }
}
