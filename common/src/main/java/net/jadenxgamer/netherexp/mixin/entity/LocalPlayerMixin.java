package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow protected int sprintTriggerTime;

    @Inject(
            method = "aiStep",
            at = @At("TAIL")
    )
    private void netherexp$aiStepModifier(CallbackInfo ci) {
        Player player = ((LocalPlayer) (Object) this);
        LocalPlayer localPlayer = ((LocalPlayer) (Object) this);
        if (player.isUsingItem() && player.getUseItem().is(JNETags.Items.DOESNT_SLOWDOWN_WHEN_USING)) {
            localPlayer.input.leftImpulse /= 0.2F;
            localPlayer.input.forwardImpulse /= 0.2F;
            this.sprintTriggerTime = Math.max(this.sprintTriggerTime, 1);
        }
    }
}
