package net.jadenxgamer.netherexp.core.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Optional;

public record JNEHugeFungusFeatureConfiguration(BlockState validBaseState,
                                                BlockState stemState, BlockState hatState, BlockState decorState, Optional<BlockState> beardState,
                                                BlockPredicate replaceableBlocks, boolean planted,
                                                int minHeight, int maxHeight, int bonusHeight) implements FeatureConfiguration {
    public static final Codec<JNEHugeFungusFeatureConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BlockState.CODEC.fieldOf("valid_base_block").forGetter(JNEHugeFungusFeatureConfiguration::validBaseState),
                    BlockState.CODEC.fieldOf("stem_state").forGetter(JNEHugeFungusFeatureConfiguration::stemState),
                    BlockState.CODEC.fieldOf("hat_state").forGetter(JNEHugeFungusFeatureConfiguration::hatState),
                    BlockState.CODEC.fieldOf("decor_state").forGetter(JNEHugeFungusFeatureConfiguration::decorState),
                    BlockState.CODEC.optionalFieldOf("beard_state").forGetter(JNEHugeFungusFeatureConfiguration::beardState),
                    BlockPredicate.CODEC.fieldOf("replaceable_blocks").forGetter(JNEHugeFungusFeatureConfiguration::replaceableBlocks),
                    Codec.BOOL.fieldOf("planted").forGetter(JNEHugeFungusFeatureConfiguration::planted),
                    Codec.INT.fieldOf("min_height").forGetter(JNEHugeFungusFeatureConfiguration::minHeight),
                    Codec.INT.fieldOf("max_height").forGetter(JNEHugeFungusFeatureConfiguration::maxHeight),
                    Codec.INT.fieldOf("bonus_height").forGetter(JNEHugeFungusFeatureConfiguration::bonusHeight)
            ).apply(instance, JNEHugeFungusFeatureConfiguration::new)
    );
}