package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class SmallRisingParticle
extends TextureSheetParticle {
    SmallRisingParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, boolean signal) {
        super(level, x, y, z);
        this.quadSize *= 0.75f;
        this.setSize(0.25f, 0.25f);
        this.lifetime = signal ? this.random.nextInt(50) + 280 : this.random.nextInt(50) + 80;
        this.gravity = 3.0E-6f;
        this.xd = velocityX;
        this.yd = velocityY + (double)(this.random.nextFloat() / 500.0f);
        this.zd = velocityZ;
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

    @Environment(value=EnvType.CLIENT)
    public static class Factory
            implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            SmallRisingParticle SmallRisingParticle = new SmallRisingParticle(clientLevel, d, e, f, g, h, i, false);
            SmallRisingParticle.setAlpha(0.9f);
            SmallRisingParticle.pickSprite(this.spriteSet);
            return SmallRisingParticle;
        }
    }
}