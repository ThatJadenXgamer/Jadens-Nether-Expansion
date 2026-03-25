package net.jadenxgamer.netherexp.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.jadenxgamer.netherexp.core.entity.Vessel;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;

public class ParticleHelper {

    public static final LodestoneWorldParticleType[] SMOKE_VARIANTS = {
            JNEParticleTypes.REDUX_POOF.get(),
            JNEParticleTypes.REDUX_POOF.get(),
            JNEParticleTypes.REDUX_POOF_BLOB.get(),
            JNEParticleTypes.REDUX_POOF_BLOB.get(),
            JNEParticleTypes.REDUX_POOF_STAR.get(),
    };

    public static void surroundBlockParticle(Level level, BlockPos pos, SimpleParticleType particle) {
        RandomSource random = level.random;
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            BlockPos relativePos = pos.relative(direction);
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                Direction.Axis axis = direction.getAxis();
                double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
                double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
                double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
                level.addParticle(particle, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, 0.0, 0.0, 0.0);
            }
        }
    }

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

    public static Vec3 calculateBoneWorldPosition(Vessel entity, float partialTicks, ModelPart... bones) {
        PoseStack poseStack = new PoseStack();

        float yBodyRot = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - yBodyRot));

        poseStack.scale(-1.0f, -1.0f, 1.0f);
        poseStack.translate(0.0f, -1.501f, 0.0f);

        for (ModelPart bone : bones) {
            bone.translateAndRotate(poseStack);
        }

        Vector3f offset = poseStack.last().pose().transformPosition(new Vector3f(0.0f, 0.0f, 0.0f));

        double x = Mth.lerp(partialTicks, entity.xo, entity.getX());
        double y = Mth.lerp(partialTicks, entity.yo, entity.getY());
        double z = Mth.lerp(partialTicks, entity.zo, entity.getZ());

        return new Vec3(x + offset.x(), y + offset.y(), z + offset.z());
    }
}
