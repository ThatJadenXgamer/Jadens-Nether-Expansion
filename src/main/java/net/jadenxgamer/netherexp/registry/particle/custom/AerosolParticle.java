package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value= EnvType.CLIENT)
public class AerosolParticle
extends SpriteBillboardParticle {
    AerosolParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.25f, 1.0f);
        this.maxAge = 130;
        this.collidesWithWorld = false;
        this.scale = 1.0f;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.maxAge || this.alpha <= 0.0f) {
            this.markDead();
            return;
        }
        if (this.age<= this.maxAge / 2 && this.alpha < 1.0f) {
            this.alpha += 0.015f;
        }
        if (this.age >= this.maxAge / 2 && this.alpha > 0.01f) {
            this.alpha -= 0.015f;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory
            implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            AerosolParticle ectoraysParticle = new AerosolParticle(clientWorld, d, e, f);
            ectoraysParticle.setSprite(this.spriteProvider);
            ectoraysParticle.setAlpha(0.01f);
            return ectoraysParticle;
        }
    }
}