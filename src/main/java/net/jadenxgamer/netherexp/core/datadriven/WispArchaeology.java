package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public record WispArchaeology(Optional<ResourceLocation> structure, Optional<HolderSet<Biome>> biomes,
                              ResourceLocation lootTable) {

    public static final Codec<WispArchaeology> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("structure").forGetter(WispArchaeology::structure),
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(WispArchaeology::biomes),
            ResourceLocation.CODEC.fieldOf("loot_table").forGetter(WispArchaeology::lootTable)
    ).apply(instance, WispArchaeology::new));
}