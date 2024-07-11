package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.custom.BloodDrop;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(
            method = "getHurtSound",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$getCustomHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if (damageSource.is(JNETags.DamageTypes.IS_SUFFOCATION)) {
            cir.setReturnValue(JNESoundEvents.ENTITY_PLAYER_HURT_SUFFOCATION.get());
        }
    }

    @Inject(
            method = "killedEntity",
            at = @At(value = "HEAD")
    )
    private void netherexp$killedEntity(ServerLevel level, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        Player player = ((Player) (Object) this);
        ItemStack stack = player.getMainHandItem();
        int count = 16;
        if (!level.isClientSide && EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.BLOODSHED.get(), stack) > 0) {
            for (int i = 0; i < count; i++) {
                BloodDrop bloodDrop = new BloodDrop(level, player);
                bloodDrop.shoot(Mth.nextDouble(level.random, -0.25, 0.25), Mth.nextDouble(level.random, 0.5, 1), Mth.nextDouble(level.random, -0.25, 0.25), 0.5f, 24);
                level.addFreshEntity(bloodDrop);
            }
        }
    }
}
