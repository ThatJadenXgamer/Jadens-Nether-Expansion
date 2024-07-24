package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiquidloggedGrateBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<Liquids> LIQUIDLOGGED = EnumProperty.create("liquidlogged", Liquids.class);

    public LiquidloggedGrateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIQUIDLOGGED, Liquids.AIR));
    }

    public static int getLuminance(BlockState state) {
        switch (state.getValue(LIQUIDLOGGED)) {
            default -> {
                return 0;
            }
            case LAVA -> {
                return 15;
            }
            case ECTOPLASM -> {
                return 7;
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        if (fluidState.getType() == Fluids.WATER) {
            return this.defaultBlockState().setValue(LIQUIDLOGGED, Liquids.WATER);
        }
        else if (fluidState.getType() == Fluids.LAVA) {
            return this.defaultBlockState().setValue(LIQUIDLOGGED, Liquids.LAVA);
        }
        else if (fluidState.getType() == JNEFluids.ECTOPLASM.get()) {
            return JNEBlocks.RUSTY_NETHERITE_GRATE.get().defaultBlockState().setValue(LIQUIDLOGGED, Liquids.ECTOPLASM);
        }
        else return this.defaultBlockState();
    }

    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getVisualShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
        return Shapes.empty();
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return fluid == Fluids.WATER || fluid == Fluids.LAVA || fluid == JNEFluids.ECTOPLASM.get();
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.getValue(LIQUIDLOGGED) == Liquids.AIR) {
            if (!level.isClientSide()) {
                if (fluidState.getType() == Fluids.WATER) {
                    level.setBlock(pos, state.setValue(LIQUIDLOGGED, Liquids.WATER), 3);
                } else if (fluidState.getType() == Fluids.LAVA) {
                    level.setBlock(pos, state.setValue(LIQUIDLOGGED, Liquids.LAVA), 3);
                } else if (fluidState.getType() == JNEFluids.ECTOPLASM) {
                    level.setBlock(pos, state.setValue(LIQUIDLOGGED, Liquids.ECTOPLASM), 3);
                }
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        if (state.getValue(LIQUIDLOGGED) != Liquids.AIR) {
            level.setBlock(pos, state.setValue(LIQUIDLOGGED, Liquids.AIR), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }
            if (state.getValue(LIQUIDLOGGED) == Liquids.WATER) {
                return new ItemStack(Items.WATER_BUCKET);
            }
            else if (state.getValue(LIQUIDLOGGED) == Liquids.LAVA) {
                return new ItemStack(Items.LAVA_BUCKET);
            }
            else {
                return new ItemStack(JNEFluids.ECTOPLASM_BUCKET.get());
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        if (state.getValue(LIQUIDLOGGED) == Liquids.WATER) {
            return Fluids.WATER.getSource(false);
        }
        else if (state.getValue(LIQUIDLOGGED) == Liquids.LAVA) {
            return Fluids.LAVA.getSource(false);
        }
        else if (state.getValue(LIQUIDLOGGED) == Liquids.ECTOPLASM) {
            return JNEFluids.ECTOPLASM.get().getSource(false);
        }
        else {
            return super.getFluidState(state);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if ((state.getValue(LIQUIDLOGGED) == Liquids.WATER)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        else if ((state.getValue(LIQUIDLOGGED) == Liquids.LAVA)) {
            level.scheduleTick(pos, Fluids.LAVA, Fluids.LAVA.getTickDelay(level));
        }
        else if ((state.getValue(LIQUIDLOGGED) == Liquids.ECTOPLASM)) {
            level.scheduleTick(pos, JNEFluids.ECTOPLASM.get(), JNEFluids.ECTOPLASM.get().getTickDelay(level));
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIQUIDLOGGED);
    }
}