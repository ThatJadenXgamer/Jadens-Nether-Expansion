package net.jadenxgamer.netherexp.core.entity.ai;

import net.jadenxgamer.netherexp.core.block.CiergeOfTreacheryBlock;
import net.jadenxgamer.netherexp.core.block.entity.CiergeOfTreacheryBlockEntity;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

// garbage from 1.20.1
public class AttackCiergeOfTreacheryGoal extends MoveToBlockGoal {
    PathfinderMob entity;
    int attackDelay = 60;

    public AttackCiergeOfTreacheryGoal(PathfinderMob pathfinderMob, int range) {
        super(pathfinderMob, 1.0F, range, range);
        entity = pathfinderMob;
    }

    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(JNEBlocks.CIERGE_OF_TREACHERY.get());
    }

    @Override
    protected @NotNull BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    public void tick() {
        super.tick();
        Level level = entity.level();
        BlockPos target = getMoveToTarget();
        BlockState state = level.getBlockState(target);
        if (this.isReachedTarget()) {
            BlockEntity blockEntity = level.getBlockEntity(target);
            if (blockEntity instanceof CiergeOfTreacheryBlockEntity ciergeOfTreachery && !state.getValue(CiergeOfTreacheryBlock.COMPLETED) && state.getValue(CiergeOfTreacheryBlock.LIT)) {
                int health = ciergeOfTreachery.getHealth();
                if (attackDelay <= 0 && !state.getValue(CiergeOfTreacheryBlock.BROKEN)) {
                    if (health > 0) {
                        ciergeOfTreachery.setHealth(health - 1);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    }
                    else {
                        level.setBlock(target, level.getBlockState(target).setValue(CiergeOfTreacheryBlock.LIT, false).setValue(CiergeOfTreacheryBlock.BROKEN, true), 2);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), JNESoundEvents.CIERGE_OF_TREACHERY_DEFEAT.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 0.5f, 1.0f);
                    }
                    attackDelay = 60;
                }
                else {
                    --attackDelay;
                }
            }
        }
    }

    @Override
    public boolean canUse() {
        return entity.getTarget() == null && !entity.getType().is(JNETags.EntityTypes.IGNORES_CIERGE_OF_TREACHERY) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        BlockPos target = getMoveToTarget();
        BlockState targetState = entity.level().getBlockState(target);
        return targetState.is(JNEBlocks.CIERGE_OF_TREACHERY.get()) && !targetState.getValue(CiergeOfTreacheryBlock.COMPLETED) && targetState.getValue(CiergeOfTreacheryBlock.LIT) && super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
