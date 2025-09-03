package net.jadenxgamer.netherexp.core.block.interfaces;

import net.jadenxgamer.netherexp.core.block.enums.Liquidlogged;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface LiquidloggedBlock extends SimpleWaterloggedBlock {

    EnumProperty<Liquidlogged.AllFluids> getLiquidloggedBlockState();

    @Override
    default boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return fluid == Fluids.WATER || fluid == Fluids.LAVA || fluid == JNEFluids.ECTOPLASM_SOURCE.get();
    }

    @Override
    default boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.getValue(getLiquidloggedBlockState()) == Liquidlogged.AllFluids.AIR) {
            if (!level.isClientSide()) {
                if (fluidState.getType() == Fluids.WATER) {
                    level.setBlock(pos, state.setValue(getLiquidloggedBlockState(), Liquidlogged.AllFluids.WATER), Block.UPDATE_ALL);
                }
                else if (fluidState.getType() == Fluids.LAVA) {
                    level.setBlock(pos, state.setValue(getLiquidloggedBlockState(), Liquidlogged.AllFluids.LAVA), Block.UPDATE_ALL);
                }
                else if (fluidState.getType() == JNEFluids.ECTOPLASM_SOURCE.get()) {
                    level.setBlock(pos, state.setValue(getLiquidloggedBlockState(), Liquidlogged.AllFluids.ECTOPLASM), Block.UPDATE_ALL);
                }
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } else return false;
    }

    @Override
    default ItemStack pickupBlock(Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        if (state.getValue(getLiquidloggedBlockState()) != Liquidlogged.AllFluids.AIR) {
            level.setBlock(pos, state.setValue(getLiquidloggedBlockState(), Liquidlogged.AllFluids.AIR), Block.UPDATE_ALL);
            if (!state.canSurvive(level, pos)) level.destroyBlock(pos, true);
            if (state.getValue(getLiquidloggedBlockState()) == Liquidlogged.AllFluids.WATER) return new ItemStack(Items.WATER_BUCKET);
            if (state.getValue(getLiquidloggedBlockState()) == Liquidlogged.AllFluids.LAVA) return new ItemStack(Items.LAVA_BUCKET);
            else return new ItemStack(JNEFluids.ECTOPLASM_BUCKET.get());
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    default Optional<SoundEvent> getPickupSound(BlockState state) {
        if (state.getValue(getLiquidloggedBlockState()) == Liquidlogged.AllFluids.LAVA) return Fluids.LAVA.getPickupSound();
        if (state.getValue(getLiquidloggedBlockState()) == Liquidlogged.AllFluids.ECTOPLASM) return JNEFluids.ECTOPLASM.get().getPickupSound();
        return SimpleWaterloggedBlock.super.getPickupSound(state);
    }
}
