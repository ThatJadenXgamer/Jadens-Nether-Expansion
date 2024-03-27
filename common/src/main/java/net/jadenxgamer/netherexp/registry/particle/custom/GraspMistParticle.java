package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class GraspMistParticle
extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    GraspMistParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteSet) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.friction = 0.96f;
        this.spriteSet = spriteSet;
        this.quadSize = 0.4f;
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    protected int getLightColor(float f) {
        return 15728880;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            GraspMistParticle glimmerParticle = new GraspMistParticle(clientLevel, d, e, f, 0.0, 0.0, 0.0, this.spriteSet);
            if (clientLevel.random.nextBoolean()) {
                glimmerParticle.setColor(1.0f, 1.0f, 1.0f);
            } else {
                glimmerParticle.setColor(1.0f, 1.0f, 1.0f);
            }
            glimmerParticle.setParticleSpeed(g * 0.01, h * 0.01, i * 0.01);
            glimmerParticle.setLifetime(clientLevel.random.nextInt(30) + 10);
            return glimmerParticle;
        }
    }
}