package net.jadenxgamer.netherexp.core.block;

import com.google.common.collect.ImmutableList;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
public class SoulCandleBlock extends AbstractJNECandleBlock {
    private static final Map<Integer, List<Vec3>> CANDLE_PARTICLE_OFFSETS = new HashMap<>(3);

    public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 1, 3);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape ONE_CANDLE_SHAPE = Block.box(5, 0, 5, 11, 10, 11);
    private static final VoxelShape TWO_CANDLES_SHAPE = Block.box(2, 0, 2, 14, 10, 14);
    private static final VoxelShape THREE_CANDLES_SHAPE = Block.box(1.5, 0, 2.5, 14.5, 10, 14.5);
    private static final VoxelShape ONE_CANDLE_COLLISION = Block.box(5, 0, 5, 11, 8, 11);
    private static final VoxelShape TWO_CANDLES_COLLISION = Block.box(2, 0, 2, 14, 8, 14);
    private static final VoxelShape THREE_CANDLES_COLLISION = Block.box(1.5, 0, 2.5, 14.5, 8, 14.5);

    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.getValue(LIT) ? 3 * state.getValue(CANDLES) : 0;

    public SoulCandleBlock(Properties properties) {
        super(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME, JNESoundEvents.SOUL_CANDLE_AMBIENT, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CANDLES, 1).setValue(LIT, false).setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
        CANDLE_PARTICLE_OFFSETS.put(1, ImmutableList.of(new Vec3(0.5, 0.75, 0.5)));
        CANDLE_PARTICLE_OFFSETS.put(2, ImmutableList.of(new Vec3(0.3125, 0.625, 0.3125), new Vec3(0.6875, 0.75, 0.6875)));
        CANDLE_PARTICLE_OFFSETS.put(3, ImmutableList.of(new Vec3(0.75, 0.5, 0.3125), new Vec3(0.5, 0.75, 0.75), new Vec3(0.25, 0.625, 0.375)));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getAbilities().mayBuild && stack.isEmpty() && state.getValue(LIT)) {
            extinguishCandle(level, state, pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        if (canBeLit(state)) {
            boolean changeState = false;
            if (stack.is(Items.FLINT_AND_STEEL)) {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                changeState = true;
            } else if (stack.is(Items.FIRE_CHARGE)) {
                level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                changeState = true;
            }

            if (changeState) {
                lightCandle(level, state, pos);
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockState state = level.getBlockState(context.getClickedPos());
        if (state.is(this)) return state.cycle(CANDLES);

        FluidState fluid = level.getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluid.is(Fluids.WATER));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(CANDLES)) {
            default -> ONE_CANDLE_SHAPE;
            case 2 -> TWO_CANDLES_SHAPE;
            case 3 -> THREE_CANDLES_SHAPE;
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(CANDLES)) {
            default -> ONE_CANDLE_COLLISION;
            case 2 -> TWO_CANDLES_COLLISION;
            case 3 -> THREE_CANDLES_COLLISION;
        };
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (!context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(CANDLES) < 3) {
            return true;
        }
        return super.canBeReplaced(state, context);
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return CANDLE_PARTICLE_OFFSETS.get(state.getValue(CANDLES));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, FACING, WATERLOGGED);
    }
}
