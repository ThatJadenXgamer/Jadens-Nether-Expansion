package net.jadenxgamer.netherexp.mixin.brewing;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin {

    @Inject(
            method = "isValid",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$setCustomValid(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot < 3 && stack.isIn(JNETags.Items.ANTIDOTES)) {
            cir.setReturnValue(true);
        }
    }
}
