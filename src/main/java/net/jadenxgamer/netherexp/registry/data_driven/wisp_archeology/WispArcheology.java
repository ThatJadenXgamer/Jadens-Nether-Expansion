package net.jadenxgamer.netherexp.registry.data_driven.wisp_archeology;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public record WispArcheology(Optional<ResourceLocation> structure, Optional<HolderSet<Biome>> biomes, ResourceLocation lootTable) {

    public static final Codec<WispArcheology> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("structure").forGetter(WispArcheology::structure),
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(WispArcheology::biomes),
            ResourceLocation.CODEC.fieldOf("loot_table").forGetter(WispArcheology::lootTable)
    ).apply(instance, WispArcheology::new));
}
