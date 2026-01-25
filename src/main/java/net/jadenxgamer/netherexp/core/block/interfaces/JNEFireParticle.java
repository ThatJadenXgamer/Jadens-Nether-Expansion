package net.jadenxgamer.netherexp.core.block.interfaces;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.Optional;

public interface JNEFireParticle {

    Color smokeStartColor(BlockState state, RandomSource random);
    Color smokeEndColor(BlockState state, RandomSource random);
    default Optional<Color> emberColor(BlockState state, RandomSource random) {
        return Optional.empty();
    }
}
