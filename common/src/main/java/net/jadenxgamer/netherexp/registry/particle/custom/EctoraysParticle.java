package net.jadenxgamer.netherexp.registry.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class EctoraysParticle
extends TextureSheetParticle {
    EctoraysParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        this.quadSize = 5.4f;
        this.setSize(0.25f, 1.0f);
        this.lifetime = 130;
        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime || this.alpha <= 0.0f) {
            this.remove();
            return;
        }
        if (this.age<= this.lifetime / 2 && this.alpha < 1.0f) {
            this.alpha += 0.015f;
        }
        if (this.age >= this.lifetime / 2 && this.alpha > 0.01f) {
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
            EctoraysParticle ectoraysParticle = new EctoraysParticle(clientLevel, d, e, f);
            ectoraysParticle.pickSprite(this.spriteSet);
            ectoraysParticle.setAlpha(0.01f);
            return ectoraysParticle;
        }
    }
}