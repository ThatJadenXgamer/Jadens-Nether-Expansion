package net.jadenxgamer.netherexp.client.particle;

import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class JNEDripHangParticle extends JNEDripParticle {
    private final ParticleOptions fallingParticle;

    JNEDripHangParticle(ClientLevel level, double x, double y, double z, Fluid type, SpriteSet spriteSet, ParticleOptions fallingParticle) {
        super(level, x, y, z, type);
        this.setSpriteFromAge(spriteSet);
        this.fallingParticle = fallingParticle;
        this.gravity *= 0.02F;
        this.lifetime = 40;
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
            this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
        }
    }

    protected void postMoveUpdate() {
        this.xd *= 0.02;
        this.yd *= 0.02;
        this.zd *= 0.02;
    }

    public static class EctoplasmProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public EctoplasmProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            JNEDripHangParticle particle = new JNEDripHangParticle(level, x, y, z, Fluids.EMPTY, this.spriteSet, JNEParticleTypes.FALLING_ECTOPLASM.get());
            particle.setColor(0.161f, 0.98f, 1f);
            return particle;
        }
    }
}