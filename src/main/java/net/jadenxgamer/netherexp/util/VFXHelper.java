package net.jadenxgamer.netherexp.util;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.util.Optional;

public class VFXHelper {

    public static void shotgunScreenShake(Vec3 pos, float fallOffDistance, Easing falloffCurve) {
        if (!JNEConfigs.SHOTGUN_SCREENSHAKE.get()) return;
        ScreenshakeHandler.addScreenshake(
                new ScreenshakeInstance(10, 1, 0, 0,
                Easing.LINEAR, Easing.LINEAR, 1.0f, Optional.of(new ScreenshakeInstance.ScreenshakePositionData(pos, fallOffDistance, falloffCurve))
        ));
    }
}
