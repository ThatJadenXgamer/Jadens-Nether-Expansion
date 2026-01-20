package net.jadenxgamer.netherexp.data.worldgen.features;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.worldgen.features.configuration.JNELargeDripstoneConfiguration;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEFeatureTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class JNEMiscFeatures {

    public static ResourceKey<ConfiguredFeature<?, ?>> NETHERRACK_SPELEOTHEM = registerKey("netherrack_speleothem");
    public static ResourceKey<ConfiguredFeature<?, ?>> SOUL_SOIL_SPELEOTHEM = registerKey("soul_soil_speleothem");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NetherExp.id(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        FeatureUtils.register(
                context, NETHERRACK_SPELEOTHEM, JNEFeatureTypes.NETHER_SPELEOTHEM.get(), new NoneFeatureConfiguration());

        FeatureUtils.register(
                context, SOUL_SOIL_SPELEOTHEM, JNEFeatureTypes.NETHER_SPELEOTHEM.get(), new NoneFeatureConfiguration());
    }

    private static Holder<Block> holder(Block block) {
        return BuiltInRegistries.BLOCK.wrapAsHolder(block);
    }

}
