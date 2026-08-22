package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.registry.JNEDecoratedPotPatterns;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotPatterns.class)
public abstract class DecoratedPotPatternsMixin {

    @Inject(
            method = "getPatternFromItem",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void bygone$getPatternFromItem(Item item, CallbackInfoReturnable<ResourceKey<DecoratedPotPattern>> cir) {
        if (item == JNEItems.SEALED_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.SEALED.getKey());
        } else if (item == JNEItems.SPECTRE_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.SPECTRE.getKey());
        } else if (item == JNEItems.MARIONETTE_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.MARIONETTE.getKey());
        } else if (item == JNEItems.ELDRITCH_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.ELDRITCH.getKey());
        } else if (item == JNEItems.DECEPTION_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.DECEPTION.getKey());
        } else if (item == JNEItems.FIREARM_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.FIREARM.getKey());
        } else if (item == JNEItems.BOTANICAL_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.BOTANICAL.getKey());
        }
    }
}