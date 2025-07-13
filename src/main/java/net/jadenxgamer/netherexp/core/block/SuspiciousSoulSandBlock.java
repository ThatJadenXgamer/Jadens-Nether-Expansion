package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.entity.SuspiciousSoulSandBlockEntity;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SuspiciousSoulSandBlock extends BrushableBlock {

    public static final BooleanProperty PERSISTENT = BooleanProperty.create("persistent");

    public SuspiciousSoulSandBlock(Properties properties) {
        super(Blocks.SOUL_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (state.getValue(PERSISTENT) && random.nextDouble() > JNEConfigs.SUSPICIOUS_SOUL_SAND_DECAY_ODDS.get()) return;

        SuspiciousSoulSandBlockEntity blockEntity = (SuspiciousSoulSandBlockEntity) level.getBlockEntity(pos);
        if (blockEntity.getDecayCounter() >= JNEConfigs.SUSPICIOUS_SOUL_SAND_MAX_DECAY.get()) {
            level.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), Block.UPDATE_ALL);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SUSPICIOUS_SOUL_SAND_BREAK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        } else {
            blockEntity.setDecayCounter(blockEntity.getDecayCounter() + 1);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return JNEConfigs.SUSPICIOUS_SOUL_SAND_DECAY_ODDS.get() > 0.0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PERSISTENT);
    }
}
