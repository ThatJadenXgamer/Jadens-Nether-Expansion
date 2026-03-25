package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.VFXHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;

public class ShotgunBarrelBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<JNEHorizontalDirectionalBlock> CODEC = simpleCodec(JNEHorizontalDirectionalBlock::new);
    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");

    public ShotgunBarrelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, false).setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);
        if (powered && !triggered) {
            level.scheduleTick(pos, this, 4);
            level.setBlock(pos, state.setValue(TRIGGERED, true), Block.UPDATE_INVISIBLE);
        } else if (!powered && triggered) {
            level.playSound(null, pos, JNESoundEvents.SHOTGUN_LOAD.get(), SoundSource.BLOCKS, 0.6f, 1.0f);
            level.setBlock(pos, state.setValue(TRIGGERED, false), Block.UPDATE_INVISIBLE);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SHOTGUN_USE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        int count = JNEConfigs.SHOTGUN_BARREL_BULLETS.get();
        VFXHelper.shotgunScreenShake(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 8.0f, Easing.LINEAR);
        for (int i = 0; i < count; i++) {
            ShotgunPellet pellet = new ShotgunPellet(pos.relative(direction).getX() + 0.5, pos.relative(direction).getY() + 0.5, pos.relative(direction).getZ() + 0.5, level);
            pellet.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 1.0F, 16);
            level.addFreshEntity(pellet);
        }
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }
}
