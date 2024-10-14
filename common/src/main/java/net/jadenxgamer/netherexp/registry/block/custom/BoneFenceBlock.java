package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BoneFenceBlock
extends CrossCollisionBlock {
    private final VoxelShape[] cullingShapes;

    public BoneFenceBlock(Properties properties) {
        super(2.0f, 2.0f, 16.0f, 16.0f, 24.0f, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
        this.cullingShapes = this.makeShapes(2.0f, 1.0f, 16.0f, 6.0f, 15.0f);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return this.cullingShapes[this.getAABBIndex(state)];
    }

    @SuppressWarnings("all")
    public boolean canConnect(BlockState state, boolean neighborIsFullSquare, Direction direction) {
        Block block = state.getBlock();
        return this.canConnectToFence(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.getShape(state, level, pos, context);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
        return false;
    }

    private boolean canConnectToFence(BlockState state) {
        return state.is(JNETags.Blocks.BONE_FENCES);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos northPos = pos.north();
        BlockPos eastPos = pos.east();
        BlockPos southPos = pos.south();
        BlockPos westPos = pos.west();
        BlockState northState = level.getBlockState(northPos);
        BlockState eastState = level.getBlockState(eastPos);
        BlockState southState = level.getBlockState(southPos);
        BlockState westState = level.getBlockState(westPos);
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(NORTH, this.canConnect(northState, northState.isFaceSturdy(level, northPos, Direction.SOUTH), Direction.SOUTH)).setValue(EAST, this.canConnect(eastState, eastState.isFaceSturdy(level, eastPos, Direction.WEST), Direction.WEST)).setValue(SOUTH, this.canConnect(southState, southState.isFaceSturdy(level, southPos, Direction.NORTH), Direction.NORTH)).setValue(WEST, this.canConnect(westState, westState.isFaceSturdy(level, westPos, Direction.EAST), Direction.EAST)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            return state.setValue(PROPERTY_BY_DIRECTION.get(direction), this.canConnect(neighborState, neighborState.isFaceSturdy(level, neighborPos, direction.getOpposite()), direction.getOpposite()));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}