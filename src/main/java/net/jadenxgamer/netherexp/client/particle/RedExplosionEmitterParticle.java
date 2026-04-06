package net.jadenxgamer.netherexp.client.particle;

import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;

public class RedExplosionEmitterParticle extends NoRenderParticle {
    private int ageExp;

    RedExplosionEmitterParticle(ClientLevel level, double d, double e, double f) {
        super(level, d, e, f, 0.0, 0.0, 0.0);
    }

    public void tick() {
        int maxAgeExp = 8;
        for(int i = 0; i < 6; ++i) {
            double x = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double y = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double z = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            this.level.addParticle(JNEParticleTypes.RED_EXPLOSION.get(), x, y, z, (float)this.ageExp / (float) maxAgeExp, 0.0, 0.0);
        }

        ++this.ageExp;
        if (this.ageExp == maxAgeExp) this.remove();
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        public Factory() {
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RedExplosionEmitterParticle(level, x, y, z);
        }
    }
}