package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class JNEWallTorchBlock extends WallTorchBlock {
    private final Supplier<SimpleParticleType> particle;

    public JNEWallTorchBlock(Properties pProperties, Supplier<SimpleParticleType> particle) {
        super(pProperties, ParticleTypes.FLAME);
        this.particle = particle;
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction $$4 = (Direction)pState.getValue(FACING);
        double $$5 = (double)pPos.getX() + 0.5;
        double $$6 = (double)pPos.getY() + 0.7;
        double $$7 = (double)pPos.getZ() + 0.5;
        double $$8 = 0.22;
        double $$9 = 0.27;
        Direction $$10 = $$4.getOpposite();
        pLevel.addParticle(ParticleTypes.SMOKE, $$5 + 0.27 * (double)$$10.getStepX(), $$6 + 0.22, $$7 + 0.27 * (double)$$10.getStepZ(), 0.0, 0.0, 0.0);
        pLevel.addParticle(this.particle.get(), $$5 + 0.27 * (double)$$10.getStepX(), $$6 + 0.22, $$7 + 0.27 * (double)$$10.getStepZ(), 0.0, 0.0, 0.0);
    }
}
