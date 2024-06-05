package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class FallingParticle
extends TextureSheetParticle {
    private final float rotationSpeed;
    private final SpriteSet spriteSet;

    FallingParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;
        this.quadSize *= 0.67499995f;
        int i = (int)(32.0 / (Math.random() * 0.8 + 0.2));
        this.lifetime = (int)Math.max((float)i * 0.9f, 1.0f);
        this.setSpriteFromAge(spriteSet);
        this.rotationSpeed = ((float)Math.random() - 0.5f) * 0.1f;
        this.roll = (float)Math.random() * ((float)Math.PI * 2);
        this.hasPhysics = true;
    }

    @Override
    protected int getLightColor(float f) {
        return 15728880;
    }

    @Override
    public float getQuadSize(float tickDelta) {
        return this.quadSize * Mth.clamp(((float)this.age + tickDelta) / (float)this.lifetime * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }
        this.setSpriteFromAge(this.spriteSet);
        this.oRoll = this.roll;
        this.roll += (float)Math.PI * this.rotationSpeed * 2.0f;
        if (this.onGround) {
            this.roll = 0.0f;
            this.oRoll = 0.0f;
        }
        this.move(this.xd, this.yd, this.zd);
        this.yd -= 0.003f;
        this.yd = Math.max(this.yd, -0.14f);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
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
            return new FallingParticle(clientLevel, d, e, f, this.spriteSet);
        }
    }
}