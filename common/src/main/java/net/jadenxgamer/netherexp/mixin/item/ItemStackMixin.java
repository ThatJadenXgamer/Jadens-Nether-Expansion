package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.item.custom.AntidoteItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @Inject(
            method = "getMaxStackSize",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$setCustomMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        if (this.getItem() instanceof PotionItem) {
            cir.setReturnValue(16);
        }
    }
}
