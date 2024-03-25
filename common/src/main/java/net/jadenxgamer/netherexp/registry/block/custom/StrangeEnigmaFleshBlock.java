package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class StrangeEnigmaFleshBlock extends JNEDirectionalBlock {
    public StrangeEnigmaFleshBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        double d = (double)pos.getX() + random.nextDouble();
        double f = (double)pos.getY() + 1.1;
        double e = (double)pos.getZ() + random.nextDouble();
        for (int i = 0; i < 3; ++i) {
            if (!random.nextBoolean()) continue;
            //TODO Particles
            //world.addParticle(JNEParticles.ENIGMA_PARTICLE, d, f, e, 0.0, 0.0, 0.0);
        }
    }
}
