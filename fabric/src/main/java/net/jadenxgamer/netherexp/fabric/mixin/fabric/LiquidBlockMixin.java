package net.jadenxgamer.netherexp.fabric.mixin.fabric;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.LiquidBlock.POSSIBLE_FLOW_DIRECTIONS;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {

    @Shadow @Final
    protected FlowingFluid fluid;

    @SuppressWarnings("deprecation")
    @Inject(
            method = "shouldSpreadLiquid",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$changeSpreadLiquid(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid.is(JNETags.Fluids.ECTOPLASM)) {

            for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.offset(direction.getOpposite().getNormal());
                if (level.getFluidState(blockPos).is(FluidTags.WATER)) {
                    Block block = JNEBlocks.BLACK_ICE.get();
                    level.setBlock(pos, block.defaultBlockState(), 2);
                    this.playFreezeSound(level, pos);
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Unique
    private void playFreezeSound(Level level, BlockPos pos) {
        level.playSound(null, pos, JNESoundEvents.ECTOPLASM_FREEZE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
