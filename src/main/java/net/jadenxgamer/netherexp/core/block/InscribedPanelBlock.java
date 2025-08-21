package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.block.enums.Liquidlogged;
import net.jadenxgamer.netherexp.core.block.interfaces.EctoplasmWaterloggedBlock;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class InscribedPanelBlock extends Block implements EctoplasmWaterloggedBlock {

    public static final EnumProperty<Liquidlogged.WaterEctoplasm> LIQUIDLOGGED = EnumProperty.create("liquidlogged", Liquidlogged.WaterEctoplasm.class);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty INSCRIPTION = IntegerProperty.create("inscription", 0, 28);
    public static final BooleanProperty SALTED = BooleanProperty.create("salted");

    public static final VoxelShape NORTH_SHAPE = Block.box(1, 2, 14, 15, 16, 16);
    public static final VoxelShape EAST_SHAPE = Block.box(0, 2, 1, 2, 16, 15);
    public static final VoxelShape SOUTH_SHAPE = Block.box(1, 2, 0, 15, 16, 2);
    public static final VoxelShape WEST_SHAPE = Block.box(14, 2, 1, 16, 16, 15);

    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.getValue(LIQUIDLOGGED) == Liquidlogged.WaterEctoplasm.ECTOPLASM ? 12 : 0;

    public InscribedPanelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(INSCRIPTION, 0).setValue(FACING, Direction.NORTH).setValue(LIQUIDLOGGED, Liquidlogged.WaterEctoplasm.AIR).setValue(SALTED, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(SALTED)) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        if (stack.is(Items.HONEYCOMB)) {
            level.setBlock(pos, state.setValue(SALTED, true), Block.UPDATE_ALL);
            if (!player.getAbilities().instabuild) stack.shrink(1);
            ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.WAX_ON);
            level.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0f, 1.0f);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        } else if (stack.isEmpty()) {
            cycleInscription(level, state, pos, player);
            ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
            level.playSound(null, pos, JNESoundEvents.SOUL_SLATE_BRICKS_HIT.get(), SoundSource.BLOCKS, 1.0f, 1.5f);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private static void cycleInscription(Level level, BlockState state, BlockPos pos, Player player) {
        int inscription = state.getValue(INSCRIPTION);
        int min = 0, max = 28;
        int next = player.isCrouching() ? (inscription == min ? max : inscription - 1) : (inscription == max ? min : inscription + 1);

        level.setBlock(pos, state.setValue(INSCRIPTION, next), Block.UPDATE_ALL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        Liquidlogged.WaterEctoplasm liquidlogged = Liquidlogged.WaterEctoplasm.AIR;
        if (fluidState.getType() == Fluids.WATER) {
            liquidlogged = Liquidlogged.WaterEctoplasm.WATER;
        } else if (fluidState.getType() == JNEFluids.ECTOPLASM_SOURCE.get()) {
            liquidlogged = Liquidlogged.WaterEctoplasm.ECTOPLASM;
        }
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIQUIDLOGGED, liquidlogged);
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
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(LIQUIDLOGGED) == Liquidlogged.WaterEctoplasm.WATER) {
            return Fluids.WATER.getSource(true);
        } else if (state.getValue(LIQUIDLOGGED) == Liquidlogged.WaterEctoplasm.ECTOPLASM) {
            return JNEFluids.ECTOPLASM_SOURCE.get().getSource(true);
        } else {
            return Fluids.EMPTY.defaultFluidState();
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if ((state.getValue(LIQUIDLOGGED) == Liquidlogged.WaterEctoplasm.WATER)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        } else if ((state.getValue(LIQUIDLOGGED) == Liquidlogged.WaterEctoplasm.ECTOPLASM)) {
            level.scheduleTick(pos, JNEFluids.ECTOPLASM_SOURCE.get(), JNEFluids.ECTOPLASM_SOURCE.get().getTickDelay(level));
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIQUIDLOGGED, INSCRIPTION, FACING, SALTED);
    }

    @Override
    public EnumProperty<Liquidlogged.WaterEctoplasm> getLiquidloggedBlockState() {
        return LIQUIDLOGGED;
    }
}
