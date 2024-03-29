package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class DecayableShroomBlock extends Block {
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 1, 10);

    // Particle is used to get the falling warts when decaying
    protected final int type;

    // Persistent is the default block obtained when middle-clicked
    protected final Block persistent;

    public DecayableShroomBlock(Properties properties, int type, Block persistent) {
        super(properties);
        this.type = type;
        this.persistent = persistent;
        this.registerDefaultState(this.defaultBlockState().setValue(DISTANCE, 10));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(DISTANCE) == 10;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.shouldDecay(state)) {
            level.removeBlock(pos, false);
        }
    }

    protected boolean shouldDecay(BlockState state) {
        return state.getValue(DISTANCE) == 10;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, updateDistanceFromStem(state, level, pos), Block.UPDATE_ALL);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        int i;
        if ((i = getDistanceFromStem(neighborState) + 1) != 1 || state.getValue(DISTANCE) != i) {
            levelAccessor.scheduleTick(pos, this, 1);
        }
        return state;
    }

    private static BlockState updateDistanceFromStem(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
        int i = 10;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            mutableBlockPos.setWithOffset(blockPos, direction);
            i = Math.min(i, getDistanceFromStem(levelAccessor.getBlockState(mutableBlockPos)) + 1);
            if (i == 1) {
                break;
            }
        }

        return blockState.setValue(DISTANCE, i);
    }

    private static int getDistanceFromStem(BlockState state) {
        if (state.is(JNETags.Blocks.STEMS)) {
            return 0;
        }
        if (state.getBlock() instanceof DecayableWartBlock) {
            return state.getValue(DISTANCE);
        }
        return 10;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int d = state.getValue(DISTANCE);
        float f = random.nextFloat();
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() - 0.05;
        double z = (double)pos.getZ() + random.nextDouble();
        if (d >= 10 && f < 0.3f) {
            switch (type) {
                default: {
                    level.addParticle(JNEParticleTypes.FALLING_SHROOMLIGHT.get(), x, y, z, 0.0, 0.0, 0.0);
                }
                case 2: {
                    level.addParticle(JNEParticleTypes.FALLING_SHROOMNIGHT.get(), x, y, z, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(this.persistent);
    }
}
