package net.jadenxgamer.netherexp.client.rendering.keyframe;

import net.minecraft.util.Mth;

public class BlendAnimationState {
    private float value;

    public void update(float target, float lerpSpeed) {
        this.value = Mth.lerp(lerpSpeed, this.value, target);
    }

    public float get() {
        return this.value;
    }
}