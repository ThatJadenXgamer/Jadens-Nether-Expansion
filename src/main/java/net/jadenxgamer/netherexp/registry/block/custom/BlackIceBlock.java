package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import static net.jadenxgamer.netherexp.config.JNEConfigs.ENABLE_BLACK_ICE_PARTICLES;

public class BlackIceBlock extends Block {

    public BlackIceBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean bl) {
        if (!level.isClientSide()) {
            if (level.getBlockState(fromPos).is(Blocks.SOUL_SAND)) {
                level.setBlock(fromPos, JNEBlocks.PALE_SOUL_SLATE.get().defaultBlockState(), 2);
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SLATE_SOLIDIFYING.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean bl) {
        super.onPlace(state, level, pos, oldState, bl);
        if (!level.isClientSide()) {
            for (Direction direction : Direction.values()) {
                BlockPos adjacentPos = pos.relative(direction);
                BlockState adjacentState = level.getBlockState(adjacentPos);

                if (adjacentState.is(Blocks.SOUL_SAND)) {
                    level.setBlock(adjacentPos, JNEBlocks.PALE_SOUL_SLATE.get().defaultBlockState(), 2);
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SLATE_SOLIDIFYING.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (ENABLE_BLACK_ICE_PARTICLES.get() && random.nextInt(10) == 0) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (!isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, random, JNEParticleTypes.BLACK_FLAKE.get());
            }
        }
    }
}