package net.jadenxgamer.netherexp.registry.entity.ai;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.TreacherousCandleBlock;
import net.jadenxgamer.netherexp.registry.block.entity.TreacherousCandleBlockEntity;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AttackTreacherousCandleGoal extends MoveToBlockGoal {
    private final PathfinderMob entity;
    private int attackDelay = 60;
    private static final SoundEvent WITHER_BREAK_BLOCK = SoundEvents.WITHER_BREAK_BLOCK;
    private static final SoundEvent TREACHEROUS_CANDLE_DEFEAT = JNESoundEvents.TREACHEROUS_CANDLE_DEFEAT.get();

    public AttackTreacherousCandleGoal(PathfinderMob pathfinderMob) {
        super(pathfinderMob, 1.0F, 6, 6);
        this.entity = pathfinderMob;
    }

    @Override
    public double acceptedDistance() {
        return 2.0;
    }

    @Override
    protected boolean isValidTarget(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return JNEBlocks.TREACHEROUS_CANDLE.get().is(state);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = entity.level();
        BlockPos target = getMoveToTarget();
        BlockState state = level.getBlockState(target);

        if (this.isReachedTarget()) {
            TreacherousCandleBlockEntity candle = getCandleBlockEntity(state);
            if (candle != null && !state.getValue(TreacherousCandleBlock.COMPLETED) && state.getValue(TreacherousCandleBlock.LIT)) {
                int health = candle.getHealth();
                if (attackDelay <= 0 && !state.getValue(TreacherousCandleBlock.BROKEN)) {
                    if (health > 0) {
                        candle.setHealth(health - 1);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    } else {
                        level.setBlock(target, level.getBlockState(target).setValue(TreacherousCandleBlock.LIT, false).setValue(TreacherousCandleBlock.BROKEN, true), 2);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), TREACHEROUS_CANDLE_DEFEAT, SoundSource.PLAYERS, 1.0f, 1.0f);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 0.5f, 1.0f);
                    }
                    attackDelay = 60;
                } else {
                    --attackDelay;
                }
            }
        }
    }

    @Override
    public boolean canUse() {
        return entity.getTarget() == null && !entity.getType().is(JNETags.EntityTypes.IGNORES_TREACHEROUS_CANDLE) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        BlockPos target = getMoveToTarget();
        BlockState targetState = entity.level().getBlockState(target);
        return targetState.is(JNEBlocks.TREACHEROUS_CANDLE.get()) && !targetState.getValue(TreacherousCandleBlock.COMPLETED) && targetState.getValue(TreacherousCandleBlock.LIT) && super.canContinueToUse();
    }

    private TreacherousCandleBlockEntity getCandleBlockEntity(BlockState state) {
        return (TreacherousCandleBlockEntity) state.getBlockEntity();
    }
}
