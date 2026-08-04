package net.jadenxgamer.netherexp.core.datadriven;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record EctoplasmHaunting(HolderSet<Block> target, ResourceLocation haunted) {
    public static final Codec<EctoplasmHaunting> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("target").forGetter(EctoplasmHaunting::target),
            ResourceLocation.CODEC.fieldOf("haunted").forGetter(EctoplasmHaunting::haunted)
    ).apply(instance, EctoplasmHaunting::new));
}