package net.jadenxgamer.netherexp.core.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

// it bothers the absolute hell out of me that vanilla's codecs aren't records, it's a pain to convert them
public record NotGarbageLargeDripstoneFeatureConfiguration(int floorToCeilingSearchRange, BlockState block, HolderSet<Block> baseBlocks,
                                                           IntProvider columnRadius, FloatProvider heightScale,
                                                           float maxColumnRadiusToCaveHeightRatio,
                                                           FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness,
                                                           FloatProvider windSpeed,
                                                           int minRadiusForWind, float minBluntnessForWind) implements FeatureConfiguration {
    public static final Codec<NotGarbageLargeDripstoneFeatureConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter(NotGarbageLargeDripstoneFeatureConfiguration::floorToCeilingSearchRange),
                    BlockState.CODEC.fieldOf("block").orElse(Blocks.DRIPSTONE_BLOCK.defaultBlockState()).forGetter(NotGarbageLargeDripstoneFeatureConfiguration::block), // tis is new innit
                    RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("base_blocks").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::baseBlocks), // that too
                    IntProvider.codec(1, 60).fieldOf("column_radius").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::columnRadius),
                    FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::heightScale),
                    Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::maxColumnRadiusToCaveHeightRatio),
                    FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::stalactiteBluntness),
                    FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::stalagmiteBluntness),
                    FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::windSpeed),
                    Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::minRadiusForWind),
                    Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(NotGarbageLargeDripstoneFeatureConfiguration::minBluntnessForWind)
            ).apply(instance, NotGarbageLargeDripstoneFeatureConfiguration::new)
    );
}