package net.jadenxgamer.netherexp.client.particle;

import net.jadenxgamer.netherexp.client.assetdriven.managers.BurnPalettesManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.RandomSource;

public class FlameToBurnDummyParticle extends TextureSheetParticle {
    public FlameToBurnDummyParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, boolean small, int row) {
        super(level, x, y, z);
        RandomSource random = level.random;
        BurnPalettesManager.flameToBurnParticle(level, random, x, y, z, xSpeed, ySpeed, zSpeed, small, row);
        this.remove();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.NO_RENDER;
    }
}