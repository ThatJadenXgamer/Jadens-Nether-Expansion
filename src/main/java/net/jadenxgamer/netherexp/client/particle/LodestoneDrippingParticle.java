package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import javax.annotation.Nullable;

public class LodestoneDrippingParticle extends LodestoneWorldParticle {

    private static final int GROW_DURATION_TICKS = 30;
    private static final int SHRINK_DURATION_TICKS = 30;
    private static final float MAX_SIZE = 0.22f;

    private State currentState = State.GROWING;
    private int shrinkStartAge = -1;
    private final float defaultGravity;

    public LodestoneDrippingParticle(ClientLevel world, WorldParticleOptions options, ParticleEngine.MutableSpriteSet spriteSet,
                                     double x, double y, double z,
                                     double xd, double yd, double zd) {
        super(world, options, spriteSet, x, y, z, xd, yd, zd);

        this.defaultGravity = options.getGravity();
        this.gravity = 0f;
        this.quadSize = 0f;
        this.setLifetime(200);
        setSize(0f, 0f);
    }

    @Override
    public void tick() {
        super.tick();
        float size = quadSize;

        switch (currentState) {
            case GROWING -> {
                float progress = Math.min(1.0f, (float) age / GROW_DURATION_TICKS);
                size = MAX_SIZE * progress;

                if (age >= GROW_DURATION_TICKS) {
                    currentState = State.FALLING;
                    gravity = defaultGravity;
                    size = MAX_SIZE;
                }
            }
            case FALLING -> {
                size = MAX_SIZE;

                if (onGround) {
                    xd = yd = zd = 0;
                    gravity = 0f;
                    currentState = State.SHRINKING;
                    shrinkStartAge = age;
                }
            }
            case SHRINKING -> {
                int shrinkAge = age - shrinkStartAge;
                float progress = Math.min(1.0f, (float) shrinkAge / SHRINK_DURATION_TICKS);
                size = MAX_SIZE * (1.0f - progress);

                if (progress >= 1.0f) remove();
            }
        }

        quadSize = size;
        setSize(size, size);
    }

    private enum State {
        GROWING,
        FALLING,
        SHRINKING
    }

    @Override
    public float getQuadLength(float partialTicks) {
        return quadSize;
    }

    public static class Factory implements ParticleProvider<WorldParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(WorldParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            return new LodestoneDrippingParticle(level, options, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }
}