package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value= EnvType.CLIENT)
public class GraspMistParticle
extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    GraspMistParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.velocityMultiplier = 0.96f;
        this.spriteProvider = spriteProvider;
        this.scale = 0.4f;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            GraspMistParticle glimmerParticle = new GraspMistParticle(clientWorld, d, e, f, 0.0, 0.0, 0.0, this.spriteProvider);
            if (clientWorld.random.nextBoolean()) {
                glimmerParticle.setColor(1.0f, 1.0f, 1.0f);
            } else {
                glimmerParticle.setColor(1.0f, 1.0f, 1.0f);
            }
            glimmerParticle.setVelocity(g * 0.01, h * 0.01, i * 0.01);
            glimmerParticle.setMaxAge(clientWorld.random.nextInt(30) + 10);
            return glimmerParticle;
        }
    }
}