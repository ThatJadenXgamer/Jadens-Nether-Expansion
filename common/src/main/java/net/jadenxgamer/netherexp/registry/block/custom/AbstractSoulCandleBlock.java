package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractSoulCandleBlock extends Block {
    public static final int field_30987 = 3;
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    protected AbstractSoulCandleBlock(Properties properties) {
        super(properties);
    }

    protected abstract Iterable<Vec3> getParticleOffsets(BlockState blockState);

    protected boolean isNotLit(BlockState state) {
        return !state.getValue(LIT);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire() && !blockState.getValue(LIT)) {
            setLit(level, blockState, blockHitResult.getBlockPos(), true);
        }
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.getValue(LIT)) {
            return;
        }
        this.getParticleOffsets(blockState).forEach(offset -> spawnCandleParticles(level, BlockPos.containing(offset.add(blockPos.getX(), blockPos.getY(), blockPos.getZ())), randomSource));
    }

    private void spawnCandleParticles(Level world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.3f) {
            world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
            if (random.nextFloat() < 0.17f) {
                world.playSound(null, pos, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f);
            }
        }
        world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
    }

    public static void extinguish(Player player, BlockState blockState, LevelAccessor level, BlockPos blockPos) {
        setLit(level, blockState, blockPos, false);
        level.playSound(player, blockPos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (!level.isClientSide()) {
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }


    private static void setLit(LevelAccessor world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlock(pos, state.setValue(LIT, lit), Block.UPDATE_ALL);
    }
}