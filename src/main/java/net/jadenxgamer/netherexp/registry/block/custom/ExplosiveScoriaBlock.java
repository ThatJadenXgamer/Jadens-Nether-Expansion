package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;

public class ExplosiveScoriaBlock
extends Block {

    //TODO: Needs a way for the unstable property to be disabled in normal gameplay
    public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

    public ExplosiveScoriaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UNSTABLE, true));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        if (!player.isCreative() && state.get(UNSTABLE)) {
            world.addParticle(ModParticles.REDSTONE_EXPLOSION_EMITTER, x, y, z, 0.0, 0.0, 0.0);
            world.playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
        if (!world.isClient() && !player.isCreative() && state.get(UNSTABLE)) {
            this.explode(world, pos);
        }
        super.onBreak(world, pos, state, player);
    }

    private static boolean hasStillWater(BlockPos pos, World world) {
        FluidState fluidState = world.getFluidState(pos);
        if (!fluidState.isIn(FluidTags.WATER)) {
            return false;
        }
        if (fluidState.isStill()) {
            return true;
        }
        float f = fluidState.getLevel();
        if (f < 2.0f) {
            return false;
        }
        FluidState fluidState2 = world.getFluidState(pos.down());
        return !fluidState2.isIn(FluidTags.WATER);
    }

    private void explode(World world, BlockPos explodedPos) {
        world.removeBlock(explodedPos, false);
        boolean bl = Direction.Type.HORIZONTAL.stream().map(explodedPos::offset).anyMatch(pos -> ExplosiveScoriaBlock.hasStillWater(pos, world));
        final boolean bl2 = bl || world.getFluidState(explodedPos.up()).isIn(FluidTags.WATER);
        ExplosionBehavior explosionBehavior = new ExplosionBehavior() {
            @Override
            public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
                if (pos.equals(explodedPos) && bl2) {
                    return Optional.of(Blocks.WATER.getBlastResistance());
                }
                return super.getBlastResistance(explosion, world, pos, blockState, fluidState);
            }
        };
        world.createExplosion(null, world.getDamageSources().explosion(null), explosionBehavior, explodedPos.getX() + 0.5, explodedPos.getY() + 0.5, explodedPos.getZ() + 0.5, 2.0f, false, World.ExplosionSourceType.TNT, false);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        float f = random.nextFloat();
        if (f < 0.3f) {
            ExplosiveScoriaBlock.sparkParticles(world, pos);
            if (f < 0.17f) {
                world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, ModSoundEvents.EXPLOSIVE_SCORIA_AMBIENT, SoundCategory.BLOCKS,0.3f,random.nextFloat() * 0.7f + 0.3f,false);
            }
        }
    }

    private static void sparkParticles(World world, BlockPos pos) {
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getOffsetX() : (double)random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getOffsetY() : (double)random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getOffsetZ() : (double)random.nextFloat();
            world.addParticle(ModParticles.REDSTONE_SPARK, (double)pos.getX() + e, (double)pos.getY() + f, (double)pos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UNSTABLE);
    }
}
