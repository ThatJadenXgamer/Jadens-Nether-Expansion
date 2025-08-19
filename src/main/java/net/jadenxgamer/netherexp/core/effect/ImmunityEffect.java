package net.jadenxgamer.netherexp.core.effect;

import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public class ImmunityEffect extends IncurableEffect {

    private final ResourceLocation immunityOf;
    public final Function<MobEffectInstance, ParticleOptions> immunityParticleFactory;

    public ImmunityEffect(MobEffectCategory category, ResourceLocation immunityOf) {
        super(category, 0);
        this.immunityOf = immunityOf;
        this.immunityParticleFactory = (particle) -> {
            int alpha = particle.isAmbient() ? Mth.floor(38.25F) : 255;
            return ColorParticleOption.create(JNEParticleTypes.IMMUNITY_EFFECT.get(), FastColor.ARGB32.color(alpha, getColor()));
        };
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        Holder<MobEffect> immuneTo = getEffectImmuneTo();
        Holder<MobEffect> itself = getAsHolder(this);
        if (entity.hasEffect(immuneTo)) {
            int currentDuration = entity.getEffect(itself).getDuration();
            int otherAmplifier = entity.getEffect(immuneTo).getAmplifier() + 1;
            int duration = (currentDuration - (JNEConfigs.IMMUNITY_CONSUMPTION.get() * (JNEConfigs.AMPLIFIER_SCALES_IMMUNITY_CONSUMPTION.get() ? otherAmplifier : 1)));

            entity.level().playSound(null, entity.blockPosition(), duration <= 0 ? JNESoundEvents.ANTIDOTE_EXPIRED.get() : JNESoundEvents.ANTIDOTE_NEGATE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            entity.removeEffect(immuneTo);
            entity.removeEffect(itself);
            entity.addEffect(new MobEffectInstance(itself, duration, amplifier));
        }
        return true;
    }

    @Override
    public int getColor() {
        getEffectImmuneTo().value();
        return getEffectImmuneTo().value().getColor();
    }

    @Override
    public @NotNull ParticleOptions createParticleOptions(MobEffectInstance effect) {
        return this.immunityParticleFactory.apply(effect);
    }

    private Holder<MobEffect> getEffectImmuneTo() {
        return getAsHolder(LookupRegistryHelper.getMobEffect(immunityOf));
    }

    private Holder<MobEffect> getAsHolder(MobEffect effect) {
        Optional<ResourceKey<MobEffect>> key = BuiltInRegistries.MOB_EFFECT.getResourceKey(effect);
        return key.map(BuiltInRegistries.MOB_EFFECT::getHolderOrThrow).orElse(null);
    }
}
