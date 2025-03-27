package net.jadenxgamer.netherexp.registry.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JNEPoofParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected JNEPoofParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ);
        this.gravity = -0.1F;
        this.friction = 0.9F;
        this.sprites = pSprites;
        this.xd = pXSpeed + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.yd = pYSpeed + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.zd = pZSpeed + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        float $$8 = this.random.nextFloat() * 0.3F + 0.7F;
        this.rCol = $$8;
        this.gCol = $$8;
        this.bCol = $$8;
        this.quadSize = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 6.0F + 1.0F);
        this.lifetime = (int)(16.0 / ((double)this.random.nextFloat() * 0.8 + 0.2)) + 2;
        this.setSpriteFromAge(pSprites);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @OnlyIn(Dist.CLIENT)
    public static class SoulProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public SoulProvider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            JNEPoofParticle particle = new JNEPoofParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            particle.setColor(0.0f, 0.8f, 0.8f);
            return particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class AncientWaxProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public AncientWaxProvider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            JNEPoofParticle particle = new JNEPoofParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            particle.setColor(0.8f, 0.0f, 0.0f);
            return particle;
        }
    }
}
