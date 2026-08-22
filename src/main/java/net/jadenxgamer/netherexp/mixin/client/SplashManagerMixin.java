package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.enums.ProfanityConfig;
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
    @Unique private static final ResourceLocation JNE_SPLASHES_LOCATION = NetherExp.netherexpPath("texts/jne_splashes.txt");
    @Unique private static final ResourceLocation JNE_SPLASHES_PROFANITY_LOCATION = NetherExp.netherexpPath("texts/profanity_splashes.txt");
    @Unique private static final ResourceLocation JNE_SPLASHES_CENSORED_LOCATION = NetherExp.netherexpPath("texts/censored_splashes.txt");

    @Inject(
            method = "apply(Ljava/util/List;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("RETURN")
    )
    private void netherexp$applyJNESplashes(List<String> object, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        if (JNEConfigs.ENABLE_JNE_SPLASH_TEXTS.get()) {
            ProfanityConfig profanity = JNEConfigs.PROFANITY.get();
            loadSplashesFile(resourceManager, JNE_SPLASHES_LOCATION, "JNE splashes");
            if (profanity == ProfanityConfig.UNFILTERED) loadSplashesFile(resourceManager, JNE_SPLASHES_PROFANITY_LOCATION, "JNE profanity splashes");
            else if (profanity == ProfanityConfig.CENSORED) loadSplashesFile(resourceManager, JNE_SPLASHES_CENSORED_LOCATION, "JNE censored splashes");
        }
    }

    @Unique
    private void loadSplashesFile(ResourceManager resourceManager, ResourceLocation splashLocation, String logName) {
        resourceManager.getResource(splashLocation).ifPresentOrElse(resource -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                reader.lines()
                        .filter(line -> !line.trim().isEmpty() && !line.trim().startsWith("..."))
                        .forEach(this.splashes::add);
            } catch (Exception e) {
                NetherExp.LOGGER.error("Failed to load {}", logName, e);
            }
        }, () -> NetherExp.LOGGER.warn("{} file not found: {}", logName, splashLocation));
    }
}