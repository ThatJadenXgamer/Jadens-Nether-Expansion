package net.jadenxgamer.netherexp.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.awt.*;
import java.util.Optional;

public class VFXHelper {

    public static void shotgunScreenShake(Vec3 pos, float fallOffDistance, Easing falloffCurve) {
        if (!JNEConfigs.SHOTGUN_SCREENSHAKE.get()) return;
        ScreenshakeHandler.addScreenshake(
                new ScreenshakeInstance(10, 1, 0, 0,
                Easing.LINEAR, Easing.LINEAR, 1.0f, Optional.of(new ScreenshakeInstance.ScreenshakePositionData(pos, fallOffDistance, falloffCurve))
        ));
    }

    public static void explosionScreenShake(Vec3 pos, float fallOffDistance, Easing falloffCurve) {
        if (!JNEConfigs.SHOTGUN_SCREENSHAKE.get()) return;
        ScreenshakeHandler.addScreenshake(
                new ScreenshakeInstance(10, 3, 2, 1,
                Easing.LINEAR, Easing.LINEAR, 1.0f, Optional.of(new ScreenshakeInstance.ScreenshakePositionData(pos, fallOffDistance, falloffCurve))
        ));
    }

    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Color color, float size, float alpha, float partialTicks) {
        poseStack.pushPose();
        float offsetX = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float offsetY = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float offsetZ = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        poseStack.translate(-offsetX, -offsetY, -offsetZ);
        builder.setColor(color).setAlpha(alpha).renderTrail(trailPointBuilder, f -> size);
        poseStack.translate(offsetX, offsetY, offsetZ);
        poseStack.popPose();
    }
}
