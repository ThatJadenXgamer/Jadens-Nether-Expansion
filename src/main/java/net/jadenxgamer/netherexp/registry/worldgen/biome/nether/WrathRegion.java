package net.jadenxgamer.netherexp.registry.worldgen.biome.nether;

import com.mojang.datafixers.util.Pair;
import net.jadenxgamer.netherexp.registry.worldgen.biome.ModBiomes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class WrathRegion extends Region {
    public WrathRegion(Identifier name, int weight) {
        super(name, RegionType.NETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addBiome(mapper,
                //Temperature
                MultiNoiseUtil.ParameterRange.of(-0.5F),
                //Humidity
                MultiNoiseUtil.ParameterRange.of(0.0F),
                //Continentalness
                MultiNoiseUtil.ParameterRange.of(0.0F),
                //Erosion
                MultiNoiseUtil.ParameterRange.of(0.0F),
                //Weirdness
                MultiNoiseUtil.ParameterRange.of(0.0F),
                //Depth
                MultiNoiseUtil.ParameterRange.of(0.0F),
                0.0F, ModBiomes.VENT_MIRE);
    }
}
