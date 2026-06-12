package net.jadenxgamer.netherexp.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Shadow
    protected abstract boolean canPlace(BlockPlaceContext context, BlockState state);

    @Inject(
            method = "getPlacementState",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$modifyPlacementState(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        Player player = context.getPlayer();
        if (player != null && player.hasEffect(JNEMobEffects.BETRAYED)) {
            BlockState hazeBlock = JNEBlocks.HAZE_BLOCK.get().getStateForPlacement(context);
            cir.setReturnValue(hazeBlock != null && this.canPlace(context, hazeBlock) ? hazeBlock : null);
        }
    }

    @WrapOperation(
            method = "place",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;consume(ILnet/minecraft/world/entity/LivingEntity;)V")
    )
    private void netherexp$preventShrinkWhenBetrayed(ItemStack instance, int amount, LivingEntity entity, Operation<Void> original) {
        if (entity.hasEffect(JNEMobEffects.BETRAYED)) amount = 0;
        original.call(instance, amount, entity);
    }
}
