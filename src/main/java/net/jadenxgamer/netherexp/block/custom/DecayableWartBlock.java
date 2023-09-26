package net.jadenxgamer.netherexp.block.custom;

import net.jadenxgamer.netherexp.misc_registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class DecayableWartBlock extends Block {
    public static final IntProperty DISTANCE = IntProperty.of("distance", 1, 10);

    protected final ParticleEffect particle;
    protected final Block persistent;

    public DecayableWartBlock(Settings settings, ParticleEffect particle, Block persistent) {
        super(settings);
        this.particle = particle;
        this.persistent = persistent;
        this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, 10));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(DISTANCE) == 10;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldDecay(state)) {
            world.removeBlock(pos, false);
        }
    }

    protected boolean shouldDecay(BlockState state) {
        return state.get(DISTANCE) == 10;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, DecayableWartBlock.updateDistanceFromStem(state, world, pos), Block.NOTIFY_ALL);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int i;
        if ((i = DecayableWartBlock.getDistanceFromStem(neighborState) + 1) != 1 || state.get(DISTANCE) != i) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    private static BlockState updateDistanceFromStem(BlockState state, WorldAccess world, BlockPos pos) {
        int i = 10;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            i = Math.min(i, DecayableWartBlock.getDistanceFromStem(world.getBlockState(mutable)) + 1);
            if (i == 1) break;
        }
        return state.with(DISTANCE, i);
    }

    private static int getDistanceFromStem(BlockState state) {
        if (state.isIn(ModTags.Blocks.STEMS)) {
            return 0;
        }
        if (state.getBlock() instanceof DecayableWartBlock) {
            return state.get(DISTANCE);
        }
        return 10;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int d = state.get(DISTANCE);
        float f = random.nextFloat();
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() - 0.05;
        double z = (double)pos.getZ() + random.nextDouble();
        if (d >= 10 && f < 0.3f) {
            world.addParticle(this.particle, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.persistent);
    }
}
