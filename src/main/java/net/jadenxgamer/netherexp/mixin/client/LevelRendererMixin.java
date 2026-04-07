package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.util.VFXHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.awt.*;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

@Mixin(value = LevelRenderer.class, priority = 650)
public abstract class LevelRendererMixin {

    @Shadow @Nullable private ClientLevel level;

    @Unique
    private long netherexp$lastMistTime = 0;

    @Inject(
            method = "renderSnowAndRain",
            at = @At(value = "HEAD")
    )
    private void netherexp$renderNetherFog(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        if (!JNEConfigImpl.CONFIG.isLoaded()) return;
        if (!NETHER_MIST_PARTICLES.get() || level.dimension() != Level.NETHER) return;

        Minecraft client = Minecraft.getInstance();
        long currentTime = client.level.getGameTime();
        if (currentTime - netherexp$lastMistTime >= NETHER_MIST_SPAWN_RATE.get()) {
            netherexp$lastMistTime = currentTime;

            var player = client.player;
            if (player == null) return;
            var random = level.random;
            double angle = random.nextDouble() * Math.PI * 2;
            double distance = Mth.nextDouble(random, NETHER_MIST_MIN_DISTANCE.get(), NETHER_MIST_MAX_DISTANCE.get());

            double x = player.getX() + Math.cos(angle) * distance;
            double y = player.getY() + Mth.nextDouble(random, -8.0, 8.0);
            double z = player.getZ() + Math.sin(angle) * distance;
            Color color = new Color(level.getBiome(new BlockPos((int) x, (int) y, (int) z)).value().getFogColor());

            VFXHelper.fogParticle(level, level.random, x, y, z, color);
        }
    }
}
