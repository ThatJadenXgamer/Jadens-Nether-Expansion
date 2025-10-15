package net.jadenxgamer.netherexp.core.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record MoundFeatureConfiguration(HolderSet<Block> validPlacements,
                                        int height, int bonusHeight, int extraHeight, int bonusExtraHeight,
                                        int minExtraMounds, int maxExtraMounds, int radius,
                                        boolean hanging) implements FeatureConfiguration {
    public static final Codec<MoundFeatureConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_placements").forGetter(MoundFeatureConfiguration::validPlacements),
                    Codec.INT.fieldOf("height").forGetter(MoundFeatureConfiguration::height),
                    Codec.INT.fieldOf("bonus_height").forGetter(MoundFeatureConfiguration::bonusHeight),
                    Codec.INT.fieldOf("extra_height").forGetter(MoundFeatureConfiguration::extraHeight),
                    Codec.INT.fieldOf("bonus_extra_height").forGetter(MoundFeatureConfiguration::bonusExtraHeight),
                    Codec.INT.fieldOf("min_extra_mounds").forGetter(MoundFeatureConfiguration::minExtraMounds),
                    Codec.INT.fieldOf("max_extra_mounds").forGetter(MoundFeatureConfiguration::maxExtraMounds),
                    Codec.INT.fieldOf("radius").forGetter(MoundFeatureConfiguration::radius),
                    Codec.BOOL.optionalFieldOf("hanging", false).forGetter(MoundFeatureConfiguration::hanging)
            ).apply(instance, MoundFeatureConfiguration::new)
    );
}