package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class SporesBlock
extends RodBlock
implements Fertilizable {

    protected final VoxelShape northShape;
    protected final VoxelShape southShape;
    protected final VoxelShape eastShape;
    protected final VoxelShape westShape;
    protected final VoxelShape upShape;
    protected final VoxelShape downShape;

    public SporesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP));
        this.upShape = Block.createCuboidShape(3, 0, 3, 13, 6, 13);
        this.downShape = Block.createCuboidShape(3, 10, 3, 13, 16, 13);
        this.northShape = Block.createCuboidShape(3, 3, 10, 13, 13, 16);
        this.southShape = Block.createCuboidShape(3, 3, 0, 13, 13, 6);
        this.eastShape = Block.createCuboidShape(0, 3, 3, 6, 13, 13);
        this.westShape = Block.createCuboidShape(10, 3, 3, 16, 13, 13);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));
        if (blockState.isOf(this) && blockState.get(FACING) == direction) {
            return this.getDefaultState().with(FACING, direction.getOpposite());
        }
        return this.getDefaultState().with(FACING, direction);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch (direction) {
            case NORTH -> {
                return this.northShape;
            }
            case SOUTH -> {
                return this.southShape;
            }
            case EAST -> {
                return this.eastShape;
            }
            case WEST -> {
                return this.westShape;
            }
            case DOWN -> {
                return this.downShape;
            }
        }
        return this.upShape;
    }

    @SuppressWarnings("all")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    @SuppressWarnings("all")
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.NORMAL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        SporesBlock.dropStack(world, pos, new ItemStack(this));
    }
}
