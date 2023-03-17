package net.jadenxgamer.netherexp.world.biome;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiomes {
    public static final RegistryKey<Biome> VENT_MIRE = RegistryKey.of(Registry.BIOME_KEY, new Identifier(NetherExp.MOD_ID, "vent_mire"));
}


