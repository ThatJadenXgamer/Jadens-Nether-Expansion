package net.jadenxgamer.netherexp.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BurningSkullBlock extends JNEHorizontalDirectionalBlock {

    public final Supplier<SimpleParticleType> particle;

    public BurningSkullBlock(Supplier<SimpleParticleType> particle, Properties properties) {
        super(properties);
        this.particle = particle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        for (int i = 0; i < 1; i++) {
            Direction direction = state.getValue(FACING);
            double x = pos.getX() + 0.5 + direction.getStepX() * 0.52;
            double z = pos.getZ() + 0.5 + direction.getStepZ() * 0.52;
            double spread = random.nextDouble() * 0.6 - 0.3;

            if (direction.getStepX() == 0) x += spread;
            else z += spread;

            double vx = direction.getStepX() * (0.001 + random.nextDouble() * 0.01);
            double vz = direction.getStepZ() * (0.001 + random.nextDouble() * 0.01);
            double vy = 0.001 + random.nextDouble() * 0.01;
            level.addParticle(particle.get(), x, pos.getY() + 0.2 + random.nextDouble() * 0.3, z, vx, vy, vz);
        }
    }
}