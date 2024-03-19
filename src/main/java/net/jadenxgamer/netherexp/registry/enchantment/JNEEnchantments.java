package net.jadenxgamer.netherexp.registry.enchantment;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.enchantment.custom.PhantasmHullEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class JNEEnchantments {
    public static final Enchantment PHANTASM_HULL = registerEnchantment("phantasm_hull",
            new PhantasmHullEnchantment());

    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(NetherExp.MOD_ID, name), enchantment);
    }

    public static void registerEnchantments() {
        NetherExp.LOGGER.debug("Registering Enchantments for " + NetherExp.MOD_ID);
    }
}
