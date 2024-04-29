package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(
            method = "getArmPose",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$getArmPose(AbstractClientPlayer abstractClientPlayer, InteractionHand interactionHand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (abstractClientPlayer.getItemInHand(interactionHand).is(JNETags.Items.SHOTGUNS)) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
