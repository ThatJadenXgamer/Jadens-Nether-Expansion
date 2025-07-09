package net.jadenxgamer.netherexp.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class HolderHelper {

    public static Holder<Enchantment> getEnchantmentHolder(Level level, ResourceKey<Enchantment> enchantment) {
        return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantment);
    }
}
