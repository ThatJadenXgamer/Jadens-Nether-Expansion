package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Stray.class)
public abstract class StrayMixin {

    @Inject(
            method = "checkStraySpawnRules",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$modfyStraySpawnRules(EntityType<Stray> stray, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        if (level.getBiome(pos).is(JNETags.Biomes.OVERRIDE_STRAY_SPAWN_RULES)) cir.setReturnValue(Monster.checkMonsterSpawnRules(stray, level, spawnType, pos, random));
    }
}