package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(
            method = "getHurtSound",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getCustomHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        if (source.isIn(JNETags.DamageTypes.IS_SUFFOCATION)) {
            cir.setReturnValue(JNESoundEvents.ENTITY_PLAYER_HURT_SUFFOCATION);
        }
    }
}
