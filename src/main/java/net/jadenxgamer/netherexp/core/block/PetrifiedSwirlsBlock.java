package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.core.block.entity.PetrifiedSwirlsBlockEntity;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;

public class PetrifiedSwirlsBlock extends BaseEntityBlock {

    public static final MapCodec<PetrifiedSwirlsBlock> CODEC = simpleCodec(PetrifiedSwirlsBlock::new);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty SALTED = BooleanProperty.create("salted");
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected final VoxelShape northAabb;
    protected final VoxelShape southAabb;
    protected final VoxelShape eastAabb;
    protected final VoxelShape westAabb;
    protected final VoxelShape upAabb;
    protected final VoxelShape downAabb;

    public PetrifiedSwirlsBlock(Properties properties) {
        super(properties);
        float height = 7;
        float aabbOffset = 3;
        this.upAabb = Block.box(aabbOffset, 0.0, aabbOffset, (16.0F - aabbOffset), height, (16.0F - aabbOffset));
        this.downAabb = Block.box(aabbOffset, (16.0F - height), aabbOffset, (16.0F - aabbOffset), 16.0, (16.0F - aabbOffset));
        this.northAabb = Block.box(aabbOffset, aabbOffset, (16.0F - height), (16.0F - aabbOffset), (16.0F - aabbOffset), 16.0);
        this.southAabb = Block.box(aabbOffset, aabbOffset, 0.0, (16.0F - aabbOffset), (16.0F - aabbOffset), height);
        this.eastAabb = Block.box(0.0, aabbOffset, aabbOffset, height, (16.0F - aabbOffset), (16.0F - aabbOffset));
        this.westAabb = Block.box((16.0F - height), aabbOffset, aabbOffset, 16.0, (16.0F - aabbOffset), (16.0F - aabbOffset));
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP).setValue(SALTED, true));
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance + 2, 1.0F, entity.damageSources().stalagmite());
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != JNEEntityType.ECTO_SLAB.get()) {
            if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
                double xChange = Math.abs(entity.getX() - entity.xOld);
                double zChange = Math.abs(entity.getZ() - entity.zOld);
                if (xChange >= 0.003F || zChange >= 0.003F) entity.hurt(level.damageSources().cactus(), 1.0F);
            }
        }
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PetrifiedSwirlsBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, st, blockEntity) -> {
            if (!state.getValue(SALTED) && blockEntity instanceof PetrifiedSwirlsBlockEntity swirls) swirls.tick();
        };
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH -> this.northAabb;
            case SOUTH -> this.southAabb;
            case EAST -> this.eastAabb;
            case WEST -> this.westAabb;
            case DOWN -> this.downAabb;
            default -> this.upAabb;
        };
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        return level.getBlockState(blockpos).isFaceSturdy(level, blockpos, direction);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return this.defaultBlockState()
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER)
                .setValue(FACING, context.getClickedFace());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, SALTED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && !state.getValue(SALTED)) {
            if (level.getBlockEntity(pos) instanceof PetrifiedSwirlsBlockEntity be && be.getPetrifier() != null) level.blockEvent(pos, this, 1, 2);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide && !state.getValue(SALTED)) {
            if (level.getBlockEntity(pos) instanceof PetrifiedSwirlsBlockEntity be && be.getPetrifier() != null) level.blockEvent(pos, this, 1, 2);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {

        public static void unpetrifyParticle(Level level, RandomSource random, BlockPos pos) {
            for (Direction direction : Direction.values()) {
                BlockPos relativePos = pos.relative(direction);
                var motionX = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
                var motionY = random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
                var motionZ = random.nextDouble() / 3.6 * (random.nextBoolean() ? 1 : -1);
                if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                    WorldParticleBuilder.create(JNEParticleTypes.ECTO_SHARD.get())
                            .setNaturalLighting()
                            .setSpinData(SpinParticleData.createRandomDirection(level.random, 0.0f, 1.0f).setCoefficient(0.0f).setEasing(Easing.SINE_IN).build())
                            .setScaleData(GenericParticleData.create(0.13f).build())
                            .setTransparencyData(GenericParticleData.create(1.0f).build())
                            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                            .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                            .setLifetime(random.nextInt(80, 120))
                            .disableNoClip()
                            .addMotion(motionX, motionY, motionZ)
                            .setGravity(0.75f)
                            .spawn(level, relativePos.getX(), relativePos.getY(), relativePos.getZ());
                }
            }
        }

        public static void petrificationParticle(Level level, RandomSource random, BlockPos pos) {
            for (Direction direction : Direction.values()) {
                BlockPos relativePos = pos.relative(direction);
                Color color = new Color(0x0E4E4E);
                if (!level.getBlockState(relativePos).isSolidRender(level, relativePos)) {
                    WorldParticleBuilder.create(JNEParticleTypes.SPARKLE.get())
                            .setFullBrightLighting()
                            .setColorData(ColorParticleData.create(color).build())
                            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 2.0f).setCoefficient(1.0f).setEasing(Easing.SINE_IN).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.13f, 0.0f).setEasing(Easing.BOUNCE_IN_OUT).build())
                            .setTransparencyData(GenericParticleData.create(0.5f).build())
                            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                            .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                            .setLifetime(random.nextInt(10, 25))
                            .enableNoClip()
                            .setGravity(0.0f)
                            .setLifeDelay(random.nextInt(0, 10))
                            .setMotion(0.0, -0.04, 0.0)
                            .spawn(level, relativePos.getX(), relativePos.getY() + 1.0, relativePos.getZ());
                }
            }
        }

        public static void attractToPetrifierParticle(Level level, RandomSource random, BlockPos pos) {
            if (!level.isClientSide) return;
            Color start = new Color(0x1EBABA);
            Color end = new Color(0x0E4E4E);
            if (level.getBlockEntity(pos) instanceof PetrifiedSwirlsBlockEntity entity) {
                BlockPos targetPos = entity.getPetrifierPos();
                if (targetPos == null) return;

                double dx = targetPos.getX() + 0.5 - (pos.getX() + 0.5);
                double dy = targetPos.getY() + 0.5 - (pos.getY() + 0.5);
                double dz = targetPos.getZ() + 0.5 - (pos.getZ() + 0.5);
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                if (distance < 0.01) return;

                double speed = 0.2;
                double vx = dx / distance * speed;
                double vy = dy / distance * speed;
                double vz = dz / distance * speed;

                int lifetime = (int) (distance / speed) + random.nextInt(10, 20);
                lifetime = Math.min(lifetime, 100);


                for (int i = 0; i < random.nextInt(4, 8); i++) {
                    double velOffset = (random.nextDouble() - 0.5) * 0.01;
                    double posOffset = random.nextDouble() / 1.6 * (random.nextBoolean() ? 1 : -1);
                    WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
                            .setFullBrightLighting()
                            .setColorData(ColorParticleData.create(start, end).setEasing(Easing.SINE_IN_OUT).setCoefficient(4.0f).build())
                            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0f, 1.5f).setEasing(Easing.SINE_IN_OUT).build())
                            .setScaleData(GenericParticleData.create(0.14f, 0.23f, 0.0f).setCoefficient(1.4f).build())
                            .setTransparencyData(GenericParticleData.create(0.7f, 0.3f, 0.0f).setCoefficient(0.5f).build())
                            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                            .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                            .setLifetime(lifetime)
                            .enableNoClip()
                            .setGravity(0.0f)
                            .setLifeDelay(i * 2)
                            .setMotion(vx + velOffset, vy + velOffset, vz + velOffset)
                            .spawn(level, (pos.getX() + 0.5) + posOffset, (pos.getY() + 0.5) + posOffset, (pos.getZ() + 0.5) + posOffset);
                }

            }
        }
    }
}