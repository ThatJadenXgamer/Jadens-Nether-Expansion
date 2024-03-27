package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class RedstoneExplosionEmitterParticle extends NoRenderParticle {
    private int age_;

    RedstoneExplosionEmitterParticle(ClientLevel level, double d, double e, double f) {
        super(level, d, e, f, 0.0, 0.0, 0.0);
    }

    public void tick() {
        int maxAge_ = 8;
        for(int i = 0; i < 6; ++i) {
            double d = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double e = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double f = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            this.level.addParticle(JNEParticleTypes.REDSTONE_EXPLOSION.get(), d, e, f, (float)this.age_ / (float) maxAge_, 0.0, 0.0);
        }

        ++this.age_;
        if (this.age_ == maxAge_) {
            this.remove();
        }

    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        public Factory() {
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new RedstoneExplosionEmitterParticle(clientLevel, d, e, f);
        }
    }
}