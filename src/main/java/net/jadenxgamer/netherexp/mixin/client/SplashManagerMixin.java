package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Mixin(SplashManager.class)
public class SplashManagerMixin {
    @Shadow @Final private List<String> splashes;
    @Unique private static final ResourceLocation JNE_SPLASHES_LOCATION = NetherExp.id("texts/splashes.txt");

    @Inject(
            method = "apply(Ljava/util/List;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("RETURN")
    )
    private void netherexp$applyJNESplashes(List<String> object, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        if (!JNEConfigs.ENABLE_JNE_SPLASH_TEXTS.get()) return;
        resourceManager.getResource(JNE_SPLASHES_LOCATION).ifPresentOrElse(resource -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                reader.lines()
                        .filter(line -> !line.trim().isEmpty() && !line.trim().startsWith("..."))
                        .forEach(this.splashes::add);
            } catch (Exception e) {
                NetherExp.LOGGER.error("Failed to load JNE splashes", e);
            }
        }, () -> NetherExp.LOGGER.warn("JNE splashes file not found: {}", JNE_SPLASHES_LOCATION));
    }
}