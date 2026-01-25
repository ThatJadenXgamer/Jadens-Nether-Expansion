package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.core.block.interfaces.JNEFireParticle;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.awt.*;
import java.util.Optional;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin implements JNEFireParticle {

    @Override
    public Color smokeStartColor(BlockState state, RandomSource random) {
        return new Color(0xFF8300);
    }

    @Override
    public Optional<Color> emberColor(BlockState state, RandomSource random) {
        return Optional.of(new Color(0xF2A56E));
    }
}
