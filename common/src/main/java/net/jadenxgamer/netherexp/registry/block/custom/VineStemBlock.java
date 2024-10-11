package net.jadenxgamer.netherexp.registry.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class VineStemBlock
extends GrowingPlantHeadBlock {

    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    private static final Map<Direction, VoxelShape> FACING_TO_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.SOUTH, Block.box(6.0, 0.0, 6.0, 10.0, 15.0, 16.0), Direction.WEST, Block.box(0.0, 0.0, 6.0, 10.0, 15.0, 10.0), Direction.NORTH, Block.box(6.0, 0.0, 0.0, 10.0, 15.0, 10.0), Direction.EAST, Block.box(6.0, 0.0, 6.0, 16.0, 15.0, 10.0)));

    private final StemGrownBlock stemGrownBlock;
    private final Supplier<Item> pickBlockItem;

    public VineStemBlock(StemGrownBlock stemGrownBlock, Supplier<Item> pickBlockItem, Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
        this.stemGrownBlock = stemGrownBlock;
        this.pickBlockItem = pickBlockItem;
        this.registerDefaultState(this.defaultBlockState().setValue(ATTACHED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        boolean a = blockState.getValue(ATTACHED);
        if (a) {
            return FACING_TO_SHAPE.get(blockState.getValue(FACING));
        }
        else return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(AGE);
        boolean a = state.getValue(ATTACHED);
        BlockPos blockPos;
        if (!a && i < 25 && random.nextDouble() < 0.5 && this.canGrowInto(level.getBlockState(blockPos = pos.offset(this.growthDirection.getNormal())))) {
            level.setBlock(blockPos, this.getGrowIntoState(state, level.random), UPDATE_ALL);
        }
        else if (!a && i == 25) {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            BlockPos blockPos2 = pos.offset(direction.getNormal());
            if (level.getBlockState(blockPos2).isAir()) {
                level.setBlock(blockPos2, this.stemGrownBlock.defaultBlockState(), UPDATE_ALL);
                level.setBlock(pos, state.setValue(ATTACHED, true).setValue(HorizontalDirectionalBlock.FACING, direction), UPDATE_ALL);
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        boolean a = blockState.getValue(ATTACHED);
        return !a;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        boolean a = state.getValue(ATTACHED);
        if (a && !neighborState.is(this.stemGrownBlock) && direction == state.getValue(FACING)) {
            return state.setValue(ATTACHED, false);
        }
        if (direction == this.growthDirection.getOpposite() && !state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
        if (direction == this.growthDirection && (neighborState.is(this) || neighborState.is(this.getBodyBlock()))) {
            return this.updateBodyAfterConvertedFromHead(state, this.getBodyBlock().defaultBlockState());
        }
        if (this.scheduleFluidTicks) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return state;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockPos growthOppositePos = blockPos.offset(this.growthDirection.getOpposite().getNormal());
        BlockState growthOppositeState = level.getBlockState(growthOppositePos);
        if (!this.canAttachTo(blockState)) {
            return false;
        } else {
            return growthOppositeState.is(this.getHeadBlock()) || growthOppositeState.is(this.getBodyBlock()) || growthOppositeState.is(JNETags.Blocks.SOUL_SAND_BLOCKS) || growthOppositeState.is(JNEBlocks.SORROWEED.get());
        }
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(this.pickBlockItem.get());
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ATTACHED, FACING, AGE);
    }

    @Override
    protected @NotNull Block getBodyBlock() {
        return JNEBlocks.SORROWSQUASH_STEM_PLANT.get();
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return NetherVines.isValidGrowthState(blockState);
    }
}