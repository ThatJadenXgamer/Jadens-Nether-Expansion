package net.jadenxgamer.netherexp.registry.worldgen.feature;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JNEFeature {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, NetherExp.MOD_ID);

    public static final RegistryObject<Feature<HugeFungusConfiguration>> WARPED_HUGE_FUNGUS = FEATURES.register("warped_huge_fungus", () ->
            new WarpedFungusFeature(HugeFungusConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SOUL_MAGMA_CLUSTER = FEATURES.register("soul_magma_cluster", () ->
            new SoulMagmaClusterFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BRAIN_TREE = FEATURES.register("brain_tree", () ->
            new BrainTreeFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<LargeDripstoneConfiguration>> LARGE_BLACK_ICE = FEATURES.register("large_black_ice", () ->
            new LargeBlackIceFeature(LargeDripstoneConfiguration.CODEC));

    public static final RegistryObject<Feature<PointedDripstoneConfiguration>> BLACK_ICICLE = FEATURES.register("black_icicle", () ->
            new BlackIcicleFeature(PointedDripstoneConfiguration.CODEC));

    public static final RegistryObject<Feature<DripstoneClusterConfiguration>> BLACK_ICE_CLUSTER = FEATURES.register("black_ice_cluster", () ->
            new BlackIceClusterFeature(DripstoneClusterConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MOUND = FEATURES.register("mound", () ->
            new MoundFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HANGING_MOUND = FEATURES.register("hanging_mound", () ->
            new HangingMoundFeature(NoneFeatureConfiguration.CODEC));

    public static void init(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
