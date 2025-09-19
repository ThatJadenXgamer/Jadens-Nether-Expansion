package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.jadenxgamer.netherexp.config.JNEConfigs.SOUL_SAND_VALLEY_WIND_SPEED;
import static net.jadenxgamer.netherexp.config.JNEConfigs.WINDY_ASH_SCALE_MULTIPLIER;

public class WindBlownParticle extends TextureSheetParticle {

    protected WindBlownParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.setSprite(spriteSet.get(this.random.nextInt(12), 12));
        this.xd = SOUL_SAND_VALLEY_WIND_SPEED.get() + (random.nextDouble() * 0.2);
        this.yd = (Mth.randomBetween(level.random, 0.1f, 0.5f)) * 0.1;
        this.zd = SOUL_SAND_VALLEY_WIND_SPEED.get() + (random.nextDouble() * 0.2);

        this.lifetime = 20 + random.nextInt(10);
        this.quadSize *= (float) (0.75f * WINDY_ASH_SCALE_MULTIPLIER.get());
        this.roll = (float) (random.nextFloat() * (2 * Math.PI));

        this.hasPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();

        this.oRoll = this.roll;

        if (this.age % 20 == 0) {
            this.xd += (random.nextDouble() - 0.5) * 0.1;
            this.zd += (random.nextDouble() - 0.5) * 0.1;
            this.xd = Mth.clamp(this.xd, 0.1, 0.5);
            this.zd = Mth.clamp(this.zd, 0.1, 0.5);
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
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
            return new WindBlownParticle(level, x, y, z, spriteSet);
        }
    }
}