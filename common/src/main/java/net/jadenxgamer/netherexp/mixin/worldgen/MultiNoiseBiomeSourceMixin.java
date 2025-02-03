package net.jadenxgamer.netherexp.mixin.worldgen;

import net.jadenxgamer.netherexp.registry.worldgen.JNESubBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiNoiseBiomeSource.class, priority = 69000)
public abstract class MultiNoiseBiomeSourceMixin {
//    @Shadow protected abstract Climate.ParameterList<Holder<Biome>> parameters();
//
//    @Inject(
//            method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;",
//            at = @At(value = "HEAD"),
//            cancellable = true
//    )
//    private void netherexp$getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
//        Holder<Biome> biome = parameters().findValue(sampler.sample(x, y, z));
//        cir.setReturnValue(JNESubBiomes.replaceBiomes(biome));
//    }
}
