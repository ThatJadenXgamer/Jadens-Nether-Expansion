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

    protected abstract Iterable<Vec3> getParticleOffsets(BlockState state);

    @SuppressWarnings("deprecation")
    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire() && !state.getValue(LIT)) {
            setLit(level, state, hitResult.getBlockPos(), true);
        }
    }

    protected boolean canBeLit(BlockState state) {
        return !(Boolean)state.getValue(LIT);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            this.getParticleOffsets(state).forEach((vec3) -> addParticlesAndSound(level, vec3.add(pos.getX(), pos.getY(), pos.getZ()), random));
        }
    }

    private static void addParticlesAndSound(Level level, Vec3 vec3, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                level.playLocalSound(vec3.x + 0.5, vec3.y + 0.5, vec3.z + 0.5, JNESoundEvents.BLOCK_SOUL_CANDLE_AMBIENT.get(), SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        level.addParticle(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
    }

    public static void extinguish(Player player, BlockState state, LevelAccessor level, BlockPos pos) {
        setLit(level, state, pos, false);
        if (state.getBlock() instanceof AbstractSoulCandleBlock) {
            ((AbstractSoulCandleBlock)state.getBlock()).getParticleOffsets(state).forEach((vec3) -> level.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + vec3.x(), (double)pos.getY() + vec3.y(), (double)pos.getZ() + vec3.z(), 0.0, 0.10000000149011612, 0.0));
        }

        level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }


    private static void setLit(LevelAccessor level, BlockState state, BlockPos pos, boolean lit) {
        level.setBlock(pos, state.setValue(LIT, lit), Block.UPDATE_ALL);
    }
}