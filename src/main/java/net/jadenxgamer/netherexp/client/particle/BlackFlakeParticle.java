package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BlackFlakeParticle extends TextureSheetParticle {
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    public BlackFlakeParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.setSprite(spriteSet.get(this.random.nextInt(12), 12));
        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -50.0 : 50.0);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 300;
        this.gravity = 7.5E-4F;
        this.quadSize = 0.1F;
        this.setSize(0.2F, 0.2F);
        this.friction = 1.0F;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float $$0 = (float)(300 - this.lifetime);
            float $$1 = Math.min($$0 / 300.0F, 1.0F);
            double xWind = Math.cos(Math.toRadians((this.particleRandom * 60.0F))) * 6.0 * Math.pow($$1, 1.25);
            double zWind = Math.sin(Math.toRadians((this.particleRandom * 60.0F))) * 6.0 * Math.pow($$1, 1.25);
            this.xd += xWind * 0.0324999999441206455;
            this.zd += zWind * 0.0324999999441206455;
            this.yd = -0.2F;
            this.rotSpeed += this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd *= this.friction;
                this.yd *= this.friction;
                this.zd *= this.friction;
            }
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new BlackFlakeParticle(level, x, y, z, spriteSet);
        }
    }
}