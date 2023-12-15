package net.jadenxgamer.netherexp.registry.fluid;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class EctoplasmFluid extends FlowableFluid {

    @Override
    protected boolean isInfinite(World world) {
        return true;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Nullable
    @Override
    protected ParticleEffect getParticle() {
        return super.getParticle();
    }


    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == ModFluids.ECTOPLASM || fluid == ModFluids.FLOWING_ECTOPLASM;
    }
    @Override
    protected int getFlowSpeed(WorldView world) {
        return 3;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        BlockPos blockPos = pos.up();
        if (world.getBlockState(blockPos).isAir() && !world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos) && state.isOf(ModFluids.ECTOPLASM)) {
            if (NetherExp.getConfig().visualeffects.ectoplasm_particles && random.nextInt(55) == 0) {
                double d = (double) pos.getX() + random.nextDouble();
                double e = (double) pos.getY() + 1.0;
                double f = (double) pos.getZ() + random.nextDouble();
                world.addParticle(ModParticles.ECTORAYS, d, e, f, 0.0, -0.03, 0.0);
            }
        }
        if (world.getBlockState(blockPos).isAir() && !world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
            if (NetherExp.getConfig().visualeffects.ectoplasm_particles && random.nextInt(18) == 0) {
                double d = (double) pos.getX() + random.nextDouble();
                double e = (double) pos.getY() + 1.0;
                double f = (double) pos.getZ() + random.nextDouble();
                world.addParticle(ModParticles.ECTOPLASMA, d, e, f, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }

    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.ECTOPLASM;
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_ECTOPLASM;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluids.ECTOPLASM_BLOCK.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Override
    public Item getBucketItem() {
        return ModFluids.ECTOPLASM_BUCKET;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(ModSoundEvents.ITEM_BUCKET_FILL_ECTOPLASM);
    }

    public static class Flowing extends EctoplasmFluid {
        public Flowing() {
        }

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }

    public static class Still extends EctoplasmFluid {
        public Still() {
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }
    }
}
