package net.jadenxgamer.netherexp.forge.worldgen;

import com.mojang.datafixers.util.Pair;
import net.jadenxgamer.netherexp.registry.worldgen.JNEBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class SecondaryVanillaSubBiomes extends Region {
    public SecondaryVanillaSubBiomes(ResourceLocation name, int weight) {
        super(name, RegionType.NETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.SOUL_SAND_VALLEY, JNEBiomes.SORROWSQUASH_PASTURES);
        });
    }
}