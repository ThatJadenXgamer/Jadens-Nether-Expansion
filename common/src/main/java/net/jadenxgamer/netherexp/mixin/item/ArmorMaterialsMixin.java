package net.jadenxgamer.netherexp.mixin.item;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.Util;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.EnumMap;

@Mixin(ArmorMaterials.class)
public abstract class ArmorMaterialsMixin {

    @ModifyArgs(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArmorMaterials;<init>(Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sounds/SoundEvent;FFLjava/util/function/Supplier;)V")
    )
    private static void netherexp$goldTweaks(Args args) {
        if (args.get(0).equals("GOLD") && JNEConfigs.ENABLE_GOLD_TWEAKS.get()) {
            args.set(3, JNEConfigs.GOLD_ARMOR_DURABILITY.get());
            args.set(4, Util.make(new EnumMap(ArmorItem.Type.class), (enumMap) -> {
                enumMap.put(ArmorItem.Type.BOOTS, JNEConfigs.GOLD_BOOTS_PROTECTION.get());
                enumMap.put(ArmorItem.Type.LEGGINGS, JNEConfigs.GOLD_LEGGINGS_PROTECTION.get());
                enumMap.put(ArmorItem.Type.CHESTPLATE, JNEConfigs.GOLD_CHESTPLATE_PROTECTION.get());
                enumMap.put(ArmorItem.Type.HELMET, JNEConfigs.GOLD_HELMET_PROTECTION.get());
            }));
        }
    }
}
