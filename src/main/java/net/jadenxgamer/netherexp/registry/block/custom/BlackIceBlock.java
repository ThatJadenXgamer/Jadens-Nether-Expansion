package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.particle.JNEParticles;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BlackIceBlock extends Block {

    public BlackIceBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState bottomState = world.getBlockState(pos.down());
        float r = random.nextInt(NetherExp.getConfig().blocks.renewableConfigs.soul_slate_from_black_ice_odds);
        if (NetherExp.getConfig().blocks.renewableConfigs.soul_slate_from_black_ice && r == 0 && bottomState.isOf(Blocks.SOUL_SAND)) {
            world.setBlockState(pos.down(), JNEBlocks.SOUL_SLATE.getDefaultState(), NOTIFY_LISTENERS);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SLATE_SOLIDIFYING, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos = pos.offset(direction);
            if (NetherExp.getConfig().visualeffects.black_ice_particles && !world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos) && random.nextInt(80) == 0) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getOffsetX() : (double) random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getOffsetY() : (double) random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getOffsetZ() : (double) random.nextFloat();
                world.addParticle(JNEParticles.BLACK_AEROSOL, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }
}
