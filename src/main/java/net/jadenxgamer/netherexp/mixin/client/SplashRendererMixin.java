package net.jadenxgamer.netherexp.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin {

    @Unique
    private static final int JNE_COLOR = 0xFFE8391F;

    @WrapOperation(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V")
    )
    private void netherexp$renderJNEColorText(GuiGraphics instance, Font font, String text, int x, int y, int color, Operation<Void> original) {
        // makes the splash text from JNE appear in our very cool Nether Expansion Red color
        if (text != null && text.startsWith("[JNE]")) {
            var newColor = JNEConfigs.RED_SPLASH_TEXT.get() ? JNE_COLOR : color;
            original.call(instance, font, text.substring(5), x, y, newColor);
        } else {
            original.call(instance, font, text, x, y, color);
        }
    }
}
