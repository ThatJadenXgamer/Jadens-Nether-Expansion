package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.core.block.interfaces.JNEFireParticle;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.awt.*;
import java.util.Optional;

@Mixin(SoulFireBlock.class)
public abstract class SoulFireBlockMixin implements JNEFireParticle {

    private static final Color[] SOUL_SMOKE_COLORS = {
            new Color(0x3C4E50),
            new Color(0x3A5659),
            new Color(0x44676A),
            new Color(0x517C7E)
    };

    @Override
    public Color smokeStartColor(BlockState state, RandomSource random) {
        return new Color(0x00EDFF);
    }

    @Override
    public Color smokeEndColor(BlockState state, RandomSource random) {
        return SOUL_SMOKE_COLORS[random.nextInt(SOUL_SMOKE_COLORS.length)];
    }

    @Override
    public Optional<Color> emberColor(BlockState state, RandomSource random) {
        return Optional.of(new Color(0x48F0F5));
    }
}
