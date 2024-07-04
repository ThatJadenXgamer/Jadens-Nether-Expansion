package net.jadenxgamer.netherexp.mixin.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public class SlimeMixin {

    @Inject(
            method = "remove",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"),
            cancellable = true
    )
    private void netherexp$remove(Entity.RemovalReason removalReason, CallbackInfo cir) {
        if (removalReason == Entity.RemovalReason.DISCARDED) {
            cir.cancel();
        }
    }
}
