package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(
            method = "getPlacementState",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void preventPlacementWithBetrayed(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<BlockState> cir) {
        Player player = blockPlaceContext.getPlayer();
        if (player != null && player.hasEffect(JNEMobEffects.BETRAYED.get())) {
            cir.setReturnValue(null);
        }
    }
}
