package net.jadenxgamer.netherexp.client.rendering;

import net.minecraft.client.renderer.ShaderInstance;

public class JNERenderStateShard {

    private static ShaderInstance renderTypeNoShadeEntityCutout;
    private static ShaderInstance renderTypeNoShadeEntityCutoutNoCull;
    private static ShaderInstance renderTypeEntityAdditive;
    private static ShaderInstance renderTypeFireOverlay;


    /////////////
    // GETTERS //
    /////////////

    public static ShaderInstance getRenderTypeNoShadeEntityCutout() {
        return renderTypeNoShadeEntityCutout;
    }
    public static ShaderInstance getRenderTypeNoShadeEntityCutoutNoCull() {
        return renderTypeNoShadeEntityCutoutNoCull;
    }
    public static ShaderInstance getRenderTypeEntityAdditive() {
        return renderTypeEntityAdditive;
    }
    public static ShaderInstance getRenderTypeFireOverlay() {
        return renderTypeFireOverlay;
    }

    /////////////
    // SETTERS //
    /////////////

    public static void setRenderTypeNoShadeEntityCutout(ShaderInstance instance) {
        JNERenderStateShard.renderTypeNoShadeEntityCutout = instance;
    }
    public static void setRenderTypeNoShadeEntityCutoutNoCull(ShaderInstance instance) {
        JNERenderStateShard.renderTypeNoShadeEntityCutoutNoCull = instance;
    }
    public static void setRenderTypeEntityAdditive(ShaderInstance instance) {
        JNERenderStateShard.renderTypeEntityAdditive = instance;
    }
    public static void setRenderTypeFireOverlay(ShaderInstance instance) {
        JNERenderStateShard.renderTypeFireOverlay = instance;
    }
}
