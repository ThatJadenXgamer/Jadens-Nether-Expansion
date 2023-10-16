package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity implements Monster {
    protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At(value = "TAIL")
    )
    private void conversionTick(CallbackInfo ci) {
        if (!this.getWorld().isClient && this.isAlive() && !this.isAiDisabled()) {
            if (this.getBlockStateAtPos().isOf(ModBlocks.MAGMA_CREAM_BLOCK)) {
                convertToMagmaCube();
            }
        }
    }

    protected void convertToMagmaCube() {
        this.convertTo(EntityType.MAGMA_CUBE, true);
        if (!this.isSilent()) {
            this.getWorld().syncWorldEvent(null, 1048, this.getBlockPos(), 0);
        }
    }
}
