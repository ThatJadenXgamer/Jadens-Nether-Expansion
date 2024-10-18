package net.jadenxgamer.netherexp.registry.worldgen.feature;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class JNEConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> SORROWEED_PATCH_BONEMEAL = registerKey("soul_sand_valley/sorroweed_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRAIN_TREE = registerKey("nether_wastes/brain_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(NetherExp.MOD_ID, name));
    }
}
