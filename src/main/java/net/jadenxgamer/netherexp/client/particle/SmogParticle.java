package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmogParticle extends TextureSheetParticle {
    SmogParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z);
        this.scale(2.0F);
        this.setSize(0.25f, 0.25f);
        this.lifetime = this.random.nextInt(50) + 80;
        this.gravity = 3.0E-6f;
        this.xd = xd;
        this.yd = yd + (double)(this.random.nextFloat() / 500.0f);
        this.zd = zd;
        this.hasPhysics = true;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime || this.alpha <= 0.0f) {
            this.remove();
            return;
        }
        this.xd += this.random.nextFloat() / 5000.0f * (float)(this.random.nextBoolean() ? 1 : -1);
        this.zd += this.random.nextFloat() / 5000.0f * (float)(this.random.nextBoolean() ? 1 : -1);
        this.yd -= this.gravity;
        this.move(this.xd, this.yd, this.zd);
        if (this.age >= this.lifetime - 60 && this.alpha > 0.01f) {
            this.alpha -= 0.015f;
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            SmogParticle smogParticle = new SmogParticle(level, x, y, z, xd, yd, zd);
            smogParticle.setAlpha(0.9f);
            smogParticle.pickSprite(this.spriteSet);
            return smogParticle;
        }
    }
}