package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.FireSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class FireSparkParticle extends FireSmokeParticle {
    protected FireSparkParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider);
        this.collidesWithWorld = false;
        this.red = 1.0f;
        this.blue = 1.0f;
        this.green = 1.0f;
        this.maxAge = 32;
        this.scale = 0.05f;
    }

    @Environment(value= EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new FireSparkParticle(clientWorld, d, e, f, g, h, i, 1.0f, this.spriteProvider);
        }
    }
}