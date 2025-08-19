package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.core.block.entity.DiscernmentGlassBlockEntity;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DiscernmentGlassBlock extends BaseEntityBlock {

    public static final MapCodec<DiscernmentGlassBlock> CODEC = simpleCodec(DiscernmentGlassBlock::new);

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public DiscernmentGlassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DiscernmentGlassBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityContext && level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity) {
            if (entityContext.getEntity() instanceof ItemEntity item) {
                boolean isFilterItem = item.getItem().getItem() == blockEntity.getFilterItem().getItem();
                if ((state.getValue(POWERED) && !isFilterItem) || (!state.getValue(POWERED) && isFilterItem)) {
                    return Shapes.empty();
                }
            }
        }
        return super.getCollisionShape(state, level, pos, context);
    }

    @Override
    public RenderShape getRenderShape(BlockState arg) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity) {
            if (!stack.isEmpty() && blockEntity.getFilterItem().isEmpty()) {
                blockEntity.setFilterItem(stack);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
                level.playSound(null, pos, JNESoundEvents.DISCERNMENT_GLASS_ADD.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                level.blockUpdated(pos, this);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else if (stack.isEmpty() && !blockEntity.getFilterItem().isEmpty()) {
                if (!player.getAbilities().instabuild) {
                    if (player.getInventory().add(blockEntity.getFilterItem())) {
                        blockEntity.removeFilterItem();
                    }
                } else {
                    blockEntity.removeFilterItem();
                }
                ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
                level.playSound(null, pos, JNESoundEvents.DISCERNMENT_GLASS_REMOVE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                level.blockUpdated(pos, this);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity && !blockEntity.getFilterItem().isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), blockEntity.getFilterItem());
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (state.getValue(POWERED) != level.hasNeighborSignal(pos)) {
            if (state.getValue(POWERED)) {
                level.scheduleTick(pos, this, 4);
            } else {
                level.setBlock(pos, state.cycle(POWERED), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(POWERED), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity) {
            return blockEntity.getFilterItem().isEmpty() ? 0 : 15;
        }
        return 0;
    }

    // Glass Stuff

    @Override
    protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    // Codec shit

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
