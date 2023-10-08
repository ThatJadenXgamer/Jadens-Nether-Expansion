package net.jadenxgamer.netherexp.block.custom;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class SoulCandleBlock
extends AbstractSoulCandleBlock
implements Waterloggable {
    public static final IntProperty CANDLES = IntProperty.of("candles", 1, 3);
    public static final BooleanProperty LIT = AbstractSoulCandleBlock.LIT;

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.get(LIT) ? 3 * state.get(CANDLES) : 0;

    @SuppressWarnings("all")
    private static final Int2ObjectMap<List<Vec3d>> CANDLES_TO_PARTICLE_OFFSETS = Util.make(() -> {
        Int2ObjectOpenHashMap int2ObjectMap = new Int2ObjectOpenHashMap();
        int2ObjectMap.defaultReturnValue(ImmutableList.of());
        int2ObjectMap.put(1, ImmutableList.of(new Vec3d(0.5, 0.75, 0.5)));
        int2ObjectMap.put(2, ImmutableList.of(new Vec3d(0.3125, 0.625, 0.3125), new Vec3d(0.6875, 0.75, 0.6875)));
        //TODO: 3rd fire placement in Soul Candle is bugged
        int2ObjectMap.put(3, ImmutableList.of(new Vec3d(0.25, 0.625, 0.25), new Vec3d(0.75, 0.625, 0.3125), new Vec3d(0.56, 0.5, 0.44)));
        return Int2ObjectMaps.unmodifiable(int2ObjectMap);
    });

    private static final VoxelShape ONE_CANDLE_SHAPE = Block.createCuboidShape(5, 0, 5, 11, 10, 11);
    private static final VoxelShape TWO_CANDLES_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 10, 14);
    private static final VoxelShape THREE_CANDLES_SHAPE = Block.createCuboidShape(1.5, 0, 2.5, 14.5, 10, 14.5);

    private static final VoxelShape ONE_CANDLE_COLLISION = Block.createCuboidShape(5, 0, 5, 11, 8, 11);
    private static final VoxelShape TWO_CANDLES_COLLISION = Block.createCuboidShape(2, 0, 2, 14, 8, 14);
    private static final VoxelShape THREE_CANDLES_COLLISION = Block.createCuboidShape(1.5, 0, 2.5, 14.5, 8, 14.5);
    public SoulCandleBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CANDLES, 1).with(LIT, false).with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean lit = state.get(LIT);
        boolean bl = false;
        if (player.getAbilities().allowModifyWorld && itemStack.isEmpty() && lit) {
            SoulCandleBlock.extinguish(player, state, world, pos);
            return ActionResult.success(world.isClient);
        }
        if (itemStack.isOf(Items.FLINT_AND_STEEL) && !lit && canBeLit(state)) {
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            world.setBlockState(pos,state.cycle(LIT), Block.NOTIFY_LISTENERS);
            if (!player.isCreative()) {
                itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            }
            bl = true;
        } else if (itemStack.isOf(Items.FIRE_CHARGE) && !lit && canBeLit(state)) {
            world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            world.setBlockState(pos,state.cycle(LIT), Block.NOTIFY_LISTENERS);
            if (!player.isCreative()) {
                itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            }
            bl = true;
        }
        if (bl) {
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && state.get(CANDLES) < 3) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.cycle(CANDLES);
        }
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, bl).with(FACING, ctx.getPlayerFacing().getOpposite());
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

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(CANDLES)) {
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
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(CANDLES)) {
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, FACING, WATERLOGGED);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(WATERLOGGED) || fluidState.getFluid() != Fluids.WATER) {
            return false;
        }
        BlockState blockState = state.with(WATERLOGGED, true);
        if (state.get(LIT)) {
            SoulCandleBlock.extinguish(null, blockState, world, pos);
        } else {
            world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
        }
        world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
        return true;
    }

    public static boolean canBeLit(BlockState state2) {
        return state2.isIn(BlockTags.CANDLES, state -> state.contains(LIT) && state.contains(WATERLOGGED)) && !state2.get(LIT) && !state2.get(WATERLOGGED);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return CANDLES_TO_PARTICLE_OFFSETS.get(state.get(CANDLES));
    }

    @Override
    protected boolean isNotLit(BlockState state) {
        return !state.get(WATERLOGGED) && super.isNotLit(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }
}
