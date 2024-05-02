package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LightTexture.class)
public class LightTextureMixin {

    @Shadow @Final private Minecraft minecraft;

    @Unique
    private boolean netherexp$hasBetrayedEffect() {
        return minecraft.player != null && minecraft.player.hasEffect(JNEMobEffects.BETRAYED.get());
    }

    @ModifyArgs(
            method = "updateLightTexture",
            at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;lerp(Lorg/joml/Vector3fc;F)Lorg/joml/Vector3f;", ordinal = 2)
    )
    private void netherexp$updateLightTexture(Args args) {
        if (netherexp$hasBetrayedEffect()) {
            args.set(0, new Vector3f(1.0f, 0.2f, 0.2f));
            args.set(1, 1.1F);
        }
    }
}
