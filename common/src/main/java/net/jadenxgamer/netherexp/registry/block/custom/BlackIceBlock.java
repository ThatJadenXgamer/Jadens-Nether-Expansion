package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlackIceBlock extends Block {

    public BlackIceBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockState bottomState = world.getBlockState(pos.below());
        // int r = random.nextInt(NetherExp.getConfig().blocks.renewableConfigs.soul_slate_from_black_ice_odds);
        int r = random.nextInt(50);
        if (r == 0 && bottomState.is(Blocks.SOUL_SAND)) {
            world.setBlock(pos.below(), JNEBlocks.SOUL_SLATE.get().defaultBlockState(), Block.UPDATE_ALL);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SLATE_SOLIDIFYING.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            BlockPos blockPos = pos.relative(direction);
            if (!world.getBlockState(blockPos).isCollisionShapeFullBlock(world, blockPos) && random.nextInt(80) == 0) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
                //TODO Add Particles
                //world.addParticle(JNEParticles.BLACK_AEROSOL, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }
}