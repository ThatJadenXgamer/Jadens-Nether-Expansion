package net.jadenxgamer.netherexp.registry.worldgen.feature;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.feature.custom.WarpedFungusFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;

public class JNEFeature {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(NetherExp.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<Feature<HugeFungusConfiguration>> WARPED_HUGE_FUNGUS = FEATURES.register("warped_huge_fungus", () ->
            new WarpedFungusFeature(HugeFungusConfiguration.CODEC));

    public static void init() {
        FEATURES.register();
    }
}
