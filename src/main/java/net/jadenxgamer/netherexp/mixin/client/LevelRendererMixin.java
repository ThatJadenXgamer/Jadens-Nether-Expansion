package net.jadenxgamer.netherexp.mixin.client;

import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.SparkParticleBehavior;

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

            fogParticle(level, level.random, x, y, z, color);
        }
    }

    @Unique
    private void fogParticle(Level level, RandomSource random, double x, double y, double z, Color color) {
        double motion = NETHER_MIST_MOTION_MULTIPLIER.get();
        float scale = (float) NETHER_MIST_SCALE.getAsDouble();
        float opacity = (float) NETHER_MIST_SCALE.getAsDouble();
        float startSize = Mth.randomBetween(random, (scale - 2.0f), scale);
        float endSize = Mth.randomBetween(random, (scale + 1.0f), (scale + 3.0f));
        float transparency = Mth.randomBetween(random, (opacity - 0.2f), opacity);
        Vec3 direction = new Vec3(0.0, 1.0, 0.0);
        WorldParticleBuilder.create(JNEParticleTypes.NETHER_FOG.get())
                .setFullBrightLighting()
                .enableNoClip()
                .setBehavior(SparkParticleBehavior.sparkBehavior().setForcedDirection(direction))
                .setForceSpawn(true)
                .setScaleData(GenericParticleData.create(startSize, endSize).build())
                .setTransparencyData(GenericParticleData.create(0.002f, transparency, 0f).build())
                .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE.withDepthFade())
                .setLifetime(random.nextInt(120, 180))
                .setMotion(random.nextDouble() * motion, random.nextDouble() * motion, random.nextDouble() * motion)
                .setColorData(ColorParticleData.create(color.brighter().brighter()).build())
                .spawn(level, x, y, z);
    }
}
