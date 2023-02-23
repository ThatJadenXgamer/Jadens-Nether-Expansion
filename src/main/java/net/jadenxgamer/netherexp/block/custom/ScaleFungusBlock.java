package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ScaleFungusBlock
extends Block
implements Fertilizable {

    public static final IntProperty ROTATION = Properties.ROTATION;
    public static final IntProperty AGE = Properties.AGE_2;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 4.0, 15.0);

    public ScaleFungusBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("all")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(ROTATION, MathHelper.floor((double)(ctx.getPlayerYaw() * 16.0f / 360.0f) + 0.5) & 0xF);
    }

    @SuppressWarnings("all")
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate(state.get(ROTATION), 16));
    }

    @SuppressWarnings("all")
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16));
    }

    @SuppressWarnings("all")
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, AGE);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 2;
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
        int age = state.get(AGE);
        if (age < 2) {
            state = state.with(AGE, age + 1);
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        }
    }
}
