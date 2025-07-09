package net.jadenxgamer.netherexp.core.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.EffectCure;

import java.util.List;
import java.util.Set;

public class IncurableEffect extends MobEffect {
    public IncurableEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public IncurableEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}
