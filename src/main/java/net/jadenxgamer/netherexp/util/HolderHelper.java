package net.jadenxgamer.netherexp.util;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class HolderHelper {

    public static Holder<Enchantment> getEnchantmentHolder(ResourceKey<Enchantment> enchantment) {
        return NetherExp.registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantment);
    }
}
