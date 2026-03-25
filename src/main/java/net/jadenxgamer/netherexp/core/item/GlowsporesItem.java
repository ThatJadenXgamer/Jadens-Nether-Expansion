package net.jadenxgamer.netherexp.core.item;

import net.jadenxgamer.netherexp.core.block.interfaces.GlowsporesApplicable;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.util.function.Supplier;

public class GlowsporesItem extends Item {

    public final Supplier<LodestoneWorldParticleType> particle;

    public GlowsporesItem(Supplier<LodestoneWorldParticleType> particle, Properties properties) {
        super(properties);
        this.particle = particle;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        boolean passed = false;
        if (player != null && state.getBlock() instanceof GlowsporesApplicable glowsporesApplicable && stack.is(glowsporesApplicable.glowsporeOfBlock())) {
            if (!glowsporesApplicable.canSporesBeApplied(state)) return InteractionResult.FAIL;

            if (glowsporesApplicable.affectedProperty() instanceof IntegerProperty integerProperty) {
                int currentValue = state.getValue(integerProperty);
                int maxValue = integerProperty.getPossibleValues().size();
                if (currentValue < maxValue) level.setBlock(pos, state.setValue(integerProperty, currentValue + 1), Block.UPDATE_ALL);
                passed = true;
            } else if (glowsporesApplicable.affectedProperty() instanceof BooleanProperty booleanProperty) {
                boolean currentValue = state.getValue(booleanProperty);
                if (!currentValue) level.setBlock(pos, state.cycle(booleanProperty), Block.UPDATE_ALL);
                passed = true;
            }
        }

        if (passed) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            glowsporeSorroundedParticle(level, pos, this.particle.get());
            level.playSound(null, pos, JNESoundEvents.GLOWSPORES_APPLY.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useOn(context);
    }

    public static void glowsporeParticle(Level level, RandomSource random, double x, double y, double z, LodestoneWorldParticleType particle, boolean goingUp) {
        WorldParticleBuilder.create(particle)
                .setFullBrightLighting()
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.13f, 0.0f).setEasing(Easing.BOUNCE_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(1).build())
                .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setLifetime(random.nextInt(30, 40))
                .disableNoClip()
                .setGravity(goingUp ? 0.0f : 0.05f)
                .setMotion(0.0, goingUp ? 0.04 : 0.0, 0.0)
                .spawn(level, x, y, z);
    }

    public static void glowsporeSorroundedParticle(Level level, BlockPos pos, LodestoneWorldParticleType particle) {
        RandomSource random = level.random;
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            BlockPos relativePos = pos.relative(direction);
            if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                Direction.Axis axis = direction.getAxis();
                double x = pos.getX() + (axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat());
                double y = pos.getY() + (axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat());
                double z = pos.getZ() + (axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat());

                WorldParticleBuilder.create(particle)
                        .setFullBrightLighting()
                        .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.0f).setCoefficient(0.7f).setEasing(Easing.SINE_IN).build())
                        .setScaleData(GenericParticleData.create(0.05f, 0.13f, 0.0f).setEasing(Easing.BOUNCE_IN_OUT).build())
                        .setTransparencyData(GenericParticleData.create(1).build())
                        .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setLifetime(random.nextInt(30, 40))
                        .disableNoClip()
                        .setGravity(0.05f)
                        .setMotion(0.0, 0.0, 0.0)
                        .spawn(level, x, y, z);
            }
        }
    }
}
