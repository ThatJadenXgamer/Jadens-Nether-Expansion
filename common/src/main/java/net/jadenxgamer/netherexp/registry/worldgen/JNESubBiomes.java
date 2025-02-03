package net.jadenxgamer.netherexp.registry.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class JNESubBiomes {
    private static RegistryAccess registryAccess;

    public static Holder<Biome> replaceBiomes(Holder<Biome> biome) {
        ResourceKey<Biome> biomeKey = biome.unwrapKey().orElseThrow();
        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
        if (biomeKey.equals(Biomes.SOUL_SAND_VALLEY)) {
            if (Math.random() < 0.5) {
                return biomeRegistry.getHolderOrThrow(JNEBiomes.SORROWSQUASH_PASTURES);
            }
        }
        return biome;
    }

    public static void setRegistryAccess(RegistryAccess registryAccess) {
        JNESubBiomes.registryAccess = registryAccess;
    }
}
