package net.jadenxgamer.netherexp.core.fluid;

import net.jadenxgamer.netherexp.core.datadriven.EctoplasmHaunting;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.SparkParticleBehavior;

import java.awt.*;
import java.util.Optional;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

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
        if (!ECTOPLASM_FREEZES.get()) return;
        if (entity instanceof LivingEntity living) {
            if (level.isClientSide || living.isInPowderSnow || living.isDeadOrDying() || !entity.canFreeze()) return;
            living.setTicksFrozen(Math.min(entity.getTicksFrozen() + 3, 200));
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide()) return;
        if (ECTOPLASM_SOUNDS.get() && random.nextInt(600) == 0)
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.ECTOPLASM_WHISPERING.get(), SoundSource.BLOCKS, 0.3f, 1.0f, false);
        if (ECTOPLASM_PARTICLES.get()) {
            BlockPos abovePos = pos.above();
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + random.nextDouble();
            if (state.getFluidState().isSource() && !level.getBlockState(abovePos).isSolidRender(level, abovePos))
                Client.rayParticle(level, random, x, y, z);
            Client.ectoplasmParticle(level, random, x, y, z);
        }
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(JNESoundEvents.BUCKET_FILL_ECTOPLASM.get());
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide()) hauntNeighbors(level, pos);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (!level.isClientSide()) hauntNeighbors(level, pos);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void hauntNeighbors(Level level, BlockPos pos) {
        Registry<EctoplasmHaunting> registry = level.registryAccess().registryOrThrow(JNERegistries.Keys.ECTOPLASM_HAUNTING);
        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.relative(direction);
            BlockState currentState = level.getBlockState(targetPos);
            if (!currentState.isAir()) {
                FluidType fluid = level.getFluidState(targetPos).getFluidType();
                if (fluid == NeoForgeMod.WATER_TYPE.value()) {
                    level.setBlock(targetPos, JNEBlocks.BLACK_ICE.get().defaultBlockState(), Block.UPDATE_ALL);
                    level.playSound(null, pos, JNESoundEvents.ECTOPLASM_FREEZE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    continue;
                }
                registry.stream()
                        .filter(e -> e.target().contains(currentState.getBlockHolder()))
                        .findFirst()
                        .map(EctoplasmHaunting::haunted)
                        .map(BuiltInRegistries.BLOCK::get)
                        .filter(hauntedBlock -> hauntedBlock != Blocks.AIR)
                        .ifPresent(hauntedBlock -> {
                            BlockState hauntedState = hauntedBlock.defaultBlockState();
                            BlockState newState = hauntedState;
                            for (Property property : currentState.getProperties()) {
                                if (hauntedState.hasProperty(property))
                                    newState = newState.setValue(property, currentState.getValue(property));
                            }
                            if (!newState.equals(currentState)) {
                                level.setBlock(targetPos, newState, Block.UPDATE_ALL);
                                level.playSound(null, pos, JNESoundEvents.ECTOPLASM_FREEZE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                            }
                        });
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {
        public static void rayParticle(Level level, RandomSource random, double x, double y, double z) {
            if (random.nextInt(55) != 0) return;
            Vec3 direction = new Vec3(0.0, 1.0, 0.0);
            WorldParticleBuilder.create(JNEParticleTypes.ECTOPLASM_RAYS.get())
                    .setFullBrightLighting()
                    .setScaleData(GenericParticleData.create(4.8f).build())
                    .setBehavior(SparkParticleBehavior.sparkBehavior().setForcedDirection(direction))
                    .setTransparencyData(GenericParticleData.create(0.02f, 1f, 0f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .setLifetime(random.nextInt(120, 180))
                    .disableNoClip()
                    .spawn(level, x, y, z);
        }

        public static void ectoplasmParticle(Level level, RandomSource random, double x, double y, double z) {
            if (random.nextInt(28) != 0) return;
            WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                    .setFullBrightLighting()
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                    .setScaleData(GenericParticleData.create(0.0f, 0.18f).setCoefficient(2.4f).build())
                    .setTransparencyData(GenericParticleData.create(1, 0).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setLifetime(random.nextInt(60, 90))
                    .disableNoClip()
                    .setGravity(0f)
                    .setColorData(ColorParticleData.create(new Color(0x3EFCFF)).build())
                    .setMotion(0.0, 0.04, 0.0)
                    .spawn(level, x, y, z);
        }
    }
}