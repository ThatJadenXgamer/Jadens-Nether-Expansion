package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class JNETorchBlock extends TorchBlock {

    private final Supplier<SimpleParticleType> particle;

    public JNETorchBlock(Properties pProperties, Supplier<SimpleParticleType> particle) {
        super(pProperties, ParticleTypes.FLAME);
        this.particle = particle;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        double x = (double)pPos.getX() + 0.5;
        double y = (double)pPos.getY() + 0.7;
        double z = (double)pPos.getZ() + 0.5;
        pLevel.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
        pLevel.addParticle(this.particle.get(), x, y, z, 0.0, 0.0, 0.0);
    }
}
