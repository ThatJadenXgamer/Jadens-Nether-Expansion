package net.jadenxgamer.netherexp.registry.worldgen;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class JNEBiomes {

    public static final ResourceKey<Biome> EXHAUST_MIRE = register("exhaust_mire");
    public static final ResourceKey<Biome> SORROWSQUASH_PASTURES = register("sorrowsquash_pastures");

    private static ResourceKey<Biome> register(String string) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(NetherExp.MOD_ID, string));
    }
}
