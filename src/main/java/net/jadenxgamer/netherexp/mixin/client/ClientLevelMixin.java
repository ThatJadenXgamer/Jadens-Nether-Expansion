package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.client.JNEFogRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(
            method = "getSkyColor",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getCustomSkyColor(Vec3 pos, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        JNEFogRenderer.skyColor(pos, partialTick, cir);
    }
}
