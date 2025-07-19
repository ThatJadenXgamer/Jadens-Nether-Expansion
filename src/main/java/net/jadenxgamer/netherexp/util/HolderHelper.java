package net.jadenxgamer.netherexp.util;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class HolderHelper {

    public static Holder<Enchantment> getEnchantmentHolder(ResourceKey<Enchantment> enchantment) {
        return NetherExp.registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantment);
    }

    public static Holder<MobEffect> getEffectHolder(ResourceKey<MobEffect> effect) {
        return NetherExp.registryAccess.registryOrThrow(Registries.MOB_EFFECT).getHolderOrThrow(effect);
    }
}
