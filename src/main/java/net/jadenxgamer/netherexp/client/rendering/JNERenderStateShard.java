package net.jadenxgamer.netherexp.client.rendering;

import net.minecraft.client.renderer.ShaderInstance;

public class JNERenderStateShard {

    private static ShaderInstance renderTypeNoShadeEntityCutout;
    private static ShaderInstance renderTypeNoShadeEntityCutoutNoCull;

    /////////////
    // GETTERS //
    /////////////

    public static ShaderInstance getRenderTypeNoShadeEntityCutout() {
        return renderTypeNoShadeEntityCutout;
    }
    public static ShaderInstance getRenderTypeNoShadeEntityCutoutNoCull() {
        return renderTypeNoShadeEntityCutoutNoCull;
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
}
