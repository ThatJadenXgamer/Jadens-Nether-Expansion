package net.jadenxgamer.netherexp.mixin.worldgen;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(BasaltColumnsFeature.class)
public class BasaltColumnsFeatureMixin {

    @Inject(
            method = "canPlaceAt",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$canPlaceAt(LevelAccessor levelAccessor, int i, BlockPos.MutableBlockPos mutableBlockPos, CallbackInfoReturnable<Boolean> cir) {
        if (levelAccessor.getBlockState(mutableBlockPos.below()).is(JNETags.Blocks.SANCTUM_BLOCKS)) {
            cir.setReturnValue(false);
        }
    }
}
