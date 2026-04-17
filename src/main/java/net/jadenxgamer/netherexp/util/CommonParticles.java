package net.jadenxgamer.netherexp.util;

import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.behaviors.SparkParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class CommonParticles {

    public static final LodestoneWorldParticleType[] SMOKE_VARIANTS = {
            JNEParticleTypes.REDUX_POOF.get(),
            JNEParticleTypes.REDUX_POOF.get(),
            JNEParticleTypes.REDUX_POOF_BLOB.get(),
            JNEParticleTypes.REDUX_POOF_BLOB.get(),
            JNEParticleTypes.REDUX_POOF_STAR.get(),
    };

    public static void smokeParticle(Level level, RandomSource random, double x, double y, double z, Color start, Color end) {
        for (int i = 0; i < 4; i++) {
            LodestoneWorldParticleType particle = SMOKE_VARIANTS[random.nextInt(SMOKE_VARIANTS.length)];
            var parX = x + 0.5 + random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
            var parZ = z + 0.5 + random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
            WorldParticleBuilder.create(particle)
                    .setNaturalLighting()
                    .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.33f, 0.53f)).build())
                    .setTransparencyData(GenericParticleData.create(1.0f, 0.6f).setEasing(Easing.BOUNCE_OUT).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setColorData(ColorParticleData.create(start, end).setCoefficient(1.3f).setEasing(Easing.SINE_OUT).build())
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(Mth.randomBetweenInclusive(random, 12, 27))
                    .enableNoClip()
                    .addMotion(0.0 + random.nextDouble() / 64, 0.1, 0.0 + random.nextDouble() / 64)
                    .spawn(level, parX, y + 0.25 + (random.nextDouble() / 8f), parZ);
        }
    }

    public static void emberParticle(Level level, RandomSource random, double x, double y, double z, Color color) {
        var parX = x + 0.5;
        var parZ = z + 0.5;
        WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT_COIL.get())
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.18f, 0.18f, 0.0f).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setLifetime(random.nextInt(10, 50))
                .disableNoClip()
                .setGravity(0f)
                .setColorData(ColorParticleData.create(color).build())
                .spawn(level, parX, y, parZ);
    }

    public static void brewingStandParticle(Level level, BlockPos pos, RandomSource random, double x, double y, double z) {
        var start = new Color(0xFDD328);
        var end = new Color(0x993B01);
        for (int i = 0; i < 3; i++) {
            LodestoneWorldParticleType particle = SMOKE_VARIANTS[random.nextInt(SMOKE_VARIANTS.length)];
            WorldParticleBuilder.create(particle)
                    .setNaturalLighting()
                    .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.23f, 0.29f)).build())
                    .setTransparencyData(GenericParticleData.create(0.4f, 0.2f).setEasing(Easing.BOUNCE_OUT).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setColorData(ColorParticleData.create(start, end).setCoefficient(0.6f).setEasing(Easing.SINE_OUT).build())
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(Mth.randomBetweenInclusive(random, 12, 27))
                    .enableNoClip()
                    .addMotion(0.0 + random.nextDouble() / 64, 0.025, 0.0 + random.nextDouble() / 64)
                    .spawn(level, x, y, z);
        }

        var parX = pos.getX() + 0.5 + random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
        var parZ = pos.getZ() + 0.5 + random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1);
        WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.18f, 0.0f).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setLifetime(random.nextInt(5, 15))
                .disableNoClip()
                .setGravity(0f)
                .setColorData(ColorParticleData.create(start).build())
                .setMotion(0.0, 0.08, 0.0)
                .spawn(level, parX, y, parZ);
    }

    public static void potionConsumeParticle(Level level, RandomSource random, LivingEntity entity, Color color) {
        Vec3 direction = new Vec3(0.0, 1.0, 0.0);
        int delay = 0;
        var x = entity.getX();
        var y = entity.getY();
        var z = entity.getZ();
        for (int i = 0; i < 3; i++) {
            WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL)
                    .setFullBrightLighting()
                    .setBehavior(DirectionalParticleBehavior.directional(direction))
                    .setScaleData(GenericParticleData.create(1.39f).setEasing(Easing.SINE_IN_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.25f, 0.0f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(10)
                    .setLifeDelay(delay)
                    .addTickActor(actor -> {
                        actor.setPos(entity.getX(), actor.getY(), entity.getZ());
                    })
                    .enableNoClip()
                    .addMotion(0.0, 0.26, 0.0)
                    .spawn(level, x, y - 0.05, z);
            delay += 3;
        }

        for (int i = 0; i < 20; i++) {
            var parX = x + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
            var parZ = z + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
            WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                    .setScaleData(GenericParticleData.create(0.38f, 0.0f).build())
                    .setTransparencyData(GenericParticleData.create(0.8f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setLifetime(random.nextInt(20))
                    .disableNoClip()
                    .setLifeDelay(random.nextInt(0, 5))
                    .setGravity(0f)
                    .setColorData(ColorParticleData.create(color).build())
                    .setMotion(0.0, 0.26, 0.0)
                    .spawn(level, parX, y, parZ);
        }
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
