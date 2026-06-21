package net.jadenxgamer.netherexp.registry.compat;

import net.jadenxgamer.elysium_api.api.reflection.ElysiumReflection;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.block.BlackIceBlock;
import net.jadenxgamer.netherexp.core.effect.ImmunityEffect;
import net.jadenxgamer.netherexp.core.misc.JNESoundType;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

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
