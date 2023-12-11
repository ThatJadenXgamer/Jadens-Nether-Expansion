package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.effect.ModStatusEffects;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "damage",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$damageHandler(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ModStatusEffects.SOUL_SHIELD)) {
            this.removeStatusEffect(ModStatusEffects.SOUL_SHIELD);
            cir.setReturnValue(false);
        }
    }
}
