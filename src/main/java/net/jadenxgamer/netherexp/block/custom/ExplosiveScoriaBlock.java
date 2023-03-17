package net.jadenxgamer.netherexp.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;

public class ExplosiveScoriaBlock
extends OreBlock {

    public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

    public ExplosiveScoriaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UNSTABLE, true));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
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

    private void explode(World world, final BlockPos explodedPos) {
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

        //TODO: add a custom damage source
        world.createExplosion(null, DamageSource.badRespawnPoint(), explosionBehavior, (double) explodedPos.getX() + 0.5, (double) explodedPos.getY() + 0.5, (double) explodedPos.getZ() + 0.5, 2.0f, false, Explosion.DestructionType.DESTROY);
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
