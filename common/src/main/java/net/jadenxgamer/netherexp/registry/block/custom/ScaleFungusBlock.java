package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScaleFungusBlock
extends BushBlock
implements BonemealableBlock {

    public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");

    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 4.0, 15.0);

    public ScaleFungusBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0).setValue(SHEARED, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean s = blockState.getValue(SHEARED);
        boolean bl = false;
        if (!s) {
            if (itemStack.is(Items.SHEARS)) {
                level.playSound(player, blockPos, JNESoundEvents.GILDING.get(), SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
                level.setBlock(blockPos, blockState.cycle(SHEARED), Block.UPDATE_CLIENTS);
                if (!player.isCreative()) {
                    itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(interactionHand));
                }
                bl = true;
                if (!level.isClientSide) {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                }
            }
            if (bl) {
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("all")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(ROTATION, Mth.floor((double)(ctx.getRotation() * 16.0f / 360.0f) + 0.5) & 0xF);
    }

    @SuppressWarnings("all")
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), 16));
    }

    @SuppressWarnings("all")
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), 16));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, AGE, SHEARED);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        int a = state.getValue(AGE);
        boolean s = state.getValue(SHEARED);
        return a < 2 || !s;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(AGE);
        boolean s = state.getValue(SHEARED);
        if (i < 2 && !s && random.nextInt(10) == 0) {
            state = state.setValue(AGE, i + 1);
            level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public boolean canSurvive(BlockState floor, LevelReader levelReader, BlockPos blockPos) {
        return floor.is(JNETags.Blocks.SCALE_FUNGUS_PLANTABLE_ON) || floor.is(BlockTags.NYLIUM) || super.canSurvive(floor, levelReader, blockPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age < 2) {
            level.setBlock(pos, defaultBlockState().setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
        }
    }
}
