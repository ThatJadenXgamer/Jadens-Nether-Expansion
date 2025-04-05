package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Shadow protected abstract boolean canPlace(BlockPlaceContext pContext, BlockState pState);

    @Inject(
            method = "getPlacementState",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$placeHazeWithBetrayed(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        // prevents block placement for players with Betrayed Effect
        Player player = context.getPlayer();
        if (player != null && player.hasEffect(JNEMobEffects.BETRAYED.get())) {
            BlockState blockstate = JNEBlocks.HAZE_BLOCK.get().getStateForPlacement(context);
            cir.setReturnValue(blockstate != null && this.canPlace(context, blockstate) ? blockstate : null);
        }
    }

    @Inject(
            method = "place",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V", shift = At.Shift.BEFORE),
            cancellable = true
    )
    private void netherexp$preventShrinkWithBetrayed(BlockPlaceContext pContext, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        if (player != null && player.hasEffect(JNEMobEffects.BETRAYED.get())) {
            cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
