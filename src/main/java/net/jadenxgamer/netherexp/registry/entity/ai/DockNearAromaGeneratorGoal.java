package net.jadenxgamer.netherexp.registry.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class DockNearAromaGeneratorGoal extends MoveToTargetPosGoal {
    private final StriderEntity strider;

    public DockNearAromaGeneratorGoal(StriderEntity entity, double speed, int range, int maxYDifference) {
        super(entity, speed, range, maxYDifference);
        strider = entity;
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return 4.0;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isOf(Blocks.DIAMOND_BLOCK);
    }

    @Override
    protected BlockPos getTargetPos() {
        return this.targetPos;
    }

    @Override
    public boolean canStart() {
        return this.strider.isSaddled() && super.canStart();
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