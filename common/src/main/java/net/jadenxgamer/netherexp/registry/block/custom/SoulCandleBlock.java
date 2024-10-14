package net.jadenxgamer.netherexp.registry.block.custom;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class SoulCandleBlock
extends AbstractSoulCandleBlock
implements SimpleWaterloggedBlock {
    public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 1, 3);
    public static final BooleanProperty LIT = AbstractSoulCandleBlock.LIT;

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.getValue(LIT) ? 3 * state.getValue(CANDLES) : 0;

    private static final Int2ObjectMap<List<Vec3>> CANDLES_TO_PARTICLE_OFFSETS;

    static {
        CANDLES_TO_PARTICLE_OFFSETS = Util.make(() -> {
            Int2ObjectMap<List<Vec3>> int2ObjectMap = new Int2ObjectOpenHashMap<>();
            int2ObjectMap.defaultReturnValue(ImmutableList.of());
            int2ObjectMap.put(1, ImmutableList.of(new Vec3(0.5, 0.75, 0.5)));
            int2ObjectMap.put(2, ImmutableList.of(new Vec3(0.3125, 0.625, 0.3125), new Vec3(0.6875, 0.75, 0.6875)));
            int2ObjectMap.put(3, ImmutableList.of(new Vec3(0.75, 0.5, 0.3125), new Vec3(0.5, 0.75, 0.75), new Vec3(0.25, 0.625, 0.375)));
            return Int2ObjectMaps.unmodifiable(int2ObjectMap);
        });
    }

    private static final VoxelShape ONE_CANDLE_SHAPE = Block.box(5, 0, 5, 11, 10, 11);
    private static final VoxelShape TWO_CANDLES_SHAPE = Block.box(2, 0, 2, 14, 10, 14);
    private static final VoxelShape THREE_CANDLES_SHAPE = Block.box(1.5, 0, 2.5, 14.5, 10, 14.5);

    private static final VoxelShape ONE_CANDLE_COLLISION = Block.box(5, 0, 5, 11, 8, 11);
    private static final VoxelShape TWO_CANDLES_COLLISION = Block.box(2, 0, 2, 14, 8, 14);
    private static final VoxelShape THREE_CANDLES_COLLISION = Block.box(1.5, 0, 2.5, 14.5, 8, 14.5);
    public SoulCandleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CANDLES, 1).setValue(LIT, false).setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean lit = state.getValue(LIT);
        boolean bl = false;
        if (player.getAbilities().mayBuild && itemStack.isEmpty() && lit) {
            SoulCandleBlock.extinguish(player, state, level, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (itemStack.is(Items.FLINT_AND_STEEL) && !lit && canBeLit(state)) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
            level.setBlock(pos, state.cycle(LIT), Block.UPDATE_CLIENTS);
            if (!player.isCreative()) {
                itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
            bl = true;
        } else if (itemStack.is(Items.FIRE_CHARGE) && !lit && this.canBeLit(state)){
            level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
            level.setBlock(pos, state.cycle(LIT), Block.UPDATE_CLIENTS);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            bl = true;
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (!context.isSecondaryUseActive() && context.getItemInHand().getItem() == this.asItem() && state.getValue(CANDLES) < 3) {
            return true;
        }
        return super.canBeReplaced(state, context);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.is(this)) {
            return state.cycle(CANDLES);
        }
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(WATERLOGGED, bl).setValue(FACING, context.getHorizontalDirection().getOpposite());
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

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }


    @SuppressWarnings("deprecation")
    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(CANDLES)) {
            default: {
                return ONE_CANDLE_SHAPE;
            }
            case 2: {
                return TWO_CANDLES_SHAPE;
            }
            case 3:
        }
        return THREE_CANDLES_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(CANDLES)) {
            default: {
                return ONE_CANDLE_COLLISION;
            }
            case 2: {
                return TWO_CANDLES_COLLISION;
            }
            case 3:
        }
        return THREE_CANDLES_COLLISION;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, FACING, WATERLOGGED);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.getValue(WATERLOGGED) || fluidState.getType() != Fluids.WATER) {
            return false;
        }
        BlockState newState = state.setValue(WATERLOGGED, true);
        if (newState.getValue(LIT)) {
            SoulCandleBlock.extinguish(null, state, level, pos);
        } else {
            level.setBlock(pos, state, Block.UPDATE_ALL);
        }
        level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return CANDLES_TO_PARTICLE_OFFSETS.get(state.getValue(CANDLES));
    }

    @Override
    protected boolean canBeLit(BlockState state) {
        return !state.getValue(WATERLOGGED) && super.canBeLit(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.below(), Direction.UP);
    }
}
