package net.jadenxgamer.netherexp.util;

import java.awt.*;

//todo: move to ElysiumAPI
public class ColorHelper {
    public static Builder adjustHSB(Color original) {
        return new Builder(original);
    }

    public static class Builder {
        private final float[] hsb;
        private float hueMul = 1.0f;
        private float satMul = 1.0f;
        private float briMul = 1.0f;

        private Builder(Color color) {
            this.hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        }
        public Builder hue(float multiplier) {
            this.hueMul = multiplier;
            return this;
        }

        public Builder saturation(float multiplier) {
            this.satMul = multiplier;
            return this;
        }

        public Builder brightness(float multiplier) {
            this.briMul = multiplier;
            return this;
        }

        public Color build() {
            float h = clamp(hsb[0] * hueMul);
            float s = clamp(hsb[1] * satMul);
            float b = clamp(hsb[2] * briMul);
            return new Color(Color.HSBtoRGB(h, s, b));
        }

        private static float clamp(float val) {
            return Math.min(1.0f, Math.max(0.0f, val));
        }
    }
}