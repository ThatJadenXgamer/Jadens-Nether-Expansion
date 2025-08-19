package net.jadenxgamer.netherexp.core.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class VineStemHeadBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<VineStemHeadBlock> CODEC = simpleCodec(VineStemHeadBlock::new);
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 16, 13);
    public static final Map<Direction, VoxelShape> FACING_SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Shapes.join(Block.box(3, 0, 3, 13, 16, 13), Block.box(3, 16, -11, 13, 32, 13), BooleanOp.OR),
            Direction.EAST, Shapes.join(Block.box(3, 0, 3, 13, 16, 13), Block.box(3, 16, 3, 27, 32, 13), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(3, 0, 3, 13, 16, 13), Block.box(3, 16, 3, 13, 32, 27), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(3, 0, 3, 13, 16, 13), Block.box(-11, 16, 3, 13, 32, 13), BooleanOp.OR)
    ));

    private final Supplier<Block> grownBlock;
    private final Supplier<Item> seed;

    public VineStemHeadBlock(Supplier<Block> grownBlock, Supplier<Item> seed, Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
        this.grownBlock = grownBlock;
        this.seed = seed;
        this.registerDefaultState(this.defaultBlockState().setValue(ATTACHED, false).setValue(FACING, Direction.NORTH));
    }

    public VineStemHeadBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
        this.grownBlock = JNEBlocks.SORROWSQUASH;
        this.seed = () -> Items.PUMPKIN_SEEDS;
        this.registerDefaultState(this.defaultBlockState().setValue(ATTACHED, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(ATTACHED) ? FACING_SHAPES.get(state.getValue(FACING)) : SHAPE;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!JNEConfigs.SHOULD_SORROWSQUASH_FALL.get() || !state.getValue(ATTACHED)) return;
        BlockPos attachedDirection = pos.relative(state.getValue(FACING));
        if (level.getBlockState(attachedDirection).is(JNEBlocks.SORROWSQUASH.get())) {
            float damage = (float) JNEConfigs.SORROWSQUISHED_DAMAGE_MULTIPLIER.getAsDouble();
            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, attachedDirection, level.getBlockState(attachedDirection));
            fallingBlock.setHurtsEntities(damage, JNEConfigs.SORROWSQUISHED_MAX_DAMAGE.get());
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(ATTACHED);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        if (random.nextDouble() < JNEConfigs.SORROWSQUASH_GROWTH_CHANCE.get()) {
            Direction growthDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            BlockPos growthPos = pos.relative(growthDirection);
            if (level.getBlockState(growthPos).isAir()) {
                level.setBlock(growthPos, this.grownBlock.get().defaultBlockState(), Block.UPDATE_ALL);
                level.setBlock(pos, state.setValue(ATTACHED, true).setValue(HorizontalDirectionalBlock.FACING, growthDirection), Block.UPDATE_ALL);
            }
        } else if (age < 25 && random.nextDouble() < 0.5 && this.canGrowInto(level.getBlockState(pos.relative(this.growthDirection)))) {
            level.setBlock(pos.relative(this.growthDirection), this.getGrowIntoState(state, level.random), Block.UPDATE_ALL);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(ATTACHED) && !neighborState.is(this.grownBlock.get()) && direction == state.getValue(FACING)) {
            return state.setValue(ATTACHED, false);
        } else if (direction == this.growthDirection.getOpposite() && !state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        } else if (direction == this.growthDirection && (neighborState.is(this) || neighborState.is(this.getBodyBlock()))) {
            return this.updateBodyAfterConvertedFromHead(state, this.getBodyBlock().defaultBlockState());
        }
        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos growthOppositePos = pos.relative(this.growthDirection.getOpposite());
        BlockState growthOppositeState = level.getBlockState(growthOppositePos);
        return growthOppositeState.is(this.getHeadBlock()) || growthOppositeState.is(this.getBodyBlock()) || growthOppositeState.is(JNETags.Blocks.SOUL_CROP_MUTATION_BLOCKS);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(this.seed.get());
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ATTACHED, FACING, AGE);
    }

    @Override
    protected Block getBodyBlock() {
        return JNEBlocks.SORROWSQUASH_STEM_PLANT.get();
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return NetherVines.getBlocksToGrowWhenBonemealed(random);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos growthPos = pos.relative(this.growthDirection);
        int ageLimiter = Math.min(state.getValue(AGE) + 1, 25);
        int toGrowWhenBonemealed = this.getBlocksToGrowWhenBonemealed(random);

        for(int k = 0; k < toGrowWhenBonemealed && this.canGrowInto(level.getBlockState(growthPos)); ++k) {
            level.setBlockAndUpdate(growthPos, state.setValue(AGE, ageLimiter).setValue(ATTACHED, false));
            growthPos = growthPos.relative(this.growthDirection);
            ageLimiter = Math.min(ageLimiter + 1, 25);
        }
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }
}
