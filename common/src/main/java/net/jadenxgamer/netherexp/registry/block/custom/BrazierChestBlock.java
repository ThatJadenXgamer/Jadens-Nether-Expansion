package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.entity.BrazierChestBlockEntity;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrazierChestBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");

    public BrazierChestBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(LOCKED, true));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        boolean locked = blockState.getValue(LOCKED);
        boolean bl = false;
        if (locked) {
            if (itemStack.is(JNEItems.TREACHEROUS_FLAME.get())) {
                bl = true;
                level.setBlock(pos, blockState.cycle(LOCKED), Block.UPDATE_CLIENTS);
                if (blockEntity instanceof BrazierChestBlockEntity) {
                    ((BrazierChestBlockEntity) blockEntity).refillLoot();
                    player.openMenu((BrazierChestBlockEntity) blockEntity);
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                if (!level.isClientSide) {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                }
            }
        }
        else {
            bl = true;
            if (blockEntity instanceof BrazierChestBlockEntity) {
                ((BrazierChestBlockEntity) blockEntity).refillLoot();
                player.openMenu((BrazierChestBlockEntity) blockEntity);
            }
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("deprecation")
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BrazierChestBlockEntity) {
            ((BrazierChestBlockEntity)blockEntity).recheckOpen();
        }
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BrazierChestBlockEntity(pos, state);
    }

    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BrazierChestBlockEntity) {
                ((BrazierChestBlockEntity)blockEntity).setCustomName(stack.getHoverName());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @SuppressWarnings("deprecation")
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, LOCKED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }
}
