package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedHazeParticle extends TextureSheetParticle {
    private final float rotationSpeed;
    RedHazeParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        this.quadSize = 3.4f;
        this.setSize(0.25f, 1.0f);
        this.lifetime = 360;
        this.hasPhysics = true;
        this.rotationSpeed = 0.005f;
        this.roll = (float)Math.random() * ((float)Math.PI * 2);
    }

    @Override
    protected int getLightColor(float f) {
        return 15728880;
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
        if (this.age <= this.lifetime / 4 && this.alpha < 0.5f) {
            this.alpha += 0.015f;
        }
        if (this.age >= this.lifetime / 4 && this.alpha > 0.01f) {
            this.alpha -= 0.005f;
        }
        this.oRoll = this.roll;
        this.roll += (float)Math.PI * this.rotationSpeed;
        this.move(this.xd, this.yd, this.zd);
        this.yd -= 0.003f;
        this.yd = Math.max(this.yd, -0.03f);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            RedHazeParticle redHazeParticle = new RedHazeParticle(clientLevel, d, e, f);
            redHazeParticle.pickSprite(this.spriteSet);
            redHazeParticle.setAlpha(0.001f);
            return redHazeParticle;
        }
    }
}