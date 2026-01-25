package net.jadenxgamer.netherexp.core.fluid;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.SparkBehaviorComponent;

import java.awt.*;
import java.util.Optional;

public class EctoplasmLiquidBlock extends LiquidBlock {
    public EctoplasmLiquidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!JNEConfigs.ECTOPLASM_FREEZES.get()) return;
        if (entity instanceof LivingEntity living) {
            if (level.isClientSide || living.isInPowderSnow || living.isDeadOrDying() || !entity.canFreeze()) return;

            living.setTicksFrozen(Math.min(entity.getTicksFrozen() + 3, 200));
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (JNEConfigs.ECTOPLASM_PARTICLES.get()) {
            BlockPos abovePos = pos.above();
            double x = (double) pos.getX() + random.nextDouble();
            double y = (double) pos.getY() + 1.0;
            double z = (double) pos.getZ() + random.nextDouble();
            if (state.getFluidState().isSource() && !level.getBlockState(abovePos).isSolidRender(level, abovePos)) {
                if (random.nextInt(55) == 0) rayParticle(level, random, x, y, z);
            }
            if (random.nextInt(28) == 0) ectoplasmParticle(level, random, x, y, z);
        }
        if (JNEConfigs.ECTOPLASM_SOUNDS.get() && random.nextInt(600) == 0) level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.ECTOPLASM_WHISPERING.get(), SoundSource.BLOCKS, 0.3f, 1.0f, false);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(JNESoundEvents.BUCKET_FILL_ECTOPLASM.get());
    }

    private void rayParticle(Level level, RandomSource random, double x, double y,double z) {
        Minecraft client = Minecraft.getInstance();
        Camera camera = client.gameRenderer.getMainCamera();
        Vec3 direction = new Vec3(0.0, 1.0, 0.0);
        WorldParticleBuilder.create(JNEParticleTypes.ECTOPLASM_RAYS.get())
                .setFullBrightLighting()
                .setScaleData(GenericParticleData.create(4.8f).build())
                .setBehavior(new SparkBehaviorComponent().setForcedDirection(direction))
                .setTransparencyData(GenericParticleData.create(0.02f, 1f, 0f).build())
                .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                .setLifetime(random.nextInt(120, 180))
                .disableNoClip()
                .spawn(level, x, y, z);
    }

    private void ectoplasmParticle(Level level, RandomSource random, double x, double y,double z) {
        WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.18f).build())
                .setTransparencyData(GenericParticleData.create(1, 0).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setLifetime(random.nextInt(60, 90))
                .disableNoClip()
                .setGravityStrength(0f)
                .setColorData(ColorParticleData.create(new Color(0x3EFCFF)).build())
                .setMotion(0.0, 0.04, 0.0)
                .spawn(level, x, y, z);
    }
}
