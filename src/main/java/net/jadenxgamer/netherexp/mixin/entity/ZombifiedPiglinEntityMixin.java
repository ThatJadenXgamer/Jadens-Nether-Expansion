package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombifiedPiglinEntityMixin extends ZombieEntity {
    public ZombifiedPiglinEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    // Prevents Zombified Piglins from spawning on these Blocks
    @Inject(
            method = "canSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$changeCanSpawn(EntityType<PiglinEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockState(pos.down()).isIn(ModTags.Blocks.ZOMBIFIED_PIGLIN_CANNOT_SPAWN_ON)) {
            cir.setReturnValue(false);
        }
    }
}
