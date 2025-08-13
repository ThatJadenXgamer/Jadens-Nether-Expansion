package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class JNEPoofParticle extends ExplodeParticle {
    protected JNEPoofParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
    }

    public static class SoulProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public SoulProvider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            JNEPoofParticle particle = new JNEPoofParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
            particle.setColor(0.0f, 0.8f, 0.8f);
            return particle;
        }
    }
}
