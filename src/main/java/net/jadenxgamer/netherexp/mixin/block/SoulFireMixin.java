package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
        if (NetherExp.getConfig().visualeffects.improved_soul_fire_particles) {
            if (random.nextInt(24) == 0) {
                world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
            world.addParticle(ModParticles.SOUL_EMBER, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.6, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.015, 0.0);
        }
        else super.randomDisplayTick(state, world, pos, random);
    }
}
