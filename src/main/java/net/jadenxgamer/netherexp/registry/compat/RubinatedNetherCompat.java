package net.jadenxgamer.netherexp.registry.compat;

import net.jadenxgamer.elysium_api.api.registry.ElysiumReflection;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

import static net.jadenxgamer.netherexp.util.RegistryHelper.registerBlock;

public class RubinatedNetherCompat {

    public static class Blocks {

        public static final Supplier<Block> SOUL_RUBY_ORE = registerBlock("soul_ruby_ore", () ->
                ElysiumReflection.createBlock()
                        .className("corundum.rubinated_nether.content.blocks.MagmaExperienceBlock")
                        .constructorValues(BlockBehaviour.Properties.ofFullCopy(JNEBlocks.SOUL_MAGMA_BLOCK.get()).strength(2.0f).requiresCorrectToolForDrops(), UniformInt.of(4, 8)));

        public static void init() {}
    }
}
