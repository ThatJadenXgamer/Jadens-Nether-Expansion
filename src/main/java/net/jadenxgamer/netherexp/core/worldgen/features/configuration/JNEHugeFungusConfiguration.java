package net.jadenxgamer.netherexp.core.worldgen.features.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Optional;

public record JNEHugeFungusConfiguration(BlockState validBaseState,
                                         BlockState stemState, BlockState hatState, BlockState decorState, Optional<BlockState> beardState,
                                         BlockPredicate replaceableBlocks, boolean planted,
                                         int minHeight, int maxHeight, int bonusHeight) implements FeatureConfiguration {
    public static final Codec<JNEHugeFungusConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockState.CODEC.fieldOf("valid_base_block").forGetter(JNEHugeFungusConfiguration::validBaseState),
                    BlockState.CODEC.fieldOf("stem_state").forGetter(JNEHugeFungusConfiguration::stemState),
                    BlockState.CODEC.fieldOf("hat_state").forGetter(JNEHugeFungusConfiguration::hatState),
                    BlockState.CODEC.fieldOf("decor_state").forGetter(JNEHugeFungusConfiguration::decorState),
                    BlockState.CODEC.optionalFieldOf("beard_state").forGetter(JNEHugeFungusConfiguration::beardState),
                    BlockPredicate.CODEC.fieldOf("replaceable_blocks").forGetter(JNEHugeFungusConfiguration::replaceableBlocks),
                    Codec.BOOL.fieldOf("planted").forGetter(JNEHugeFungusConfiguration::planted),
                    Codec.INT.fieldOf("min_height").forGetter(JNEHugeFungusConfiguration::minHeight),
                    Codec.INT.fieldOf("max_height").forGetter(JNEHugeFungusConfiguration::maxHeight),
                    Codec.INT.fieldOf("bonus_height").forGetter(JNEHugeFungusConfiguration::bonusHeight)
            ).apply(instance, JNEHugeFungusConfiguration::new)
    );
}