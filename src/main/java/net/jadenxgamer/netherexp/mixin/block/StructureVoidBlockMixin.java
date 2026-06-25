package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.StructureVoidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// this is not actually a JNE feature, I was just getting pissed off by how annoying structure voids were to deal with
// TODO: reminder to get rid of this for production
@Mixin(StructureVoidBlock.class)
public abstract class StructureVoidBlockMixin {

    @Inject(
            method = "getRenderShape",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    @Unique
    private void netherexp$makeVoidsRender(BlockState state, CallbackInfoReturnable<RenderShape> cir) {
        if (!JNEConfigImpl.COMMON.isLoaded()) return;
        if (JNEConfigs.DEVELOPER_MODE.get()) cir.setReturnValue(RenderShape.MODEL);
    }
}
