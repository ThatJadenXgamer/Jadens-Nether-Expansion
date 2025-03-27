package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.entity.custom.BlackIcicle;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(value = CrossbowItem.class, priority = -200)
public class CrossbowItemMixin {

    //TODO: make this tag driven and add it to Elysium
    @Inject(
            method = "getAllSupportedProjectiles",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void netherexp$crossbowProjectiles(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
        cir.setReturnValue(cir.getReturnValue().or(stack -> stack.is(JNEBlocks.BLACK_ICICLE.get().asItem())));
    }

    @Inject(
            method = "shootProjectile",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void netherexp$shootBlackIcicle(Level pLevel, LivingEntity pShooter, InteractionHand pHand, ItemStack pCrossbowStack, ItemStack pAmmoStack, float pSoundPitch, boolean pIsCreativeMode, float pVelocity, float pInaccuracy, float pProjectileAngle, CallbackInfo ci) {
        if (!pLevel.isClientSide() && pAmmoStack.is(JNEBlocks.BLACK_ICICLE.get().asItem())) {
            Vec3 look = pShooter.getLookAngle();
            BlackIcicle blackIcicle = new BlackIcicle(pLevel, pShooter);
            blackIcicle.shoot(look.x, look.y, look.z, 1.3F, 3);
            pLevel.addFreshEntity(blackIcicle);
            pLevel.playSound(null, pShooter.getX(), pShooter.getY(), pShooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, pSoundPitch);
            ci.cancel();
        }
    }
}
