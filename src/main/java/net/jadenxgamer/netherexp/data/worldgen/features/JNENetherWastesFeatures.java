package net.jadenxgamer.netherexp.data.worldgen.features;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class JNENetherWastesFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> BRAIN_TREE = registerKey("brain_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NetherExp.id("nether_wastes/" + name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    }
}
