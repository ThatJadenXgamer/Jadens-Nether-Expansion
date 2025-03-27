package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.elysium_api.impl.misc_registry.ElysiumTags;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WarpedWartBlock extends BushBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final VoxelShape AGE_0_TOP = Block.box(5, 7, 5, 11, 16, 11);
    public static final VoxelShape AGE_1_TOP = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_2_TOP = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_3_TOP = Block.box(2, 0, 2, 14, 16, 14);
    public static final VoxelShape AGE_3_BOTTOM = Block.box(2, 5, 2, 14, 16, 14);

    public WarpedWartBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.UPPER));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AGE)) {
            default: {
                return AGE_0_TOP;
            }
            case 1: {
                return AGE_1_TOP;
            }
            case 2: {
                return AGE_2_TOP;
            }
            case 3:
        }
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return AGE_3_BOTTOM;
        }
        return AGE_3_TOP;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState above = level.getBlockState(pos.above());
            return above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER;
        }
        return this.canPlantDownBelow(level.getBlockState(pos.above()));
    }

    protected boolean canPlantDownBelow(BlockState ceiling) {
        return ceiling.is(Blocks.SOUL_SAND) || ceiling.is(ElysiumTags.Blocks.NETHER_WART_PLANTABLE_ON);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (half == DoubleBlockHalf.LOWER && direction == Direction.UP) {
            if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.UPPER) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        else if (half == DoubleBlockHalf.UPPER && direction == Direction.DOWN) {
            if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.LOWER) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        BlockPos below = pos.below();
        if (age < 2 && random.nextInt(10) == 0) {
            level.setBlock(pos, state.setValue(AGE, age + 1), 2);
        }
        else if (age == 2 && level.getBlockState(below).isAir()) {
            level.setBlock(pos, state.setValue(AGE, 3), 2);
            level.setBlock(below, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, 3), 2);
        }
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(JNEItems.WARPED_WART.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }
}
