package net.jadenxgamer.netherexp.registry.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModArmorTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> RIFT = registerTrim("rift");

    private static RegistryKey<ArmorTrimPattern> registerTrim(String name) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier(NetherExp.MOD_ID, name));
    }

    public static void registerArmorTrimPatterns() {
        NetherExp.LOGGER.debug("Registering Items for " + NetherExp.MOD_ID);
    }
}
