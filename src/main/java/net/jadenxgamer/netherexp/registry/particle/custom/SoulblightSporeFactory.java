package net.jadenxgamer.netherexp.registry.particle.custom;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SoulblightSporeFactory extends WaterSuspendParticle.CrimsonSporeFactory {

    public SoulblightSporeFactory(SpriteProvider spriteProvider) {
        super(spriteProvider);
    }

    @Override
    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        WaterSuspendParticle waterSuspendParticle = (WaterSuspendParticle) super.createParticle(defaultParticleType, clientWorld, d, e, f, g, h, i);
        waterSuspendParticle.setColor(178, 128, 70);
        return waterSuspendParticle;
    }
}