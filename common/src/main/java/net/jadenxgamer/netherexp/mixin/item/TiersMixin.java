package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.world.item.Tiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Tiers.class)
public abstract class TiersMixin {

    @ModifyArgs(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Tiers;<init>(Ljava/lang/String;IIIFFILjava/util/function/Supplier;)V")
    )
    private static void netherexp$goldTweaks(Args args) {
        if (args.get(0).equals("GOLD") && JNEConfigs.ENABLE_GOLD_TWEAKS.get()) {
            args.set(2, netherexp$getGoldMiningTier());
            args.set(3, JNEConfigs.GOLD_TOOLS_DURABILITY.get());
            args.set(4, JNEConfigs.GOLD_TOOLS_SPEED.get());
        }
    }

    @Unique
    private static int netherexp$getGoldMiningTier() {
        switch (JNEConfigs.GOLD_TOOLS_MINING_TIER.get()) {
            case DIAMOND_LIKE -> {
                return 3;
            }
            case IRON_LIKE -> {
                return 2;
            }
            case STONE_LIKE -> {
                return 1;
            }
            default -> {
                return 0;
            }
        }
    }
}
