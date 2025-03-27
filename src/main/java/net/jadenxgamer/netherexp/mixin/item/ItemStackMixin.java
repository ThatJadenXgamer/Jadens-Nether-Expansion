package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.JNEForgeConfigs;
import net.jadenxgamer.netherexp.registry.item.custom.AntidoteItem;
import net.jadenxgamer.netherexp.registry.item.custom.GrenadeAntidoteItem;
import net.jadenxgamer.netherexp.registry.item.custom.WillOWispItem;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
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

    @Shadow public abstract boolean is(Item arg);

    @Inject(
            method = "getMaxStackSize",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$setCustomMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = ((ItemStack) (Object) this);
        // changes the stack size of vanilla and some of our modded items to dynamically change stack sizes with configs
        if (this.getItem() instanceof WillOWispItem) {
            cir.setReturnValue(JNEConfigs.WILL_O_WISP_STACK_SIZE.get());
        }

        if (JNEConfigs.FORCE_DISABLE_POTION_STACK_SIZE.get()) return;
        if (this.getItem() instanceof PotionItem && !stack.is(JNETags.Items.DOESNT_MODIFY_POTION_STACK_SIZE)) {
            cir.setReturnValue(JNEConfigs.POTION_STACK_SIZE.get());
        }
        if (this.getItem() instanceof AntidoteItem && !stack.is(JNETags.Items.DOESNT_MODIFY_POTION_STACK_SIZE)) {
            cir.setReturnValue(JNEConfigs.POTION_STACK_SIZE.get());
        }
        if (this.getItem() instanceof GrenadeAntidoteItem && !stack.is(JNETags.Items.DOESNT_MODIFY_POTION_STACK_SIZE)) {
            cir.setReturnValue(JNEConfigs.POTION_STACK_SIZE.get());
        }
    }
}
