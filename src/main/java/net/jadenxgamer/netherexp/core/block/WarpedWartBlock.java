package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.elysium_api.api.tags.ElysiumTags;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WarpedWartBlock extends BushBlock {
    public static final MapCodec<WarpedWartBlock> CODEC = simpleCodec(WarpedWartBlock::new);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final VoxelShape AGE_0 = Block.box(5, 7, 5, 11, 16, 11);
    public static final VoxelShape AGE_1 = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_2 = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape AGE_3_TOP = Block.box(2, 0, 2, 14, 16, 14);
    public static final VoxelShape AGE_3_BOTTOM = Block.box(2, 5, 2, 14, 16, 14);

    public WarpedWartBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.UPPER));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AGE)) {
            default -> AGE_0;
            case 1 -> AGE_1;
            case 2 -> AGE_2;
            case 3 -> state.getValue(HALF) == DoubleBlockHalf.LOWER ? AGE_3_BOTTOM : AGE_3_TOP;
        };
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER;
        }
        return canPlantBelow(above);
    }

    protected boolean canPlantBelow(BlockState ceiling) {
        return ceiling.is(Blocks.SOUL_SAND) || ceiling.is(ElysiumTags.Blocks.NETHER_WART_PLANTABLE_ON);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);

        if (half == DoubleBlockHalf.UPPER && direction == Direction.DOWN) {
            if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.LOWER) {
                return Blocks.AIR.defaultBlockState();
            }
        } else if (half == DoubleBlockHalf.LOWER && direction == Direction.UP) {
            if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.UPPER) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) != 0) return;
        int age = state.getValue(AGE);

        level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_ALL);
        if (age == 2) level.setBlock(pos.below(), state.setValue(AGE, age + 1).setValue(HALF, DoubleBlockHalf.LOWER), Block.UPDATE_ALL);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
