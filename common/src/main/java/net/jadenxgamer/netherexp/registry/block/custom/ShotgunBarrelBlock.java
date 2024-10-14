package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.entity.custom.SoulBullet;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
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
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean bl) {
        boolean power = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above());
        boolean triggered = state.getValue(TRIGGERED);
        if (power && !triggered) {
            level.scheduleTick(pos, this, 4);
            level.setBlock(pos, state.setValue(TRIGGERED, true), 4);
        } else if (!power && triggered) {
            level.setBlock(pos, state.setValue(TRIGGERED, false), 4);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        int count = JNEConfigs.SHOTGUN_BARREL_BULLETS.get();
        for (int i = 0; i < count; i++) {
            SoulBullet soulBullet = new SoulBullet(pos.offset(direction.getNormal()).getX() + 0.5, pos.offset(direction.getNormal()).getY() + 0.5, pos.offset(direction.getNormal()).getZ() + 0.5, level);
            soulBullet.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 1.0F, 16);
            level.addFreshEntity(soulBullet);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }
}
