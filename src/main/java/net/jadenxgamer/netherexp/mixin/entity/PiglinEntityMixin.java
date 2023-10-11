package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin
extends AbstractPiglinEntity
implements CrossbowUser, InventoryOwner {
    public PiglinEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    // Prevents Piglins from spawning on these Blocks
    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockPos pos = getBlockPos();
        return !world.getBlockState(pos.down()).isIn(ModTags.Blocks.PIGLIN_CANNOT_SPAWN_ON);
    }
}
