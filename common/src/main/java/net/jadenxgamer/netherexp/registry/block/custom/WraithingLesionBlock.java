package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class WraithingLesionBlock extends Block {

    public static final IntegerProperty SLICES = IntegerProperty.create("slices", 1, 4);

    public WraithingLesionBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SLICES, 4));
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        int slices = state.getValue(SLICES);
        if (stack.is(Items.ROTTEN_FLESH) && slices < 4) {
            stack.shrink(1);
            level.playSound(null, pos, SoundEvents.MUD_BRICKS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (level.random.nextInt(5) == 0) {
                level.setBlock(pos, state.setValue(SLICES, slices + 1), 2);
                spawnParticles(level, pos, ParticleTypes.COMPOSTER);
            }
            return InteractionResult.SUCCESS;
        } else {
            if (slices == 1) {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            } else {
                level.setBlock(pos, state.setValue(SLICES, slices - 1), 2);
            }
            popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(JNEItems.WRAITHING_FLESH.get(), 12));
            level.playSound(null, pos, SoundEvents.MUD_BRICKS_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
            spawnParticles(level, pos, ParticleTypes.SOUL);
        }
        return InteractionResult.SUCCESS;
    }

    private static void spawnParticles(Level level, BlockPos blockPos, ParticleOptions particle) {
        RandomSource randomSource = level.random;
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                level.addParticle(particle, (double) blockPos.getX() + e, (double) blockPos.getY() + f, (double) blockPos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(SLICES) < 4;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int slices = state.getValue(SLICES);
        if (random.nextInt(10) == 0) {
            level.setBlock(pos, state.setValue(SLICES, slices + 1), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SLICES);
    }
}
