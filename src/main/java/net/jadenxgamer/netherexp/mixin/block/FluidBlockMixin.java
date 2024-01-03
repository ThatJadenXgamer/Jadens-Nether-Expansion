package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Unique
    private void playFreezeSound(World world, BlockPos pos) {
        world.playSound(null, pos, ModSoundEvents.ECTOPLASM_FREEZE, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
                    Block block = ModBlocks.BLACK_ICE;
                    world.setBlockState(pos, block.getDefaultState());
                    this.playFreezeSound(world, pos);
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
