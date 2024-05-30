package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    protected AbstractSoulCandleBlock(Properties properties) {
        super(properties);
    }

    protected abstract Iterable<Vec3> getParticleOffsets(BlockState blockState);

    @SuppressWarnings("deprecation")
    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire() && !blockState.getValue(LIT)) {
            setLit(level, blockState, blockHitResult.getBlockPos(), true);
        }
    }

    protected boolean canBeLit(BlockState blockState) {
        return !(Boolean)blockState.getValue(LIT);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(LIT)) {
            this.getParticleOffsets(blockState).forEach((vec3) -> addParticlesAndSound(level, vec3.add(blockPos.getX(), blockPos.getY(), blockPos.getZ()), randomSource));
        }
    }

    private static void addParticlesAndSound(Level level, Vec3 vec3, RandomSource randomSource) {
        float f = randomSource.nextFloat();
        if (f < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                level.playLocalSound(vec3.x + 0.5, vec3.y + 0.5, vec3.z + 0.5, JNESoundEvents.BLOCK_SOUL_CANDLE_AMBIENT.get(), SoundSource.BLOCKS, 1.0F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        level.addParticle(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
    }

    public static void extinguish(Player player, BlockState blockState, LevelAccessor level, BlockPos blockPos) {
        setLit(level, blockState, blockPos, false);
        if (blockState.getBlock() instanceof AbstractSoulCandleBlock) {
            ((AbstractSoulCandleBlock)blockState.getBlock()).getParticleOffsets(blockState).forEach((vec3) -> level.addParticle(ParticleTypes.SMOKE, (double)blockPos.getX() + vec3.x(), (double)blockPos.getY() + vec3.y(), (double)blockPos.getZ() + vec3.z(), 0.0, 0.10000000149011612, 0.0));
        }

        level.playSound(null, blockPos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
    }


    private static void setLit(LevelAccessor world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlock(pos, state.setValue(LIT, lit), Block.UPDATE_ALL);
    }
}