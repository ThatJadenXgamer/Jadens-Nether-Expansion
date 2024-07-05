package net.jadenxgamer.netherexp.mixin.entity;

import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEDecoratedPotPatterns;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotPatterns.class)
public abstract class DecoratedPotPatternsMixin {

    @Inject(
            method = "getResourceKey",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private static void netherexp$netherResourceKeys(Item item, CallbackInfoReturnable<ResourceKey<String>> cir) {
        if (item == JNEItems.SEALED_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.SEALED);
        }
        if (item == JNEItems.SPECTRE_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.SPECTRE);
        }
        if (item == JNEItems.MARIONETTE_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.MARIONETTE);
        }
        if (item == JNEItems.ELDRITCH_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.ELDRITCH);
        }
        if (item == JNEItems.DECEPTION_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.DECEPTION);
        }
        if (item == JNEItems.FIREARM_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.FIREARM);
        }
        if (item == JNEItems.BOTANICAL_POTTERY_SHERD.get()) {
            cir.setReturnValue(JNEDecoratedPotPatterns.BOTANICAL);
        }
    }

    @Inject(
            method = "bootstrap",
            at = @At(value = "HEAD")
    )
    private static void netherexp$bootstrap(Registry<String> registry, CallbackInfoReturnable<String> cir) {
        Registry.register(registry, JNEDecoratedPotPatterns.SEALED, "sealed_pottery_pattern");
        Registry.register(registry, JNEDecoratedPotPatterns.SPECTRE, "spectre_pottery_pattern");
        Registry.register(registry, JNEDecoratedPotPatterns.MARIONETTE, "marionette_pottery_pattern");
        Registry.register(registry, JNEDecoratedPotPatterns.ELDRITCH, "eldritch_pottery_pattern");
        Registry.register(registry, JNEDecoratedPotPatterns.DECEPTION, "deception_pottery_pattern");
        Registry.register(registry, JNEDecoratedPotPatterns.FIREARM, "firearm_pottery_pattern");
        Registry.register(registry, JNEDecoratedPotPatterns.BOTANICAL, "botanical_pottery_pattern");
    }
}
