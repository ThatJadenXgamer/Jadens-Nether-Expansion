package net.jadenxgamer.netherexp.core.effect;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SoulSpeedEffect extends JNEMobEffect {

    private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("netherexp", "effect.soul_speed_speed");
    private static final ResourceLocation EFFICIENCY_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("netherexp", "effect.soul_speed_efficiency");

    public SoulSpeedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        boolean onSoulSpeedBlock = isAboveSoulSpeedBlock(entity);

        AttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance efficiencyAttribute = entity.getAttribute(Attributes.MOVEMENT_EFFICIENCY);

        if (speedAttribute != null && efficiencyAttribute != null) {
            double speedBoostAmount = 0.0405 + (amplifier * 0.0105);

            if (onSoulSpeedBlock && !entity.isFallFlying()) {
                AttributeModifier currentSpeedModifier = speedAttribute.getModifier(SPEED_MODIFIER_ID);
                if (currentSpeedModifier == null || currentSpeedModifier.amount() != speedBoostAmount) {
                    if (currentSpeedModifier != null) speedAttribute.removeModifier(SPEED_MODIFIER_ID);
                    speedAttribute.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_ID, speedBoostAmount, AttributeModifier.Operation.ADD_VALUE));
                }
                if (!efficiencyAttribute.hasModifier(EFFICIENCY_MODIFIER_ID))
                    efficiencyAttribute.addTransientModifier(new AttributeModifier(EFFICIENCY_MODIFIER_ID, 1.0, AttributeModifier.Operation.ADD_VALUE));

                Vec3 delta = entity.getDeltaMovement();
                double horizontalSpeedSq = delta.x * delta.x + delta.z * delta.z;
                if (entity.tickCount % 5 == 0 && horizontalSpeedSq > 0.00001 && entity.onGround()) {
                    RandomSource random = entity.getRandom();
                    entity.level().addParticle(ParticleTypes.SOUL,
                            entity.getX() + (random.nextDouble() - 0.5) * entity.getBbWidth(),
                            entity.getY() + 0.1,
                            entity.getZ() + (random.nextDouble() - 0.5) * entity.getBbWidth(),
                            delta.x * -0.2, 0.1, delta.z * -0.2);
                    if (random.nextFloat() < 0.35F) entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 0.6F, 0.6F + random.nextFloat() * 0.4F);
                }

            } else {
                if (speedAttribute.hasModifier(SPEED_MODIFIER_ID)) speedAttribute.removeModifier(SPEED_MODIFIER_ID);
                if (efficiencyAttribute.hasModifier(EFFICIENCY_MODIFIER_ID)) efficiencyAttribute.removeModifier(EFFICIENCY_MODIFIER_ID);
            }
        }

        if (!entity.level().isClientSide()) {
            MobEffectInstance instance = entity.getEffect(JNEMobEffects.SOUL_SPEED);
            if (instance != null && instance.getDuration() == 1 && amplifier > 0) {
                int duration = JNEConfigs.SOUL_SPEED_DURATION.get() * 20;
                entity.addEffect(new MobEffectInstance(JNEMobEffects.SOUL_SPEED, duration, amplifier - 1, instance.isAmbient(), instance.isVisible(), instance.showIcon()));
            }
        }

        return true;
    }

    @Override
    public void onEffectRemoved(LivingEntity entity, int amplifier) {
        AttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance efficiencyAttribute = entity.getAttribute(Attributes.MOVEMENT_EFFICIENCY);

        if (speedAttribute != null) speedAttribute.removeModifier(SPEED_MODIFIER_ID);
        if (efficiencyAttribute != null) efficiencyAttribute.removeModifier(EFFICIENCY_MODIFIER_ID);
    }

    private boolean isAboveSoulSpeedBlock(LivingEntity entity) {
        var onState = entity.level().getBlockState(entity.getOnPos()).is(BlockTags.SOUL_SPEED_BLOCKS);
        var belowState = entity.level().getBlockState(entity.getOnPos().below()).is(BlockTags.SOUL_SPEED_BLOCKS);
        var evenFurtherBelowState = entity.level().getBlockState(entity.getOnPos().below(2)).is(BlockTags.SOUL_SPEED_BLOCKS);

        return entity.onGround() ? onState : onState || belowState || evenFurtherBelowState;
    }
}