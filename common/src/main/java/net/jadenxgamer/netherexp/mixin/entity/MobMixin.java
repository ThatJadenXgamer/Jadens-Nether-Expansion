package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.entity.ai.AttackTreacherousCandleGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Shadow @Final
    protected GoalSelector targetSelector;

    @Inject(
            method = "finalizeSpawn",
            at = @At(value = "HEAD")
    )
    private void netherexp$finalizeAttackTreacherousCandle(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CompoundTag compoundTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        Mob mob = ((Mob) (Object) this);
        if (mob instanceof PathfinderMob pathfinderMob) {
            targetSelector.addGoal(2, new AttackTreacherousCandleGoal(pathfinderMob, 32));
        }
    }
}
