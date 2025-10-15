package net.jadenxgamer.netherexp.core.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
public record JNELargeDripstoneConfiguration(int floorToCeilingSearchRange, BlockState block, HolderSet<Block> baseBlocks,
                                             IntProvider columnRadius, FloatProvider heightScale,
                                             float maxColumnRadiusToCaveHeightRatio,
                                             FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness,
                                             FloatProvider windSpeed,
                                             int minRadiusForWind, float minBluntnessForWind) implements FeatureConfiguration {
    public static final Codec<JNELargeDripstoneConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter(JNELargeDripstoneConfiguration::floorToCeilingSearchRange),
                    BlockState.CODEC.fieldOf("block").orElse(Blocks.DRIPSTONE_BLOCK.defaultBlockState()).forGetter(JNELargeDripstoneConfiguration::block), // tis is new innit
                    RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("base_blocks").forGetter(JNELargeDripstoneConfiguration::baseBlocks), // that too
                    IntProvider.codec(1, 60).fieldOf("column_radius").forGetter(JNELargeDripstoneConfiguration::columnRadius),
                    FloatProvider.codec(0.0F, 20.0F).fieldOf("height_scale").forGetter(JNELargeDripstoneConfiguration::heightScale),
                    Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(JNELargeDripstoneConfiguration::maxColumnRadiusToCaveHeightRatio),
                    FloatProvider.codec(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(JNELargeDripstoneConfiguration::stalactiteBluntness),
                    FloatProvider.codec(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(JNELargeDripstoneConfiguration::stalagmiteBluntness),
                    FloatProvider.codec(0.0F, 2.0F).fieldOf("wind_speed").forGetter(JNELargeDripstoneConfiguration::windSpeed),
                    Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(JNELargeDripstoneConfiguration::minRadiusForWind),
                    Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(JNELargeDripstoneConfiguration::minBluntnessForWind)
            ).apply(instance, JNELargeDripstoneConfiguration::new)
    );
}