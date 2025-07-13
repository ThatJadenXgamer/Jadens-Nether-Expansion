package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
    private final Supplier<Double> conversionOdds;

    public BuriedConverterBlock(IntProvider xpRange, Supplier<Block> convertsTo, Supplier<Double> conversionOdds, Properties properties) {
        super(xpRange, properties);
        this.convertsTo = convertsTo;
        this.conversionOdds = conversionOdds;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() > conversionOdds.get()) return;

        int buried = 0;
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            if (level.getBlockState(pos.relative(direction)).isSolid()) {
                ++buried;
            } else break;
        }
        if (buried >= 6) {
            level.setBlock(pos, convertsTo.get().defaultBlockState(), Block.UPDATE_ALL);
            level.playSound(null, pos, JNESoundEvents.FOSSILIZATION.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }
}
