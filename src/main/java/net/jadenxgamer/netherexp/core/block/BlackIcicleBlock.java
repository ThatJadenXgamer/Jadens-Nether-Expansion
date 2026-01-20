package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class BlackIcicleBlock extends PointedDripstoneBlock {

    public BlackIcicleBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos pos = hit.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, pos) && projectile.getDeltaMovement().length() > 0.6) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction tipDirection = state.getValue(TIP_DIRECTION);
            if (tipDirection == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this)) {
                return state;
            } else if (direction == tipDirection.getOpposite() && !this.canSurvive(state, level, pos)) {
                if (tipDirection == Direction.DOWN) {
                    level.scheduleTick(pos, this, 2);
                } else {
                    level.scheduleTick(pos, this, 1);
                }

                return state;
            } else {
                boolean merged = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness thickness = calculateIcicleThickness(level, pos, tipDirection, merged);
                return state.setValue(THICKNESS, thickness);
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(THICKNESS) != DripstoneThickness.TIP) return;

        if (JNEConfigs.BLACK_ICE_PARTICLES.get() && random.nextInt(10) == 0) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            if (!isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, random, JNEParticleTypes.BLACK_FLAKE.get());
            }
        }
    }

    @Override
    public void onBrokenAfterFall(Level pLevel, BlockPos pPos, FallingBlockEntity pFallingBlock) {
        if (!pFallingBlock.isSilent()) {
            pLevel.levelEvent(2001, pPos, BlackIcicleBlock.getId(this.defaultBlockState()));
            pLevel.playSound(null, pPos, JNESoundEvents.BLACK_ICE_BREAK.get(), SoundSource.BLOCKS, 0.7f, 1.5f);
        }

        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, new AABB(pPos).inflate(1.0));

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && pFallingBlock.getBlockState().getValue(THICKNESS) == DripstoneThickness.TIP) {
                livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + JNEConfigs.BLACK_ICE_FREEZING_TICKS.get());
            }
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.setTicksFrozen(entity.getTicksFrozen() + 5);
        }
        return super.getFallDamageSource(entity);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return isValidIciclePlacement(level, pos, state.getValue(TIP_DIRECTION));
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (fallDistance >= 1.0f) level.destroyBlock(pos, false);
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isStalagmite(state) && !this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            spawnFallingIcicle(state, level, pos);
        }
    }

    private static boolean isValidIciclePlacement(LevelReader level, BlockPos pos, Direction direction) {
        BlockPos relativePos = pos.relative(direction.getOpposite());
        BlockState relativeState = level.getBlockState(relativePos);
        return canSupportCenter(level, relativePos, direction) || isIcicleWithDirection(relativeState, direction);
    }

    private static void spawnFallingIcicle(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos mPos = pos.mutable();

        for(BlockState icicle = state; isStalactite(icicle); icicle = level.getBlockState(mPos)) {
            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, mPos, icicle);
            if (isTip(icicle, true)) {
                int max = Math.max(1 + pos.getY() - mPos.getY(), 6);
                float fallDamagePerDistance = (float) max;
                fallingBlock.setHurtsEntities(fallDamagePerDistance, 15);
                break;
            }

            mPos.move(Direction.DOWN);
        }
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() > JNEConfigs.BLACK_ICICLE_GROWTH_CHANCE.get()) return;

        if (isStalactiteStartPos(state, level, pos)) {
            growIcicleIfPossible(state, level, pos, Direction.DOWN);
        } else if (isStalagmiteStartPos(state, level, pos)) {
            growIcicleIfPossible(state, level, pos, Direction.UP);
        }
    }

    public static void growIcicleIfPossible(BlockState state, ServerLevel level, BlockPos pos, Direction growthDirection) {
        BlockPos tip = findTip(state, level, pos, 7, false);
        if (tip == null) return;

        BlockState tipState = level.getBlockState(tip);
        if (canTipGrow(tipState, level, tip) && tipState.getValue(WATERLOGGED)) {
            grow(level, tip, growthDirection);
        }
    }

    private static void grow(ServerLevel level, BlockPos pos, Direction direction) {
        BlockPos relative = pos.relative(direction);
        BlockState relativeState = level.getBlockState(relative);
        if (isUnmergedTipWithDirection(relativeState, direction.getOpposite())) {
            createMergedTips(relativeState, level, relative);
        } else if (relativeState.isAir() || relativeState.is(Blocks.WATER)) {
            crateIcicle(level, relative, direction, DripstoneThickness.TIP);
        }
        level.playSound(null, pos, JNESoundEvents.ECTOPLASM_FREEZE.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    private static void crateIcicle(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        BlockState state = JNEBlocks.BLACK_ICICLE.get().defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
        level.setBlock(pos, state, 3);
    }

    private static void createMergedTips(BlockState state, LevelAccessor level, BlockPos pos) {
        BlockPos above;
        BlockPos below;
        if (state.getValue(TIP_DIRECTION) == Direction.UP) {
            below = pos;
            above = pos.above();
        } else {
            above = pos;
            below = pos.below();
        }

        crateIcicle(level, above, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        crateIcicle(level, below, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    @Nullable
    private static BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int maxIterations, boolean isTipMerge) {
        if (isTip(state, isTipMerge)) {
            return pos;
        } else {
            Direction direction = state.getValue(TIP_DIRECTION);
            BiPredicate<BlockPos, BlockState> biPredicate = (funcPos, funcState) -> funcState.is(Blocks.POINTED_DRIPSTONE) && funcState.getValue(TIP_DIRECTION) == direction;
            return findBlockVertical(level, pos, direction.getAxisDirection(), biPredicate, (p) -> isTip(p, isTipMerge), maxIterations).orElse(null);
        }
    }

    private static boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
        Direction direction = state.getValue(TIP_DIRECTION);
        BlockPos relativePos = pos.relative(direction);
        BlockState relativeState = level.getBlockState(relativePos);
        return relativeState.isAir() || relativeState.getFluidState().is(Fluids.WATER) || isUnmergedTipWithDirection(relativeState, direction.getOpposite());
    }

    private static Optional<BlockPos> findBlockVertical(LevelAccessor level, BlockPos pos, Direction.AxisDirection axis, BiPredicate<BlockPos, BlockState> positionalStatePredicate, Predicate<BlockState> statePredicate, int maxIterations) {
        Direction direction = Direction.get(axis, Direction.Axis.Y);
        BlockPos.MutableBlockPos mPos = pos.mutable();

        for(int i = 1; i < maxIterations; ++i) {
            mPos.move(direction);
            BlockState mutableState = level.getBlockState(mPos);
            if (statePredicate.test(mutableState)) {
                return Optional.of(mPos.immutable());
            }

            if (level.isOutsideBuildHeight(mPos.getY()) || !positionalStatePredicate.test(mPos, mutableState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction opposite = context.getNearestLookingVerticalDirection().getOpposite();
        Direction direction = calculateTipDirection(level, pos, opposite);
        if (direction == null) {
            return null;
        } else {
            boolean merge = !context.isSecondaryUseActive();
            DripstoneThickness thickness = calculateIcicleThickness(level, pos, direction, merge);
            return thickness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
        }
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction direction) {
        Direction tipDirection;
        if (isValidIciclePlacement(level, pos, direction)) {
            tipDirection = direction;
        } else {
            if (!isValidIciclePlacement(level, pos, direction.getOpposite())) {
                return null;
            }

            tipDirection = direction.getOpposite();
        }

        return tipDirection;
    }

    private static boolean isTip(BlockState state, boolean isTipMerge) {
        if (!state.is(JNEBlocks.BLACK_ICICLE.get())) {
            return false;
        } else {
            DripstoneThickness thickness = state.getValue(THICKNESS);
            return thickness == DripstoneThickness.TIP || isTipMerge && thickness == DripstoneThickness.TIP_MERGE;
        }
    }

    private static boolean isUnmergedTipWithDirection(BlockState state, Direction direction) {
        return isTip(state, false) && state.getValue(TIP_DIRECTION) == direction;
    }

    private static boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
        return isStalactite(state) && !level.getBlockState(pos.above()).is(JNEBlocks.BLACK_ICICLE.get());
    }

    private static boolean isStalagmiteStartPos(BlockState state, LevelAccessor level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        return belowState.isSolidRender(level, pos.below()) && !state.getFluidState().isEmpty();
    }

    private static boolean isStalactite(BlockState state) {
        return isIcicleWithDirection(state, Direction.DOWN);
    }

    private static boolean isStalagmite(BlockState state) {
        return isIcicleWithDirection(state, Direction.UP);
    }

    private static boolean isIcicleWithDirection(BlockState state, Direction direction) {
        return state.is(JNEBlocks.BLACK_ICICLE.get()) && state.getValue(TIP_DIRECTION) == direction;
    }
    private static DripstoneThickness calculateIcicleThickness(LevelReader level, BlockPos pos, Direction direction, boolean isTipMerge) {
        Direction opposite = direction.getOpposite();
        BlockState relativePos = level.getBlockState(pos.relative(direction));
        if (isIcicleWithDirection(relativePos, opposite)) {
            return !isTipMerge && relativePos.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isIcicleWithDirection(relativePos, direction)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness thickness = relativePos.getValue(THICKNESS);
            if (thickness != DripstoneThickness.TIP && thickness != DripstoneThickness.TIP_MERGE) {
                BlockState roPos = level.getBlockState(pos.relative(opposite));
                return !isIcicleWithDirection(roPos, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }
}
