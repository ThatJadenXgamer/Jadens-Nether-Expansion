package net.jadenxgamer.netherexp.registry.misc_registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageSources {

    public static final RegistryKey<DamageType> DEATH_CLOUD_SUFFOCATION = register("grave_cloud_suffocation");

    public static final RegistryKey<DamageType> STAMPEDE_CRUSH = register("stampede_crush");

    private static RegistryKey<DamageType> register(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(NetherExp.MOD_ID, name));
    }

    public static void registerModDamageSources() {
        NetherExp.LOGGER.debug("Registering Damage Sources for " + NetherExp.MOD_ID);
    }
}
