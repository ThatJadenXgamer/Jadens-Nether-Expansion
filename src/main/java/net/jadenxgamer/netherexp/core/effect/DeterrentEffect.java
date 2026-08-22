package net.jadenxgamer.netherexp.core.effect;

import net.jadenxgamer.elysium_api.api.util.ElysiumTimeTracker;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.event.JNEEvents;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.ColorHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;

import java.awt.*;
import java.util.List;

public class DeterrentEffect extends ImmunityEffect {

    public DeterrentEffect(MobEffectCategory category, ResourceLocation immunityOf) {
        super(category, immunityOf);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        Holder<MobEffect> immuneTo = getImmunityHolder();
        Holder<MobEffect> self = getSelfHolder();
        if (immuneTo == null || self == null) return true;

        MobEffectInstance selfEffect = entity.getEffect(self);
        if (selfEffect == null) return true;

        double radius = 8.0 * (amplifier + 1);
        int currentDuration = selfEffect.getDuration();

        List<LivingEntity> affectedEntities = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), e -> e.hasEffect(immuneTo));
        int removalCount = affectedEntities.size();
        for (LivingEntity target : affectedEntities) target.removeEffect(immuneTo);

        if (removalCount > 0) {
            int newDuration = currentDuration - (removalCount * 200);
            entity.removeEffect(self);
            if (newDuration <= 0) {
                entity.level().playSound(null, entity.blockPosition(),
                        JNESoundEvents.ANTIDOTE_EXPIRED.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            } else {
                entity.addEffect(new MobEffectInstance(self, newDuration, selfEffect.getAmplifier(), selfEffect.isAmbient(), selfEffect.isVisible(), selfEffect.showIcon()));
                entity.level().playSound(null, entity.blockPosition(),
                        JNESoundEvents.ANTIDOTE_NEGATE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
        return true;
    }

    public static void handlePulseEffect(LivingEntity entity) {
        if (entity.tickCount % 30 == 0) {
            List<MobEffectInstance> deterrentEffects = entity.getActiveEffects().stream().filter(e -> e.getEffect().value() instanceof DeterrentEffect).toList();
            if (!deterrentEffects.isEmpty()) {
                MobEffectInstance chosen = deterrentEffects.get(entity.getRandom().nextInt(deterrentEffects.size()));
                Color color = new Color(chosen.getEffect().value().getColor());
                if (entity.level().isClientSide()) {
                    DeterrentEffect.Client.spawnPulseParticle(entity, color, chosen.getAmplifier());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static class Client {

        public static void spawnPulseParticle(LivingEntity entity, Color color, int amplifier) {
            Vec3 pos = entity.position();
            Vec3 direction = new Vec3(0.0, 1.0, 0.0);
            int radius = 8 * (amplifier + 1);
            WorldParticleBuilder.create(JNEParticleTypes.LARGE_BURST.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(entity.level().random, 0.0f, 1.0f).setCoefficient(0.25f).setEasing(Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(ColorHelper.adjustHSB(color).brightness(1.5f).build()).build())
                    .setScaleData(GenericParticleData.create(0.1f, radius).setEasing(Easing.SINE_OUT).build())
                    .setBehavior(DirectionalParticleBehavior.directional(direction))
                    .setTransparencyData(GenericParticleData.create(0.5f, 0.0f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setLifetime(80)
                    .addTickActor(actor -> actor.setPos(entity.getX(), entity.getY() + 0.4, entity.getZ()))
                    .disableNoClip()
                    .spawn(entity.level(), pos.x, pos.y + 0.4, pos.z);
        }
    }
}