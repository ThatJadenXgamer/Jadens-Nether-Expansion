package net.jadenxgamer.netherexp.fabric.worldgen.region;

import com.mojang.datafixers.util.Pair;
import net.jadenxgamer.netherexp.registry.worldgen.JNEBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class SorrowsquashPasturesRegion extends Region {
    public SorrowsquashPasturesRegion(ResourceLocation name, int weight) {
        super(name, RegionType.NETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addBiome(mapper,
                //Temperature
                Climate.Parameter.point(0.0F),
                //Humidity
                Climate.Parameter.point(-0.5F),
                //Continentalness
                Climate.Parameter.point(0.0F),
                //Erosion
                Climate.Parameter.point(0.0F),
                //Weirdness
                Climate.Parameter.point(0.0F),
                //Depth
                Climate.Parameter.point(0.0F),
                0.0F, JNEBiomes.SORROWSQUASH_PASTURES);
    }
}
