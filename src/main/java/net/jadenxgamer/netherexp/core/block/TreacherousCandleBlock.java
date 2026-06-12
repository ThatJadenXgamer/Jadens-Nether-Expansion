package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.core.block.entity.TreacherousCandleBlockEntity;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TreacherousCandleBlock extends BaseEntityBlock {

    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final BooleanProperty BROKEN = BooleanProperty.create("broken");
    public static final BooleanProperty COMPLETED = BooleanProperty.create("completed");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 16, 13);
    protected static final VoxelShape BROKEN_SHAPE = Block.box(1, 0, 1, 15, 2, 15);

    public TreacherousCandleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false).setValue(BROKEN, false).setValue(FACING, Direction.NORTH).setValue(COMPLETED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, JNEBlockEntityType.TREACHEROUS_CANDLE.get(), TreacherousCandleBlockEntity::tick);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(BROKEN) ? BROKEN_SHAPE : SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TreacherousCandleBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        boolean lit = state.getValue(LIT);
        boolean broken = state.getValue(BROKEN);
        boolean bl = false;
        if (broken) {
            if (itemStack.is(Items.HONEYCOMB)) {
                bl = true;
                if (blockEntity instanceof TreacherousCandleBlockEntity treacherousCandleBlock) {
                    TreacherousCandleBlockEntity.resetValues(treacherousCandleBlock);
                }
                level.setBlock(pos, state.setValue(BROKEN, false), Block.UPDATE_CLIENTS);
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_CANDLE_PLACE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }
        else if (!lit) {
            if (itemStack.is(Items.FLINT_AND_STEEL) && validPath(state, pos, level)) {
                bl = true;
                if (blockEntity instanceof TreacherousCandleBlockEntity treacherousCandleBlock) {
                    TreacherousCandleBlockEntity.findAllNearbyPlayers(treacherousCandleBlock, pos, level);
                }
                level.setBlock(pos, state.cycle(LIT), Block.UPDATE_CLIENTS);
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.BRAZIER_CHEST_LIT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                fireParticles(level, pos);
                if (!player.getAbilities().instabuild) itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            else if (itemStack.is(Items.FLINT_AND_STEEL) && !validPath(state, pos, level)) {
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
        if (bl) {
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private boolean validPath(BlockState state, BlockPos pos, Level level) {
        Direction facing = state.getValue(FACING);

        for (int d = 1; d <= 8; d++) {
            BlockPos raycast = pos.relative(facing, d);
            BlockPos raycastAbove = raycast.above();

            // Checks the center and above block to see if it's air
            if (!level.getBlockState(raycast).isAir() || !level.getBlockState(raycastAbove).isAir()) {
                return false;
            }
        }
        return true;
    }

    public @NotNull RenderShape getRenderShape(BlockState arg) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, COMPLETED, BROKEN, FACING);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean lit = state.getValue(LIT);
        boolean completed = state.getValue(COMPLETED);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(x + Mth.nextInt(random, -20, 20), y + random.nextInt(20), z + Mth.nextInt(random, -20, 20));
            BlockState blockState = level.getBlockState(mutable);
            if (blockState.isSolidRender(level, mutable)) continue;
            if (lit && !completed) {
                level.addParticle(JNEParticleTypes.RED_SPARKLE.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                int u = random.nextInt(6);
                if (u == 0) {
                    level.addParticle(JNEParticleTypes.RED_HAZE.get(), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
            }
        }

        int i = random.nextInt(1);
        if (i == 0 && lit) {
            level.addParticle(JNEParticleTypes.TREACHEROUS_FLAME.get(), (double)pos.getX() + 0.5 + level.random.nextDouble() / 4.0 * (double)(level.random.nextBoolean() ? 1 : -1), (double)pos.getY() + 1.1, (double)pos.getZ() + 0.5 + level.random.nextDouble() / 4.0 * (double)(level.random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        }
    }

    private static void fireParticles(Level level, BlockPos blockPos) {
        RandomSource randomSource = level.random;
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                level.addParticle(JNEParticleTypes.TREACHEROUS_FLAME.get(), (double) blockPos.getX() + e, (double) blockPos.getY() + f, (double) blockPos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }
}
