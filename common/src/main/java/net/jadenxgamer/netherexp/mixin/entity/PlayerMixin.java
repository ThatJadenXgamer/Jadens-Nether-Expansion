package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
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
    private void netherexp$killedEntity(ServerLevel level, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
        /*
         * Players which kill an entity with the bloodshed enchantment will make the entity splatter into healing blood projectiles
         * the amount of blood which spawns from it is calculated by the entity's hitbox size and generally checks if it bleeds
         * the amount of health each blood heals is dependent on the enchantment level
        */
        Player player = ((Player) (Object) this);
        ItemStack stack = player.getMainHandItem();
        int bloodshed = EnchantmentHelper.getItemEnchantmentLevel(JNEEnchantments.BLOODSHED.get(), stack);
        if (!level.isClientSide && bloodshed > 0 && !other.getType().is(JNETags.EntityTypes.DOES_NOT_BLEED)) {
            float width = other.getDimensions(other.getPose()).width;
            float height = other.getDimensions(other.getPose()).height;
            int count = Math.max(1, Math.round((width + height) * 6.5f));
            for (int i = 0; i < count; i++) {
                BloodDrop bloodDrop = new BloodDrop(other.getX(), other.getY() + 0.5, other.getZ(), level, other, bloodshed);
                bloodDrop.shoot(Mth.nextDouble(level.random, -0.25, 0.25), Mth.nextDouble(level.random, 0.4, 0.6), Mth.nextDouble(level.random, -0.25, 0.25), 0.5f, 16);
                level.addFreshEntity(bloodDrop);
            }
        }
    }
}
