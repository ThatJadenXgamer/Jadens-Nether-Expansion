package net.jadenxgamer.netherexp.client.particle;

import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;

public class JNEFallAndLandParticle extends JNEDripParticle {
    private final ParticleOptions landingParticle;
    protected JNEFallAndLandParticle(ClientLevel level, double x, double y, double z, Fluid fluid, SpriteSet spriteSet, ParticleOptions landingParticle) {
        super(level, x, y, z, fluid);
        this.setSpriteFromAge(spriteSet);
        this.landingParticle = landingParticle;
        this.lifetime = (int)(64.0 / (Math.random() * 0.8 + 0.2));
    }

    protected void postMoveUpdate() {
        if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landingParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }
    }

    public static class EctoplasmProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public EctoplasmProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            JNEFallAndLandParticle particle = new JNEFallAndLandParticle(level, x, y, z, JNEFluids.ECTOPLASM_SOURCE.get(), this.spriteSet, JNEParticleTypes.ECTOSPLASH.get());
            particle.setColor(0.161f, 0.98f, 1f);
            return particle;
        }
    }
}