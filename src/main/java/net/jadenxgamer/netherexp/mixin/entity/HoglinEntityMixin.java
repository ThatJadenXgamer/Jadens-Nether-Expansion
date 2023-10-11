package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.Hoglin;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoglinEntity.class)
public abstract class HoglinEntityMixin
extends AnimalEntity
implements Monster, Hoglin {
    protected HoglinEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    // Prevents Hoglins from spawning on these Blocks
    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockPos pos = getBlockPos();
        return !world.getBlockState(pos.down()).isIn(ModTags.Blocks.HOGLIN_CANNOT_SPAWN_ON);
    }
}
