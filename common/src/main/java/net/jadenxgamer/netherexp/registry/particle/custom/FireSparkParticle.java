package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class FireSparkParticle extends SmokeParticle {
    protected FireSparkParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteSet spriteSet) {
        super(level, x, y, z, velocityX, velocityY, velocityZ, scaleMultiplier, spriteSet);
        this.hasPhysics = false;
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;
        this.lifetime = 32;
        this.quadSize = 0.05f;
    }

    @Override
    protected int getLightColor(float f) {
        return 15728880;
    }

    @Environment(value= EnvType.CLIENT)
    public static class Factory
            implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new FireSparkParticle(clientLevel, d, e, f, g, h, i, 1.0f, this.spriteSet);
        }
    }
}