package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.elysium_api.api.extensions.IElysiumBlockExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

import java.awt.*;

import static net.jadenxgamer.netherexp.util.CommonParticles.SMOKE_VARIANTS;

public class MagmaCreamBlock extends HalfTransparentBlock implements IElysiumBlockExtension {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    private static final double PARTICLE_SPEED_THRESHOLD = 0.4;

    public MagmaCreamBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        var face = hitResult.getDirection();
        var property = getPropertyForFace(face);
        var isFaceSlick = state.getValue(property);

        if (stack.canPerformAction(ItemAbilities.AXE_SCRAPE) && isFaceSlick) {
            level.setBlock(pos, state.setValue(property, false), Block.UPDATE_ALL);
            level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            if (level.isClientSide()) Client.faceParticle(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), face);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else if (stack.canPerformAction(ItemAbilities.FIRESTARTER_LIGHT) && !isFaceSlick) {
            level.setBlock(pos, state.setValue(property, true), Block.UPDATE_ALL);
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            if (level.isClientSide()) Client.faceParticle(level, pos, ParticleTypes.SMALL_FLAME, face);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(UP) ? 1.0f / 0.91f : super.getFriction(state, level, pos, entity);
    }

    @Override
    public boolean canStickToFace(BlockState selfState, BlockState otherState, Direction selfFace, Direction otherFace) {
        if (selfState.getBlock() == this && otherState.getBlock() == this) return true;
        return !selfState.getValue(getPropertyForFace(selfFace));
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(UP) || entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        } else entity.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (state.getValue(UP)) {
            double horizontalSpeed = Math.sqrt(entity.getDeltaMovement().x * entity.getDeltaMovement().x + entity.getDeltaMovement().z * entity.getDeltaMovement().z);
            if (level.isClientSide() && horizontalSpeed >= PARTICLE_SPEED_THRESHOLD) {
                Client.particle(level, level.random, entity.getRandomX(0.5), entity.blockPosition().getY(), entity.getRandomZ(0.5));
                for (int i = 0; i < 2; i++) level.addParticle(ParticleTypes.SMALL_FLAME, entity.getRandomX(0.5), entity.blockPosition().getY() + 0.2, entity.getRandomZ(0.5), 0.0f, 0.08f, 0.0f);
            }
        } else {
            double d0 = Math.abs(entity.getDeltaMovement().y);
            if (d0 < 0.1 && !entity.isSteppingCarefully()) {
                double d1 = 0.4 + d0 * 0.2;
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(d1, 1.0, d1));
            }
        }
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        return state.getValue(UP);
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        return state1.getValue(UP);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    private static BooleanProperty getPropertyForFace(Direction face) {
        return switch (face) {
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case UP -> UP;
            case DOWN -> DOWN;
        };
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {
        public static void faceParticle(Level level, BlockPos pos, BlockParticleOption particle, Direction direction) {
            RandomSource random = level.random;
            BlockPos relativePos = pos.relative(direction);
            for (int i = 0; i < 16; i++) {
                if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                    Direction.Axis axis = direction.getAxis();
                    double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
                    double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
                    double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
                    level.addParticle(particle, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, 0.0, 0.0, 0.0);
                }
            }
        }

        public static void faceParticle(Level level, BlockPos pos, SimpleParticleType particle, Direction direction) {
            RandomSource random = level.random;
            BlockPos relativePos = pos.relative(direction);
            for (int i = 0; i < 10; i++) {
                if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                    Direction.Axis axis = direction.getAxis();
                    double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getStepX() : (double) random.nextFloat();
                    double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getStepY() : (double) random.nextFloat();
                    double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getStepZ() : (double) random.nextFloat();
                    level.addParticle(particle, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, 0.0, 0.0, 0.0);
                }
            }
        }

        public static void particle(Level level, RandomSource random, double x, double y, double z) {
            var start = new Color(0xD05E20);
            var end = new Color(0xEFE44C);
            LodestoneWorldParticleType particle = SMOKE_VARIANTS[random.nextInt(SMOKE_VARIANTS.length)];
            for (int i = 0; i < 4; i++) {
                WorldParticleBuilder.create(particle)
                        .setNaturalLighting()
                        .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.43f, 0.6f), 0.95f).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0.5f, 0.0f).setEasing(Easing.BOUNCE_OUT).build())
                        .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                        .setColorData(ColorParticleData.create(start, end).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.5f).build())
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setLifetime(Mth.randomBetweenInclusive(random, 20, 30))
                        .disableNoClip()
                        .addMotion(0.0 + random.nextDouble() / 24, 0.09, 0.0 + random.nextDouble() / 24)
                        .setGravity(0.3f)
                        .setLifeDelay(i * 3)
                        .spawn(level, x, y + 0.2, z);
            }
        }
    }
}
