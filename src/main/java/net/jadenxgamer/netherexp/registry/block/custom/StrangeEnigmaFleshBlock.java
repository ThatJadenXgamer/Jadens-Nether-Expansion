package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.particle.JNEParticles;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class StrangeEnigmaFleshBlock extends JNEFacingBlock {
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
            world.addParticle(JNEParticles.ENIGMA_PARTICLE, d, f, e, 0.0, 0.0, 0.0);
        }
    }
}
