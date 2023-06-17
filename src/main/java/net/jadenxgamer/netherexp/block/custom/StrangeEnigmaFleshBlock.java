package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.particle.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class StrangeEnigmaFleshBlock extends ModFacingBlock{
    public StrangeEnigmaFleshBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d = (double)pos.getX() + random.nextDouble();
        double f = (double)pos.getY() + 1.1;
        double e = (double)pos.getZ() + random.nextDouble();
        for (int i = 0; i < 3; ++i) {
            if (!random.nextBoolean()) continue;
            world.addParticle(ModParticles.ENIGMA_PARTICLE, d, f, e, 0.0, 0.0, 0.0);
        }
    }
}
