package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.FluidBlock.FLOW_DIRECTIONS;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin extends Block implements FluidDrainable {

    @Shadow
    @Final
    protected FlowableFluid fluid;

    public FluidBlockMixin(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Inject(
            method = "receiveNeighborFluids",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid.isIn(ModTags.Fluids.ECTOPLASM)) {

            for (Direction direction : FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    Block block = world.getFluidState(pos).isStill() ? Blocks.PACKED_ICE : Blocks.ICE;
                    world.setBlockState(pos, block.getDefaultState());
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
