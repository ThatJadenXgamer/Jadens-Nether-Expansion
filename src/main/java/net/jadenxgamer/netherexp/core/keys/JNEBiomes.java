package net.jadenxgamer.netherexp.core.keys;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class JNEBiomes {
    public static final ResourceKey<Biome> BLACK_ICE_GLACIERS = register("black_ice_glaciers");

    private static ResourceKey<Biome> register(String string) {
        return ResourceKey.create(Registries.BIOME, NetherExp.netherexpPath(string));
    }
}
