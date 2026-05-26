package net.jadenxgamer.netherexp.util;

import net.jadenxgamer.elysium_api.api.util.RegistryAccessHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class HolderHelper {

    public static Holder<Enchantment> getEnchantmentHolder(ResourceKey<Enchantment> enchantment) {
        return RegistryAccessHelper.getCurrent()
                .flatMap(access -> access.registryOrThrow(Registries.ENCHANTMENT).getHolder(enchantment))
                .orElse(null);
    }
}
