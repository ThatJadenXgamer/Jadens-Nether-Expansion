package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BuriedConverterBlock extends DropExperienceBlock {

    private final Supplier<Block> convertsTo;

    public BuriedConverterBlock(IntProvider xpRange, Supplier<Block> convertsTo, Properties properties) {
        super(xpRange, properties);
        this.convertsTo = convertsTo;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int conversionOdds = random.nextInt(50);
        if (conversionOdds != 0) return;

        int buried = 0;
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            if (level.getBlockState(pos.relative(direction)).isSolid()) {
                ++buried;
            } else break;
        }
        if (buried >= 6) {
            level.setBlock(pos, convertsTo.get().defaultBlockState(), Block.UPDATE_ALL);
            level.playSound(null, pos, JNESoundEvents.Interactions.FOSSILIZATION.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }
}
