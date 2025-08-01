package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public record OnDeathGroundConversion(HolderSet<EntityType<?>> entityTypes,
                                      ResourceLocation groundBlock, ResourceLocation conversionBlock) {

    public static final Codec<OnDeathGroundConversion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("entity_types").forGetter(OnDeathGroundConversion::entityTypes),
            ResourceLocation.CODEC.fieldOf("ground_block").forGetter(OnDeathGroundConversion::groundBlock),
            ResourceLocation.CODEC.fieldOf("conversion_block").forGetter(OnDeathGroundConversion::conversionBlock)
    ).apply(instance, OnDeathGroundConversion::new));
}