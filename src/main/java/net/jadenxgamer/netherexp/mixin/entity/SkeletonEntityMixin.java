package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends AbstractSkeletonEntity {
    protected SkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onKilledBy(@Nullable LivingEntity adversary) {
        BlockState floor = this.getSteppingBlockState();
        BlockPos floorPos = this.getBlockPos().down();
        if (NetherExp.getConfig().blocks.fossilOreConfigs.skeleton_fossilization && floor.isIn(JNETags.Blocks.FOSSIL_ORE_CONVERTIBLE)) {
            getWorld().setBlockState(floorPos, JNEBlocks.FOSSIL_ORE.getDefaultState());
        }
        super.onKilledBy(adversary);
    }
}
