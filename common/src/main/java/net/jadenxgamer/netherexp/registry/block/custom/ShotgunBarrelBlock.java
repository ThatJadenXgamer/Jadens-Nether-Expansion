package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ShotgunBarrelBlock extends JNEHorizontalDirectionalBlock {
    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");

    public ShotgunBarrelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, false).setValue(FACING, Direction.NORTH));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean power = level.hasNeighborSignal(blockPos) || level.hasNeighborSignal(blockPos.above());
        boolean triggered = blockState.getValue(TRIGGERED);
        if (power && !triggered) {
            level.scheduleTick(blockPos, this, 4);
            level.setBlock(blockPos, blockState.setValue(TRIGGERED, true), 4);
        } else if (!power && triggered) {
            level.setBlock(blockPos, blockState.setValue(TRIGGERED, false), 4);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        Direction direction = blockState.getValue(FACING);
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
        int count = Mth.nextInt(randomSource, 6, 12);
        for (int i = 0; i < count; i++) {
            SoulBullet soulBullet = new SoulBullet(pos.offset(direction.getNormal()).getX(), pos.offset(direction.getNormal()).getY(), pos.offset(direction.getNormal()).getZ(), level);
            soulBullet.shoot(direction.getStepX() + 0.5, direction.getStepY() + 0.5, direction.getStepZ() + 0.5, 1.0F, 16);
            level.addFreshEntity(soulBullet);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }
}
