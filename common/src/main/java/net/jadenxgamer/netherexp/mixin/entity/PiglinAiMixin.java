package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @Inject(
            method = "isZombified",
            at = @At(value = "TAIL"),
            cancellable = true
    )
    private static void netherexp$isPossessedMob(EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
        // for some reason mojang removed the fucking piglin_afraid_of tag
        if (entityType == JNEEntityType.VESSEL.get() || entityType == JNEEntityType.BANSHEE.get()) {
            cir.setReturnValue(true);
        }
    }
}
