package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Hoglin.class)
public abstract class HoglinMixin extends Animal
implements Enemy, HoglinBase {
    protected HoglinMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    // Prevents Hoglins from spawning on these Blocks
    @Inject(
            method = "checkHoglinSpawnRules",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$changeCanSpawn(EntityType<Hoglin> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir) {
        if (levelAccessor.getBlockState(blockPos.below()).is(JNETags.Blocks.HOGLIN_CANNOT_SPAWN_ON)) {
            cir.setReturnValue(false);
        }
    }
}
