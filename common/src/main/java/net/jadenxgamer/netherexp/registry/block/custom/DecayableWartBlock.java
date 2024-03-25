package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class DecayableWartBlock extends Block {
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 1, 10);
    public static final IntegerProperty SPOTS = IntegerProperty.create("spots", 0, 3);

    // Particle is used to get the falling warts when decaying
    protected final ParticleOptions particle;

    // Persistent is the default block obtained when middle-clicked
    protected final Block persistent;

    /*
     * Spore value dictates what kind of spore to drop when sheared
     * 1 = Lightspores
     * 2 = Nightspores
     */
    protected final int spore;

    public DecayableWartBlock(Properties properties, ParticleOptions particle, Block persistent, int spore) {
        super(properties);
        this.particle = particle;
        this.persistent = persistent;
        this.spore = spore;
        this.registerDefaultState(this.defaultBlockState().setValue(DISTANCE, 10).setValue(SPOTS, 0));
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
        level.setBlock(pos, DecayableWartBlock.updateDistanceFromStem(state, level, pos), Block.UPDATE_ALL);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        int i;
        if ((i = DecayableWartBlock.getDistanceFromStem(neighborState) + 1) != 1 || state.getValue(DISTANCE) != i) {
            levelAccessor.scheduleTick(pos, this, 1);
        }
        return state;
    }

    private static void dropLight(Level level, BlockPos pos, BlockState state) {
        int s = state.getValue(SPOTS);
        DecayableWartBlock.popResource(level, pos, new ItemStack(JNEItems.LIGHTSPORES.get(), s));
    }

    private static void dropNight(Level level, BlockPos pos, BlockState state) {
        int s = state.getValue(SPOTS);
        DecayableWartBlock.popResource(level, pos, new ItemStack(JNEItems.NIGHTSPORES.get(), s));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean bl = false;
        int s = state.getValue(SPOTS);
        if (itemStack.is(Items.SHEARS) && s >= 1) {
            if (this.spore == 1) {
                dropLight(level, pos, state);
            } else if (this.spore == 2) {
                dropNight(level, pos, state);
            }
            level.playSound(player, pos, JNESoundEvents.LIGHTSPORES_SHEAR.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, state.setValue(SPOTS, 0));
            level.gameEvent(player, GameEvent.SHEAR, pos);
            itemStack.hurtAndBreak(1, player, (playerEntity) -> {
                playerEntity.broadcastBreakEvent(hand);
            });
            bl = true;
        }
        if (!level.isClientSide && bl) {
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @SuppressWarnings("all")
    public boolean maxSpots(BlockState state) {
        return state.getValue(SPOTS) == 3;
    }

    public BlockState setSpots(BlockState state) {
        int s = state.getValue(SPOTS);
        return state.setValue(SPOTS, s + 1);
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
            level.addParticle(this.particle, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, SPOTS);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(this.persistent);
    }
}
