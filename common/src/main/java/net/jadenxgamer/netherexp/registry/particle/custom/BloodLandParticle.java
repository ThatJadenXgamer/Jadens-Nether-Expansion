package net.jadenxgamer.netherexp.registry.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class BloodLandParticle extends JNEDripParticle {
    protected BloodLandParticle(ClientLevel level, double x, double y, double z, Fluid fluid, SpriteSet spriteSet) {
        super(level, x, y, z, fluid);
        setSpriteFromAge(spriteSet);
        this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.setColor(0.678f, 0.114f, 0.125f);
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BloodLandParticle(level, x, y, z, Fluids.EMPTY, this.spriteSet);
        }
    }
}
