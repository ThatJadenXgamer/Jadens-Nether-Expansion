package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.effect.custom.ImmunityEffect;
import net.jadenxgamer.netherexp.registry.entity.custom.Banshee;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Shadow
    public abstract boolean hasEffect(MobEffect effect);

    @Shadow
    @Final
    private static EntityDataAccessor<Integer> DATA_EFFECT_COLOR_ID;

    @Shadow
    public abstract Map<MobEffect, MobEffectInstance> getActiveEffectsMap();

    @Shadow public abstract boolean isDeadOrDying();

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(
            method = "getBlockSpeedFactor",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$soulSpeedVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        // deals with unbounded speed mechanics by overriding the speed factor for certain blocks with conditions
        BlockState state = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (state.is(JNETags.Blocks.UNBOUNDED_SPEED_BLOCKS) && this.hasEffect(JNEMobEffects.UNBOUNDED_SPEED.get()) || entity.getType().is(JNETags.EntityTypes.IGNORES_SOUL_SAND_SLOWNESS) && !EnchantmentHelper.hasSoulSpeed(entity)) {
            cir.setReturnValue(1.0f);
        }
        else if (state.is(Blocks.SOUL_SAND) || state.is(JNEBlocks.ECTO_SOUL_SAND.get()) && JNEConfigs.REDUCE_SOUL_SAND_SLOWNESS.get() && !EnchantmentHelper.hasSoulSpeed(entity)) {
            cir.setReturnValue(0.7f);
        }
    }

    @ModifyArg(
            method = "tryAddSoulSpeed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V")
    )
    private int netherexp$addSoulSpeedQOL(int amount) {
        // removes the stupid soul speed durability if enabled in configs
        return JNEConfigs.REMOVE_SOUL_SPEED_DURABILITY_PENALTY.get() ? 0 : amount;
    }

    @Inject(
            method = "tickEffects",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"),
            cancellable = true
    )
    private void netherexp$antidoteParticles(CallbackInfo cir) {
        // changes the normal effect particles with unique antidote ones if you have an immunity effect
        for (MobEffect effect : this.getActiveEffectsMap().keySet()) {
            if (effect instanceof ImmunityEffect) {
                int i = this.entityData.get(DATA_EFFECT_COLOR_ID);
                if (i > 0) {
                    boolean flag;
                    flag = !this.isInvisible();
                    if (flag && this.random.nextInt(5) == 0) {
                        double d0 = (double) (i >> 16 & 255) / 255.0;
                        double d1 = (double) (i >> 8 & 255) / 255.0;
                        double d2 = (double) (i & 255) / 255.0;
                        this.level().addParticle(JNEParticleTypes.IMMUNITY_EFFECT.get(), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d0, d1, d2);
                    }
                }
                cir.cancel();
            }
        }
    }

    @Inject(
            method = "getDamageAfterArmorAbsorb",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurtArmor(Lnet/minecraft/world/damagesource/DamageSource;F)V"),
            cancellable = true
    )
    private void netherexp$preventArmorDamage(DamageSource damageSource, float f, CallbackInfoReturnable<Float> cir) {
        // makes it so some damage types do not take away armor durability since shotguns are absolutely BUSTED otherwise
        if (damageSource.is(JNETags.DamageTypes.CANT_DAMAGE_ARMOR)) {
            f = CombatRules.getDamageAfterAbsorb(f, ((LivingEntity) (Object) this).getArmorValue(), (float) ((LivingEntity) (Object) this).getAttributeValue(Attributes.ARMOR_TOUGHNESS));
            cir.setReturnValue(f);
        }
    }

    @ModifyArg(
            method = "handleEntityEvent",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V")
    )
    private ParticleOptions netherexp$handleEntityEvent(ParticleOptions particle) {
        // for whatever reason the teleport particles are hardcoded (yippie!!!) and need to be mixed into change. thanks mojang
        if (((LivingEntity) (Object) this) instanceof Banshee) {
            return ParticleTypes.SOUL;
        }
        return particle;
    }
}
