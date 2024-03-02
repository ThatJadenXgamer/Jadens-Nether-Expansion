package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class LiquidloggedGrateBlock extends Block implements Waterloggable {

    public static final EnumProperty<Liquids> LIQUIDLOGGED = EnumProperty.of("liquidlogged", Liquids.class);

    public LiquidloggedGrateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIQUIDLOGGED, Liquids.AIR));
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean canFillWithFluid(@Nullable PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return fluid == Fluids.WATER || fluid == Fluids.LAVA || fluid == JNEFluids.ECTOPLASM;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.get(LIQUIDLOGGED) == Liquids.AIR) {
            if (!world.isClient()) {
                if (fluidState.getFluid() == Fluids.WATER) {
                    world.setBlockState(pos, state.with(LIQUIDLOGGED, Liquids.WATER), 3);
                } else if (fluidState.getFluid() == Fluids.LAVA) {
                    world.setBlockState(pos, state.with(LIQUIDLOGGED, Liquids.LAVA), 3);
                } else if (fluidState.getFluid() == JNEFluids.ECTOPLASM) {
                    world.setBlockState(pos, state.with(LIQUIDLOGGED, Liquids.ECTOPLASM), 3);
                }
                world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack tryDrainFluid(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(LIQUIDLOGGED) != Liquids.AIR) {
            world.setBlockState(pos, state.with(LIQUIDLOGGED, Liquids.AIR), 3);
            if (!state.canPlaceAt(world, pos)) {
                world.breakBlock(pos, true);
            }

            if (state.get(LIQUIDLOGGED) == Liquids.WATER) {
                return new ItemStack(Items.WATER_BUCKET);
            }
            else if (state.get(LIQUIDLOGGED) == Liquids.LAVA) {
                return new ItemStack(Items.LAVA_BUCKET);
            }
            else {
                return new ItemStack(JNEFluids.ECTOPLASM_BUCKET);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(LIQUIDLOGGED) == Liquids.WATER) {
            return Fluids.WATER.getStill(false);
        }
        else if (state.get(LIQUIDLOGGED) == Liquids.LAVA) {
            return Fluids.LAVA.getStill(false);
        }
        else if (state.get(LIQUIDLOGGED) == Liquids.ECTOPLASM) {
            return JNEFluids.ECTOPLASM.getStill(false);
        }
        else {
            return super.getFluidState(state);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((state.get(LIQUIDLOGGED) == Liquids.WATER)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        else if ((state.get(LIQUIDLOGGED) == Liquids.LAVA)) {
            world.scheduleFluidTick(pos, Fluids.LAVA, Fluids.LAVA.getTickRate(world));
        }
        else if ((state.get(LIQUIDLOGGED) == Liquids.ECTOPLASM)) {
            world.scheduleFluidTick(pos, JNEFluids.ECTOPLASM, JNEFluids.ECTOPLASM.getTickRate(world));
        }

        return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIQUIDLOGGED);
    }
}
