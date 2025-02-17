package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
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

import static net.jadenxgamer.netherexp.config.JNEConfigs.ENABLE_BLACK_ICE_PARTICLES;

public class BlackIcicleBlock extends PointedDripstoneBlock {

    public BlackIcicleBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        BlockPos pos = pHit.getBlockPos();
        if (!pLevel.isClientSide && pProjectile.mayInteract(pLevel, pos) && pProjectile.getDeltaMovement().length() > 0.6) {
            pLevel.destroyBlock(pos, true);
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        if (pDirection != Direction.UP && pDirection != Direction.DOWN) {
            return pState;
        } else {
            Direction tipDir = pState.getValue(TIP_DIRECTION);
            if (tipDir == Direction.DOWN && pLevel.getBlockTicks().hasScheduledTick(pCurrentPos, this)) {
                return pState;
            } else if (pDirection == tipDir.getOpposite() && !this.canSurvive(pState, pLevel, pCurrentPos)) {
                if (tipDir == Direction.DOWN) {
                    pLevel.scheduleTick(pCurrentPos, this, 2);
                } else {
                    pLevel.scheduleTick(pCurrentPos, this, 1);
                }

                return pState;
            } else {
                boolean merged = pState.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness thickness = calculateIcicleThickness(pLevel, pCurrentPos, tipDir, merged);
                return pState.setValue(THICKNESS, thickness);
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (state.getValue(THICKNESS) != DripstoneThickness.TIP) return;
        if (ENABLE_BLACK_ICE_PARTICLES.get() && random.nextInt(10) == 0) {
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
            pLevel.playSound(null, pPos, JNESoundEvents.BLOCK_BLACK_ICE_BREAK.get(), SoundSource.BLOCKS, 0.7f, 1.5f);
        }

        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, new AABB(pPos).inflate(1.0));

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity && pFallingBlock.getBlockState().getValue(THICKNESS) == DripstoneThickness.TIP) {
                livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + JNEConfigs.BLACK_ICICLE_FREEZE_TICKS.get());
            }
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity pEntity) {
        if (pEntity instanceof LivingEntity) {
            pEntity.setTicksFrozen(pEntity.getTicksFrozen() + 5);
        }
        return super.getFallDamageSource(pEntity);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isValidIciclePlacement(pLevel, pPos, pState.getValue(TIP_DIRECTION));
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (isStalagmite(pState) && !this.canSurvive(pState, pLevel, pPos)) {
            pLevel.destroyBlock(pPos, true);
        } else {
            spawnFallingIcicle(pState, pLevel, pPos);
        }
    }

    private static boolean isValidIciclePlacement(LevelReader level, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState blockState = level.getBlockState(blockPos);
        return canSupportCenter(level, blockPos, direction) || isIcicleWithDirection(blockState, direction);
    }

    private static void spawnFallingIcicle(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        BlockPos.MutableBlockPos mutablePos = pPos.mutable();

        for(BlockState icicle = pState; isStalactite(icicle); icicle = pLevel.getBlockState(mutablePos)) {
            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(pLevel, mutablePos, icicle);
            if (isTip(icicle, true)) {
                int max = Math.max(1 + pPos.getY() - mutablePos.getY(), 6);
                float f = (float) max;
                fallingBlock.setHurtsEntities(f, 15);
                break;
            }

            mutablePos.move(Direction.DOWN);
        }
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.011377778F) {
            if (isStalactiteStartPos(pState, pLevel, pPos)) {
                growIcicleIfPossible(pState, pLevel, pPos, Direction.DOWN);
            } else if (isStalagmiteStartPos(pState, pLevel, pPos)) {
                growIcicleIfPossible(pState, pLevel, pPos, Direction.UP);
            }
        }
    }

    public static void growIcicleIfPossible(BlockState pState, ServerLevel pLevel, BlockPos pPos, Direction growthDirection) {
        BlockPos tip = findTip(pState, pLevel, pPos, 7, false);
        if (tip != null) {
            BlockState tipState = pLevel.getBlockState(tip);
            if (canTipGrow(tipState, pLevel, tip) && tipState.getValue(WATERLOGGED)) {
                grow(pLevel, tip, growthDirection);
            }
        }
    }

    private static void grow(ServerLevel pServer, BlockPos pPos, Direction pDirection) {
        BlockPos relative = pPos.relative(pDirection);
        BlockState relativeState = pServer.getBlockState(relative);
        if (isUnmergedTipWithDirection(relativeState, pDirection.getOpposite())) {
            createMergedTips(relativeState, pServer, relative);
        } else if (relativeState.isAir() || relativeState.is(Blocks.WATER)) {
            crateIcicle(pServer, relative, pDirection, DripstoneThickness.TIP);
        }
    }

    private static void crateIcicle(LevelAccessor pLevel, BlockPos pPos, Direction pDirection, DripstoneThickness pThickness) {
        BlockState state = JNEBlocks.BLACK_ICICLE.get().defaultBlockState().setValue(TIP_DIRECTION, pDirection).setValue(THICKNESS, pThickness).setValue(WATERLOGGED, pLevel.getFluidState(pPos).getType() == Fluids.WATER);
        pLevel.setBlock(pPos, state, 3);
    }

    private static void createMergedTips(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        BlockPos above;
        BlockPos below;
        if (pState.getValue(TIP_DIRECTION) == Direction.UP) {
            below = pPos;
            above = pPos.above();
        } else {
            above = pPos;
            below = pPos.below();
        }

        crateIcicle(pLevel, above, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        crateIcicle(pLevel, below, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    @Nullable
    private static BlockPos findTip(BlockState pState, LevelAccessor pLevel, BlockPos pPos, int pMaxIterations, boolean pIsTipMerge) {
        if (isTip(pState, pIsTipMerge)) {
            return pPos;
        } else {
            Direction direction = pState.getValue(TIP_DIRECTION);
            BiPredicate<BlockPos, BlockState> biPredicate = (pos, state) -> state.is(Blocks.POINTED_DRIPSTONE) && state.getValue(TIP_DIRECTION) == direction;
            return findBlockVertical(pLevel, pPos, direction.getAxisDirection(), biPredicate, (p) -> isTip(p, pIsTipMerge), pMaxIterations).orElse(null);
        }
    }

    private static boolean canTipGrow(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        Direction direction = pState.getValue(TIP_DIRECTION);
        BlockPos relative = pPos.relative(direction);
        BlockState relativeState = pLevel.getBlockState(relative);
        if (!relativeState.getFluidState().isEmpty()) {
            return false;
        } else {
            return relativeState.isAir() || relativeState.getFluidState().is(Fluids.WATER) || isUnmergedTipWithDirection(relativeState, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> findBlockVertical(LevelAccessor pLevel, BlockPos pPos, Direction.AxisDirection pAxis, BiPredicate<BlockPos, BlockState> pPositionalStatePredicate, Predicate<BlockState> pStatePredicate, int pMaxIterations) {
        Direction direction = Direction.get(pAxis, Direction.Axis.Y);
        BlockPos.MutableBlockPos mutablePos = pPos.mutable();

        for(int i = 1; i < pMaxIterations; ++i) {
            mutablePos.move(direction);
            BlockState mutableState = pLevel.getBlockState(mutablePos);
            if (pStatePredicate.test(mutableState)) {
                return Optional.of(mutablePos.immutable());
            }

            if (pLevel.isOutsideBuildHeight(mutablePos.getY()) || !pPositionalStatePredicate.test(mutablePos, mutableState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Direction opposite = pContext.getNearestLookingVerticalDirection().getOpposite();
        Direction direction = calculateTipDirection(level, pos, opposite);
        if (direction == null) {
            return null;
        } else {
            boolean merge = !pContext.isSecondaryUseActive();
            DripstoneThickness thickness = calculateIcicleThickness(level, pos, direction, merge);
            return thickness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
        }
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader pLevel, BlockPos pPos, Direction pDir) {
        Direction direction;
        if (isValidIciclePlacement(pLevel, pPos, pDir)) {
            direction = pDir;
        } else {
            if (!isValidIciclePlacement(pLevel, pPos, pDir.getOpposite())) {
                return null;
            }

            direction = pDir.getOpposite();
        }

        return direction;
    }

    private static boolean isTip(BlockState pState, boolean pIsTipMerge) {
        if (!pState.is(JNEBlocks.BLACK_ICICLE.get())) {
            return false;
        } else {
            DripstoneThickness thickness = pState.getValue(THICKNESS);
            return thickness == DripstoneThickness.TIP || pIsTipMerge && thickness == DripstoneThickness.TIP_MERGE;
        }
    }

    private static boolean isUnmergedTipWithDirection(BlockState pState, Direction pDir) {
        return isTip(pState, false) && pState.getValue(TIP_DIRECTION) == pDir;
    }

    private static boolean isStalactiteStartPos(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isStalactite(pState) && !pLevel.getBlockState(pPos.above()).is(JNEBlocks.BLACK_ICICLE.get());
    }

    private static boolean isStalagmiteStartPos(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        BlockState belowState = pLevel.getBlockState(pPos.below());
        return belowState.isSolidRender(pLevel, pPos.below()) && !pState.getFluidState().isEmpty();
    }

    private static boolean isStalactite(BlockState pState) {
        return isIcicleWithDirection(pState, Direction.DOWN);
    }

    private static boolean isStalagmite(BlockState pState) {
        return isIcicleWithDirection(pState, Direction.UP);
    }

    private static boolean isIcicleWithDirection(BlockState pState, Direction pDir) {
        return pState.is(JNEBlocks.BLACK_ICICLE.get()) && pState.getValue(TIP_DIRECTION) == pDir;
    }
    private static DripstoneThickness calculateIcicleThickness(LevelReader pLevel, BlockPos pPos, Direction pDir, boolean pIsTipMerge) {
        Direction opposite = pDir.getOpposite();
        BlockState rPos = pLevel.getBlockState(pPos.relative(pDir));
        if (isIcicleWithDirection(rPos, opposite)) {
            return !pIsTipMerge && rPos.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isIcicleWithDirection(rPos, pDir)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness thickness = rPos.getValue(THICKNESS);
            if (thickness != DripstoneThickness.TIP && thickness != DripstoneThickness.TIP_MERGE) {
                BlockState roPos = pLevel.getBlockState(pPos.relative(opposite));
                return !isIcicleWithDirection(roPos, pDir) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }
}
