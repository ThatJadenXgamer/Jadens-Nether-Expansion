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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ImmunityEffect extends IncurableEffect {

    protected static final int DEFAULT_COLOR = 0x808080;
    protected final ResourceLocation immunityOf;
    protected Holder<MobEffect> cachedImmunityHolder;
    protected Holder<MobEffect> cachedSelfHolder;

    public ImmunityEffect(MobEffectCategory category, ResourceLocation immunityOf) {
        super(category, 0);
        this.immunityOf = immunityOf;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return getImmunityHolder() != null;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        Holder<MobEffect> immuneTo = getImmunityHolder();
        if (immuneTo == null) return true;
        Holder<MobEffect> self = getSelfHolder();

        if (self != null && entity.hasEffect(immuneTo)) {
            MobEffectInstance selfEffect = entity.getEffect(self);
            if (selfEffect == null) return true;

            int otherAmplifier = entity.getEffect(immuneTo).getAmplifier() + 1;
            int newDuration = selfEffect.getDuration() - (JNEConfigs.IMMUNITY_CONSUMPTION.get() *
                    (JNEConfigs.AMPLIFIER_SCALES_IMMUNITY_CONSUMPTION.get() ? otherAmplifier : 1));

            entity.level().playSound(null, entity.blockPosition(),
                    newDuration <= 0 ? JNESoundEvents.ANTIDOTE_EXPIRED.get() : JNESoundEvents.ANTIDOTE_NEGATE.get(),
                    SoundSource.PLAYERS, 1.0f, 1.0f);

            entity.removeEffect(immuneTo);
            entity.removeEffect(self);
            entity.addEffect(new MobEffectInstance(self, newDuration, amplifier));
        }
        return true;
    }

    @Override
    public int getColor() {
        Holder<MobEffect> immuneTo = getImmunityHolder();
        return immuneTo != null ? immuneTo.value().getColor() : DEFAULT_COLOR;
    }

    @Override
    public @NotNull ParticleOptions createParticleOptions(MobEffectInstance effect) {
        int alpha = effect.isAmbient() ? Mth.floor(38.25F) : 255;
        return ColorParticleOption.create(JNEParticleTypes.IMMUNITY_EFFECT.get(),
                FastColor.ARGB32.color(alpha, getColor()));
    }

    @Nullable
    protected Holder<MobEffect> getImmunityHolder() {
        if (cachedImmunityHolder == null) {
            MobEffect effect = LookupRegistryHelper.getMobEffect(immunityOf);
            if (effect != null) cachedImmunityHolder = getAsHolder(effect);
        }
        return cachedImmunityHolder;
    }

    @Nullable
    protected Holder<MobEffect> getSelfHolder() {
        if (cachedSelfHolder == null) cachedSelfHolder = getAsHolder(this);
        return cachedSelfHolder;
    }

    @Nullable
    protected Holder<MobEffect> getAsHolder(MobEffect effect) {
        if (effect == null) return null;
        Optional<ResourceKey<MobEffect>> key = BuiltInRegistries.MOB_EFFECT.getResourceKey(effect);
        return key.map(BuiltInRegistries.MOB_EFFECT::getHolderOrThrow).orElse(null);
    }
}