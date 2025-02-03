package net.jadenxgamer.netherexp.mixin.worldgen;

import net.jadenxgamer.netherexp.registry.worldgen.JNESubBiomes;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftServer.class, priority = 994)
public class MinecraftServerMixin {

//    @Inject(
//            method = "<init>",
//            at = @At(value = "RETURN"),
//            require = 1
//    )
//    private void netherexp$init(CallbackInfo ci) {
//        JNESubBiomes.setRegistryAccess(((MinecraftServer) (Object) this).registryAccess());
//    }
}
