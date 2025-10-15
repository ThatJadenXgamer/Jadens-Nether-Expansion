package net.jadenxgamer.netherexp.core.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record MoundConfiguration(HolderSet<Block> validPlacements,
                                 int height, int bonusHeight, int extraHeight, int bonusExtraHeight,
                                 int minExtraMounds, int maxExtraMounds, int radius,
                                 boolean hanging) implements FeatureConfiguration {
    public static final Codec<MoundConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_placements").forGetter(MoundConfiguration::validPlacements),
                    Codec.INT.fieldOf("height").forGetter(MoundConfiguration::height),
                    Codec.INT.fieldOf("bonus_height").forGetter(MoundConfiguration::bonusHeight),
                    Codec.INT.fieldOf("extra_height").forGetter(MoundConfiguration::extraHeight),
                    Codec.INT.fieldOf("bonus_extra_height").forGetter(MoundConfiguration::bonusExtraHeight),
                    Codec.INT.fieldOf("min_extra_mounds").forGetter(MoundConfiguration::minExtraMounds),
                    Codec.INT.fieldOf("max_extra_mounds").forGetter(MoundConfiguration::maxExtraMounds),
                    Codec.INT.fieldOf("radius").forGetter(MoundConfiguration::radius),
                    Codec.BOOL.optionalFieldOf("hanging", false).forGetter(MoundConfiguration::hanging)
            ).apply(instance, MoundConfiguration::new)
    );
}