package net.jadenxgamer.netherexp.registry.entity.ai;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.custom.TreacherousCandleBlock;
import net.jadenxgamer.netherexp.registry.block.entity.TreacherousCandleBlockEntity;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AttackTreacherousCandleGoal extends MoveToBlockGoal {
    PathfinderMob entity;
    int attackDelay = 60;

    public AttackTreacherousCandleGoal(PathfinderMob pathfinderMob, int range) {
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
        return state.is(JNEBlocks.TREACHEROUS_CANDLE.get());
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
            if (blockEntity instanceof TreacherousCandleBlockEntity treacherousCandle && !state.getValue(TreacherousCandleBlock.COMPLETED) && state.getValue(TreacherousCandleBlock.LIT)) {
                int health = treacherousCandle.getHealth();
                if (attackDelay <= 0 && !state.getValue(TreacherousCandleBlock.BROKEN)) {
                    if (health > 0) {
                        treacherousCandle.setHealth(health - 1);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    }
                    else {
                        level.setBlock(target, level.getBlockState(target).setValue(TreacherousCandleBlock.LIT, false).setValue(TreacherousCandleBlock.BROKEN, true), 2);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_BREAK_BLOCK, SoundSource.BLOCKS, 1.0f, 1.0f);
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
        return entity.getTarget() == null && !entity.getType().is(JNETags.EntityTypes.IGNORES_TREACHEROUS_CANDLE) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        BlockPos target = getMoveToTarget();
        return entity.level().getBlockState(target).is(JNEBlocks.TREACHEROUS_CANDLE.get()) && super.canContinueToUse();
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
