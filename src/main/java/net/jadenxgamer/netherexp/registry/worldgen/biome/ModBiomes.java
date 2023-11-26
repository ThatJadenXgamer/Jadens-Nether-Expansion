package net.jadenxgamer.netherexp.registry.worldgen.biome;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomes
{
    public static final RegistryKey<Biome> VENT_MIRE = register("vent_mire");

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(NetherExp.MOD_ID, name));
    }

    public static void registerBiomes() {
        NetherExp.LOGGER.debug("Registering Biomes for " + NetherExp.MOD_ID);
    }
}
