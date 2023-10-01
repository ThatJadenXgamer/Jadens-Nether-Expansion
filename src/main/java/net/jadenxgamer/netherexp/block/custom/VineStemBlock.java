package net.jadenxgamer.netherexp.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Map;
import java.util.function.Supplier;

public class VineStemBlock
extends AbstractPlantStemBlock {

    public static final BooleanProperty ATTACHED = Properties.ATTACHED;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    private static final Map<Direction, VoxelShape> FACING_TO_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.SOUTH, Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 15.0, 16.0), Direction.WEST, Block.createCuboidShape(0.0, 0.0, 6.0, 10.0, 15.0, 10.0), Direction.NORTH, Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 15.0, 10.0), Direction.EAST, Block.createCuboidShape(6.0, 0.0, 6.0, 16.0, 15.0, 10.0)));

    private final GourdBlock gourdBlock;
    private final Supplier<Item> pickBlockItem;

    public VineStemBlock(GourdBlock gourdBlock, Supplier<Item> pickBlockItem, AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.1);
        this.gourdBlock = gourdBlock;
        this.pickBlockItem = pickBlockItem;
        this.setDefaultState(this.stateManager.getDefaultState().with(ATTACHED, false).with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean a = state.get(ATTACHED);
        if (a) {
            return FACING_TO_SHAPE.get(state.get(FACING));
        }
        else return SHAPE;
    }

    @Override
    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.SORROWSQUASH_STEM_PLANT;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        boolean a = state.get(ATTACHED);
        BlockPos blockPos;
        if (!a && i < 25 && random.nextDouble() < 0.1 && this.chooseStemState(world.getBlockState(blockPos = pos.offset(this.growthDirection)))) {
            world.setBlockState(blockPos, this.age(state, world.random));
        }
        else if (!a && i == 25) {
            Direction direction = Direction.Type.HORIZONTAL.random(random);
            BlockPos blockPos2 = pos.offset(direction);
            if (world.getBlockState(blockPos2).isAir()) {
                world.setBlockState(blockPos2, this.gourdBlock.getDefaultState());
                world.setBlockState(pos, state.with(ATTACHED, true).with(HorizontalFacingBlock.FACING, direction));
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        boolean a = state.get(ATTACHED);
        return !a;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean a = state.get(ATTACHED);
        if (a && !neighborState.isOf(this.gourdBlock) && direction == state.get(FACING)) {
            return state.with(ATTACHED, false);
        }
        if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        if (direction == this.growthDirection && (neighborState.isOf(this) || neighborState.isOf(this.getPlant()))) {
            return this.copyState(state, this.getPlant().getDefaultState());
        }
        if (this.tickWater) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.pickBlockItem.get());
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ATTACHED, FACING, AGE);
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}