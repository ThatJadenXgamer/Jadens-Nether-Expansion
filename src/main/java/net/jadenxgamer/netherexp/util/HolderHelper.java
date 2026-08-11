package net.jadenxgamer.netherexp.util;

import net.jadenxgamer.elysium_api.api.util.RegistryAccessHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class HolderHelper {

    public static int getEnchantmentLevel(ResourceKey<Enchantment> enchantment, ItemStack stack) {
        Holder<Enchantment> holder = getEnchantmentHolder(enchantment);
        return (holder != null) ? EnchantmentHelper.getTagEnchantmentLevel(holder, stack) : 0;
    }

    private static Holder<Enchantment> getEnchantmentHolder(ResourceKey<Enchantment> enchantment) {
        return RegistryAccessHelper.getCurrent()
                .flatMap(access -> access.registryOrThrow(Registries.ENCHANTMENT).getHolder(enchantment))
                .orElse(null);
    }
}