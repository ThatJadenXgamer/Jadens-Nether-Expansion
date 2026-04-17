package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(PotionItem.class)
public class PotionItemMixin {

    @Inject(
            method = "finishUsingItem",
            at = @At(value = "HEAD")
    )
    private static void netherexp$injectPotionUseEffect(ItemStack stack, Level level, LivingEntity entityLiving, CallbackInfoReturnable<ItemStack> cir) {
        PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        boolean hasEffect = contents.hasEffects();
        if (hasEffect) {
            if (level.isClientSide()) CommonParticles.potionConsumeParticle(level, level.random, entityLiving, new Color(contents.getColor()));
            level.playSound(null, entityLiving.blockPosition(), JNESoundEvents.POTION_POSTDRINK.get(), SoundSource.PLAYERS, 1.0f, 1.0F);
        }
    }
}
