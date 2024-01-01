package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.effect.ModStatusEffects;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "getVelocityMultiplier",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        BlockState state = this.getWorld().getBlockState(this.getVelocityAffectingPos());
        if (state.isIn(ModTags.Blocks.UNBOUNDED_SPEED_BLOCKS) && this.hasStatusEffect(ModStatusEffects.UNBOUNDED_SPEED) && !EnchantmentHelper.hasSoulSpeed((LivingEntity) (Object) this)) {
            cir.setReturnValue(1.0F);
        }
        else if (state.isIn(ModTags.Blocks.UNBOUNDED_SPEED_BLOCKS) && NetherExp.getConfig().blocks.soulSandConfigs.nerfed_soul_sand_slowness) {
            cir.setReturnValue(0.6F);
        }
    }
}