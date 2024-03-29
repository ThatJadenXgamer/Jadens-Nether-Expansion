package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SmokestalkBlock extends GrowingPlantHeadBlock {

    public static final BooleanProperty SMOKEY = BooleanProperty.create("smokey");

    public static final VoxelShape SHAPE = Shapes.join(Block.box(5, 0, 5, 11, 8, 11), Block.box(4, 8, 4, 12, 12, 12), BooleanOp.OR);

    public SmokestalkBlock(Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
        this.registerDefaultState(defaultBlockState().setValue(SMOKEY, false));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean s = state.getValue(SMOKEY);
        if (s) {
            level.addParticle(ParticleTypes.LARGE_SMOKE, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.012, 0.0);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean s = blockState.getValue(SMOKEY);
        boolean bl = false;
        if (!s) {
            if (itemStack.is(JNETags.Items.SMOKESTALK_FUEL)) {
                level.playSound(player, blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
                level.setBlock(blockPos, blockState.cycle(SMOKEY), Block.UPDATE_CLIENTS);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                spawnParticles(level, blockPos);
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

    // Particles for Gilding
    private static void spawnParticles(Level level, BlockPos blockPos) {
        RandomSource randomSource = level.random;
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                level.addParticle(ParticleTypes.FLAME, (double) blockPos.getX() + e, (double) blockPos.getY() + f, (double) blockPos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SMOKEY, AGE);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return NetherVines.getBlocksToGrowWhenBonemealed(randomSource);
    }

    @Override
    protected @NotNull Block getBodyBlock() {
        return JNEBlocks.SMOKESTALK_PLANT.get();
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return NetherVines.isValidGrowthState(blockState);
    }
}
