package net.jadenxgamer.netherexp.core.block.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

public interface GlowsporesApplicable {

    Property<?> affectedProperty();

    Item glowsporeOfBlock();

    default void dropGlowspores(Level level, BlockPos pos, Direction direction) {
        Block.popResourceFromFace(level, pos, direction, new ItemStack(glowsporeOfBlock(), 1));
    }
}
