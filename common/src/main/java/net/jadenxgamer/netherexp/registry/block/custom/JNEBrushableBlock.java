package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class JNEBrushableBlock extends BrushableBlock {
    public static final BooleanProperty PERSISTENT = BooleanProperty.create("persistent");

    public JNEBrushableBlock(Block block, Properties properties, SoundEvent soundEvent, SoundEvent soundEvent2) {
        super(block, properties, soundEvent, soundEvent2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (!state.getValue(PERSISTENT) && random.nextInt(1) == 0) {
            level.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), 2);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.BLOCK_SUSPICIOUS_SOUL_SAND_BREAK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PERSISTENT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JNEBrushableBlockEntity(pos, state);
    }
}
