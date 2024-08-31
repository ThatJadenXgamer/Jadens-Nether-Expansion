package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.JNEForgeConfigs;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WitherSkeleton.class)
public abstract class WitherSkeletonMixin extends AbstractSkeleton {
    protected WitherSkeletonMixin(EntityType<? extends AbstractSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void createWitherRose(@Nullable LivingEntity adversary) {
        BlockState floor = this.getBlockStateOn();
        BlockPos floorPos = this.blockPosition().below();
        if (floor.is(JNETags.Blocks.FOSSIL_ORE_CONVERTIBLE) && JNEConfigs.WITHER_SKELETON_FOSSILIZATION.get()) {
            level().setBlock(floorPos, JNEBlocks.FOSSIL_FUEL_ORE.get().defaultBlockState(), 2);
        }
        super.createWitherRose(adversary);
    }
}

