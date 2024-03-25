package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(
            method = "getHurtSound",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getCustomHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if (damageSource.is(JNETags.DamageTypes.IS_SUFFOCATION)) {
            cir.setReturnValue(JNESoundEvents.ENTITY_PLAYER_HURT_SUFFOCATION.get());
        }
    }
}
