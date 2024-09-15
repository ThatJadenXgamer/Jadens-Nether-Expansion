package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.entity.ai.AttackTreacherousCandleGoal;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
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
        // all mobs spawned in will be given this special target goal to go break treacherous candles assuming they care about it
        if (((Mob) (Object) this) instanceof PathfinderMob pathfinderMob && !pathfinderMob.getType().is(JNETags.EntityTypes.IGNORES_TREACHEROUS_CANDLE)) {
            targetSelector.addGoal(2, new AttackTreacherousCandleGoal(pathfinderMob, 32));
        }
    }

    @Inject(
            method = "getDefaultLootTable",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$configurableLootTables(CallbackInfoReturnable<ResourceLocation> cir) {
        // this lets me dynamically alter the drop loot tables for mobs with configs and not have to rely on LootTableModifications or just overriding the loot json
        Mob mob = ((Mob) (Object) this);
        if (mob instanceof Hoglin && JNEConfigs.HOGLIN_DROPS_HOGHAM.get()) {
            cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "entities/hoglin_hogham"));
        }
        if (mob instanceof WitherSkeleton && JNEConfigs.WITHER_SKELETON_DROPS_FOSSIL_FUEL.get()) {
            cir.setReturnValue(new ResourceLocation(NetherExp.MOD_ID, "entities/wither_skeleton_fossil_fuel"));
        }
    }
}
