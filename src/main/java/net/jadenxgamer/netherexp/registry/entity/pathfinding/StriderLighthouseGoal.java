package net.jadenxgamer.netherexp.registry.entity.pathfinding;

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class StriderLighthouseGoal extends MoveToTargetPosGoal {

    public StriderLighthouseGoal(PathAwareEntity mob, double speed, int range) {
        super(mob, speed, range);
    }



    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return false;
    }
}
