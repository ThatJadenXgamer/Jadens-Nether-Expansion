package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulPathBlock
extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    public SoulPathBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    public boolean useShapeForLightOcclusion(BlockState arg) {
        return true;
    }

    public BlockState getStateForPlacement(BlockPlaceContext arg) {
        return !this.defaultBlockState().canSurvive(arg.getLevel(), arg.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), Blocks.SOUL_SOIL.defaultBlockState(), arg.getLevel(), arg.getClickedPos()) : super.getStateForPlacement(arg);
    }

    @SuppressWarnings("deprecation")
    public @NotNull BlockState updateShape(BlockState arg, Direction arg2, BlockState arg3, LevelAccessor arg4, BlockPos arg5, BlockPos arg6) {
        if (arg2 == Direction.UP && !arg.canSurvive(arg4, arg5)) {
            arg4.scheduleTick(arg5, this, 1);
        }

        return super.updateShape(arg, arg2, arg3, arg4, arg5, arg6);
    }

    @SuppressWarnings("deprecation")
    public void tick(BlockState arg, ServerLevel arg2, BlockPos arg3, RandomSource arg4) {
        turnToBase(null, arg, arg2, arg3);
    }

    public static void turnToBase(@Nullable Entity entity, BlockState blockState, Level level, BlockPos blockPos) {
        BlockState blockState2 = pushEntitiesUp(blockState, Blocks.SOUL_SOIL.defaultBlockState(), level, blockPos);
        level.setBlockAndUpdate(blockPos, blockState2);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, blockState2));
    }

    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState arg, LevelReader arg2, BlockPos arg3) {
        BlockState blockState = arg2.getBlockState(arg3.above());
        return !blockState.isSolid() || blockState.getBlock() instanceof FenceGateBlock;
    }

    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState arg, BlockGetter arg2, BlockPos arg3, PathComputationType arg4) {
        return false;
    }
}