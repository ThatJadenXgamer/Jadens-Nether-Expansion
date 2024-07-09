package net.jadenxgamer.netherexp.registry.block.custom;

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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class LightAbleBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public LightAbleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean lit = blockState.getValue(LIT);
        boolean bl = false;
        if (!lit) {
            if (itemStack.is(Items.FLINT_AND_STEEL)) {
                level.playSound(player, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
                level.setBlock(blockPos, blockState.cycle(LIT), Block.UPDATE_CLIENTS);
                if (!player.isCreative()) {
                    itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(interactionHand));
                }
                blockParticle(level, blockPos);
                bl = true;
            } else if (itemStack.is(Items.FIRE_CHARGE)) {
                level.playSound(player, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
                level.setBlock(blockPos, blockState.cycle(LIT), Block.UPDATE_CLIENTS);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                blockParticle(level, blockPos);
                bl = true;
            }
            if (!level.isClientSide && bl) {
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            }
        }
        if (bl) {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private static void blockParticle(Level level, BlockPos blockPos) {
        RandomSource randomSource = level.random;
        Direction[] var5 = Direction.values();

        for (Direction direction : var5) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) randomSource.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) randomSource.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) randomSource.nextFloat();
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, (double) blockPos.getX() + e, (double) blockPos.getY() + f, (double) blockPos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (!level.isClientSide && projectile.isOnFire() && projectile.mayInteract(level, blockPos) && !(Boolean)blockState.getValue(LIT)) {
            level.setBlock(blockPos, blockState.setValue(LIT, true), 11);
        }
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
