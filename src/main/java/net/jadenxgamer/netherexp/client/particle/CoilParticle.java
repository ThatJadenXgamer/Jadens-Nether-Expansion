package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import javax.annotation.Nullable;

public class CoilParticle extends LodestoneWorldParticle {
    private final double centerX, centerY, centerZ;
    private final double coilRadius;
    private final double coilHeight;
    private final double coilSpeed;
    private final double timeOffset;

    public CoilParticle(ClientLevel world, WorldParticleOptions options, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, options, spriteSet, x, y, z, xd, yd, zd);
        this.centerX = x;
        this.centerY = y;
        this.centerZ = z;
        this.xd = (random.nextDouble() - 0.5) * 0.02;
        this.yd = 0.08;
        this.zd = (random.nextDouble() - 0.5) * 0.02;
        this.coilRadius = 2.2;
        this.coilHeight = 3.1;
        this.coilSpeed = 0.4;
        this.timeOffset = random.nextDouble() * 20.0;
    }

    @Override
    public void tick() {
        super.tick();
        double worldTime = this.level.getGameTime() + timeOffset;
        double coilProgress = (worldTime * 0.05) * coilSpeed;
        double lifeProgress = Math.min(1.0, (double)this.age / this.lifetime);

        double angle = coilProgress * Math.PI * 2;
        double radius = coilRadius * (1 - lifeProgress * 0.3);

        double baseHeight = centerY + lifeProgress * coilHeight;
        double coilOffsetY = Math.sin(coilProgress * Math.PI * 2) * 0.2;

        double newX = centerX + Math.cos(angle) * radius;
        double newY = baseHeight + coilOffsetY;
        double newZ = centerZ + Math.sin(angle) * radius;

        double smoothFactor = 0.3;
        this.x = this.x + (newX - this.x) * smoothFactor;
        this.y = this.y + (newY - this.y) * smoothFactor;
        this.z = this.z + (newZ - this.z) * smoothFactor;

        if (this.age % 5 == 0) {
            double windStrength = 0.01;
            this.xd += Math.cos(angle) * windStrength;
            this.zd += Math.sin(angle) * windStrength;
        }
    }

    public static class Factory implements ParticleProvider<WorldParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(WorldParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            return new CoilParticle(level, options, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }
}