package net.jadenxgamer.netherexp.client.particle;

import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class GlacierEffectParticle extends TextureSheetParticle {
    public GlacierEffectParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        RandomSource random = level.random;
        Minecraft client = Minecraft.getInstance();
        blackFlake(level, random, x, y, z);
        if (client.getCameraEntity() != null) coldFog(client.getCameraEntity(), level, random, x, y, z);
        this.remove();
    }

    private static void blackFlake(ClientLevel level, RandomSource random, double x, double y, double z) {
        WorldParticleBuilder.create(JNEParticleTypes.BLACK_FLAKE_WORLD.get())
                .setLightLevel(10485920)
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.5f).setCoefficient(0.2f).build())
                .setScaleData(GenericParticleData.create(0.0f, Mth.randomBetween(random, 0.17f, 0.23f)).setCoefficient(3.0f).build())
                .setTransparencyData(GenericParticleData.create(1.0f).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                .setLifetime(Mth.randomBetweenInclusive(random, 30, 60))
                .enableNoClip()
                .addMotion(-0.45, Mth.randomBetween(random, -0.1f, -0.4f), 0.25)
                .setGravity(0.0f)
                .spawn(level, x, y, z);
    }

    private static void coldFog(Entity entity, ClientLevel level, RandomSource random, double x, double y, double z) {
        if (entity.distanceToSqr(x, y, z) < 7.0) return;
        if (random.nextInt(12) != 0) return;;
        WorldParticleBuilder.create(JNEParticleTypes.COLD_FOG.get())
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.2f).setCoefficient(0.2f).build())
                .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 3.47f, 6.83f)).build())
                .setTransparencyData(GenericParticleData.create(0.0f, Mth.randomBetween(random, 0.6f, 1.0f), 0.0f).build())
                .setColorData(ColorParticleData.create(new Color(0x1E222C)).build())
                .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE.withDepthFade())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                .setLifetime(Mth.randomBetweenInclusive(random, 27, 40))
                .enableNoClip()
                .addMotion(-0.6, Mth.randomBetween(random, -0.1f, -0.2f), 0.4)
                .setGravity(0.0f)
                .spawn(level, x, y, z);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.NO_RENDER;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {

        public Factory(SpriteSet ignoredSprite) {}

        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new GlacierEffectParticle(level, x, y, z);
        }
    }
}