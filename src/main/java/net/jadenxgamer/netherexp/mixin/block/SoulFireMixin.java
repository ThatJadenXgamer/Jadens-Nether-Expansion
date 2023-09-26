package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.particle.ModParticles;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulFireBlock.class)
public abstract class SoulFireMixin
extends AbstractFireBlock {

    public SoulFireMixin(Settings settings, float damage) {
        super(settings, damage);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        world.addParticle(ModParticles.SOUL_EMBER, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.6, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.015, 0.0);
    }
}
