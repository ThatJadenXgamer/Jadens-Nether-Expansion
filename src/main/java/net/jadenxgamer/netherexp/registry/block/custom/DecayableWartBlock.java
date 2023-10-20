package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.item.ModItems;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class DecayableWartBlock extends Block {
    public static final IntProperty DISTANCE = IntProperty.of("distance", 1, 10);
    public static final IntProperty SPOTS = IntProperty.of("spots", 0, 3);

    // Particle is used to get the falling warts when decaying
    protected final ParticleEffect particle;

    // Persistent is the default block obtained when middle-clicked
    protected final Block persistent;

    /*
     * Spore value dictates what kind of spore to drop when sheared
     * 1 = Lightspores
     * 2 = Nightspores
     */
    protected final int spore;

    public DecayableWartBlock(Settings settings, ParticleEffect particle, Block persistent, int spore) {
        super(settings);
        this.particle = particle;
        this.persistent = persistent;
        this.spore = spore;
        this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, 10).with(SPOTS, 0));
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
        if ((i = DecayableWartBlock.getDistanceFromStem(neighborState) + 1) != 1 || state.get(DISTANCE) != i && !neighborState.isOf(ModBlocks.WARPED_WART_BEARD)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    private static void dropLight(World world, BlockPos pos, BlockState state) {
        int s = state.get(SPOTS);
        SpottedWartBlock.dropStack(world, pos, new ItemStack(ModItems.LIGHTSPORES, s));
    }

    private static void dropNight(World world, BlockPos pos, BlockState state) {
        int s = state.get(SPOTS);
        SpottedWartBlock.dropStack(world, pos, new ItemStack(ModItems.NIGHTSPORES, s));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl = false;
        int s = state.get(SPOTS);
        if (itemStack.isOf(Items.SHEARS) && s >= 1) {
            if (this.spore == 1) {
                dropLight(world, pos, state);
            }
            else if (this.spore == 2) {
                dropNight(world, pos, state);
            }
            world.playSound(player, pos, ModSoundEvents.LIGHTSPORES_SHEAR, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, state.with(SPOTS, 0), Block.NOTIFY_LISTENERS);
            world.emitGameEvent(player, GameEvent.SHEAR, pos);
            itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
            bl = true;
        }
        if (!world.isClient() && bl) {
            player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
        }
        if (bl) {
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @SuppressWarnings("all")
    public boolean maxSpots(BlockState state) {
        return state.get(SPOTS) == 3;
    }

    public BlockState setSpots(BlockState state) {
        int s = state.get(SPOTS);
        return state.with(SPOTS, s + 1);
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
        builder.add(DISTANCE, SPOTS);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.persistent);
    }
}
