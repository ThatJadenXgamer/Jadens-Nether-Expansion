package net.jadenxgamer.netherexp.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ParticleHelper {

    public static void surroundBlockParticle(Level level, BlockPos pos, SimpleParticleType particle) {
        RandomSource random = level.random;
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            BlockPos relativePos = pos.relative(direction);
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                Direction.Axis axis = direction.getAxis();
                double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
                double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
                double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
                level.addParticle(particle, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, 0.0, 0.0, 0.0);
            }
        }
    }
}
