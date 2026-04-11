package net.jadenxgamer.netherexp.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.SparkParticleBehavior;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.awt.*;
import java.util.Optional;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

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

    public static void netherFogParticle(Level level, RandomSource random, double x, double y, double z, Color color) {
        double motion = NETHER_MIST_MOTION_MULTIPLIER.get();
        float scale = (float) NETHER_MIST_SCALE.getAsDouble();
        float opacity = NETHER_MIST_OPACITY.get().floatValue();
        float startSize = Mth.randomBetween(random, (scale - 2.0f), scale);
        float endSize = Mth.randomBetween(random, (scale + 1.0f), (scale + 3.0f));
        float transparency = Mth.randomBetween(random, (opacity - 0.2f), (opacity + 1.0f));
        Vec3 direction = new Vec3(0.0, 1.0, 0.0);
        WorldParticleBuilder.create(JNEParticleTypes.NETHER_FOG.get())
                .setFullBrightLighting()
                .enableNoClip()
                .setBehavior(SparkParticleBehavior.sparkBehavior().setForcedDirection(direction))
                .setForceSpawn(true)
                .setScaleData(GenericParticleData.create(startSize, endSize).build())
                .setTransparencyData(GenericParticleData.create(0.02f, 3.0f, 0f).build())
                .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE.withDepthFade())
                .setLifetime(random.nextInt(120, 180))
                .setMotion(random.nextDouble() * motion, random.nextDouble() * motion, random.nextDouble() * motion)
                .setColorData(ColorParticleData.create(color.brighter().brighter()).build())
                .spawn(level, x, y, z);
    }
}
