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
public class LightTextureMixinOld {

    @Shadow @Final private Minecraft minecraft;

    @Unique
    private boolean hasBetrayedEffect() {
        return minecraft.player != null && minecraft.player.hasEffect(JNEMobEffects.BETRAYED.get());
    }

    @Unique
    private long lastFrameTime = -1L;
    @Unique
    private float lerpFactor = 0.0f;

    @Unique
    private Vector3f originalLightColor = new Vector3f(1.0f, 1.0f, 1.0f); // Default light color (white)
    @Unique
    private Vector3f betrayedLightColor = new Vector3f(1.0f, 0.2f, 0.2f); // Betrayed effect light color (red)

    @ModifyArgs(
            method = "updateLightTexture",
            at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;lerp(Lorg/joml/Vector3fc;F)Lorg/joml/Vector3f;", ordinal = 2)
    )
    private void netherexp$updateLightTexture(Args args) {
        long currentTime = System.nanoTime();
        if (lastFrameTime == -1L) {
            lastFrameTime = currentTime;
        }

        float deltaTime = (currentTime - lastFrameTime) / 1_000_000_000.0f;
        lastFrameTime = currentTime;

        if (hasBetrayedEffect()) {
            if (lerpFactor < 1.0) {
                this.lerpFactor = Math.min(1.0f, this.lerpFactor + 2.0f * deltaTime);
            }

            Vector3f lerpedColor = new Vector3f();
            lerpedColor.lerp(this.betrayedLightColor, this.lerpFactor, this.originalLightColor);

            args.set(0, lerpedColor);
            args.set(1, 0.15F);
        }
        else {
            if (lerpFactor > 0.0) {
                this.lerpFactor = Math.min(1.0f, this.lerpFactor - 2.0f * deltaTime);
            }
        }
    }
}
