package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Shadow public abstract boolean hasEffect(MobEffect effect);

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(
            method = "getBlockSpeedFactor",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$soulSpeedVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        BlockState state = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
        if (state.is(JNETags.Blocks.UNBOUNDED_SPEED_BLOCKS) && this.hasEffect(JNEMobEffects.UNBOUNDED_SPEED.get()) && !EnchantmentHelper.hasSoulSpeed((LivingEntity) (Object) this)) {
            cir.setReturnValue(1.0F);
        }
        // TODO Add Configs
//        else if (state.is(JNETags.Blocks.UNBOUNDED_SPEED_BLOCKS) && NetherExp.getConfig().gamemechanics.soulSpeedConfigs.nerfed_soul_sand_slowness) {
//            cir.setReturnValue(0.6F);
//        }
    }

    @ModifyArg(
            method = "tryAddSoulSpeed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V")
    )
    private int netherexp$addSoulSpeedQOL(int amount) {
        return 1;
        // TODO Add Configs
//        return NetherExp.getConfig().gamemechanics.soulSpeedConfigs.no_soul_speed_degradation ? 0 : 1;
    }
}
