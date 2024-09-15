package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface SimpleEctoplasmWaterloggedBlock extends BucketPickup, LiquidBlockContainer {

    EnumProperty<EctoplasmWaterlogged> LIQUIDLOGGED = EnumProperty.create("liquidlogged", EctoplasmWaterlogged.class);

    default boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return fluid == Fluids.WATER || fluid == JNEFluids.ECTOPLASM.get();
    }

    default boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (state.getValue(LIQUIDLOGGED) == EctoplasmWaterlogged.AIR) {
            if (!level.isClientSide()) {
                if (fluidState.getType() == Fluids.WATER) {
                    level.setBlock(pos, state.setValue(LIQUIDLOGGED, EctoplasmWaterlogged.WATER), 3);
                } else if (fluidState.getType() == JNEFluids.ECTOPLASM.get()) {
                    level.setBlock(pos, state.setValue(LIQUIDLOGGED, EctoplasmWaterlogged.ECTOPLASM), 3);
                }
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } else {
            return false;
        }
    }

    default @NotNull ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        if (state.getValue(LIQUIDLOGGED) != EctoplasmWaterlogged.AIR) {
            level.setBlock(pos, state.setValue(LIQUIDLOGGED, EctoplasmWaterlogged.AIR), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }
            if (state.getValue(LIQUIDLOGGED) == EctoplasmWaterlogged.WATER) {
                return new ItemStack(Items.WATER_BUCKET);
            }
            else {
                return new ItemStack(JNEFluids.ECTOPLASM_BUCKET.get());
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    default @NotNull Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }
}
