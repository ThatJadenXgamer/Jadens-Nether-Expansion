package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.client.assetdriven.managers.BurnPalettesManager;
import net.jadenxgamer.netherexp.client.particle.FlameToBurnDummyParticle;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlameParticle.SmallFlameProvider.class)
public class FlameParticleSmallProviderMixin {

    @Inject(
            method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void redirectToBurn(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfoReturnable<Particle> cir) {
        if (!JNEConfigs.DUNGEONS_FLAME_PARTICLES.get()) return;
        var key = BuiltInRegistries.PARTICLE_TYPE.getKey(type);
        int row = BurnPalettesManager.getRowForParticle(key);
        if (row >= 0) cir.setReturnValue(new FlameToBurnDummyParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, true, row));
    }
}