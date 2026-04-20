package net.jadenxgamer.netherexp.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import javax.annotation.Nullable;

public class JNEPortalParticle extends LodestoneWorldParticle {
    private double targetX, targetY, targetZ;
    private final double startX, startY, startZ;

    public JNEPortalParticle(ClientLevel world, WorldParticleOptions options, ParticleEngine.MutableSpriteSet spriteSet,
                             double x, double y, double z, double xd, double yd, double zd) {
        super(world, options, spriteSet, x, y, z, xd, yd, zd);
        this.startX = this.x;
        this.startY = this.y;
        this.startZ = this.z;
    }

    public void setTargetPos(double x, double y, double z) {
        this.targetX = x;
        this.targetY = y;
        this.targetZ = z;
    }

    @Override
    public void tick() {
        float progress = (float) this.age / (this.lifetime - 1);
        if (progress > 1.0f) progress = 1.0f;

        double newX = startX + (targetX - startX) * progress;
        double newY = startY + (targetY - startY) * progress;
        double newZ = startZ + (targetZ - startZ) * progress;
        this.setPos(newX, newY, newZ);

        super.tick();
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    public static class Factory implements ParticleProvider<WorldParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(WorldParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            return new JNEPortalParticle(world, data, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }
}