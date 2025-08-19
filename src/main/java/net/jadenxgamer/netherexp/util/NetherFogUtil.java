package net.jadenxgamer.netherexp.util;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.enums.NetherFogDistance;

public class NetherFogUtil {
    public static float getFogStart() {
        if (JNEConfigs.NETHER_FOG_DISTANCE.get() == NetherFogDistance.DISABLED) {
            return -8.0f;
        } else return 0.0f;
    }

    public static float getFogEnd(float viewDistance) {
        switch (JNEConfigs.NETHER_FOG_DISTANCE.get()) {
            case FAR -> {
                return viewDistance * 1.5f;
            }
            case DISABLED -> {
                return 1_000_000.0f;
            }
        }
        return viewDistance;
    }
}
