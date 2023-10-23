package net.jadenxgamer.netherexp.registry.worldgen.feature;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {

    // NETHER WASTES

    public static final RegistryKey<PlacedFeature> QUARTZ_CRYSTAL = registerKey("nether_wastes/quartz_crystal");
    public static final RegistryKey<PlacedFeature> QUARTZ_CRYSTAL_EXTRA = registerKey("nether_wastes/quartz_crystal_extra");

    // SOUL SAND VALLEY

    public static final RegistryKey<PlacedFeature> ECTO_SOUL_SAND = registerKey("soul_sand_valley/ecto_soul_sand");

    public static final RegistryKey<PlacedFeature> BONE_ROD = registerKey("soul_sand_valley/bone_rod");
    public static final RegistryKey<PlacedFeature> JAGGED_SOUL_SLATE = registerKey("soul_sand_valley/jagged_soul_slate");
    public static final RegistryKey<PlacedFeature> ORE_SOUL_MAGMA = registerKey("soul_sand_valley/ore_soul_magma");
    public static final RegistryKey<PlacedFeature> SOUL_SWIRLS_CEILING = registerKey("soul_sand_valley/soul_swirls_ceiling");
    public static final RegistryKey<PlacedFeature> SOUL_SWIRLS_FLOOR = registerKey("soul_sand_valley/soul_swirls_floor");

    // CRIMSON FOREST

    public static final RegistryKey<PlacedFeature> WEEPING_VINES_NETHEREXP = registerKey("crimson_forest/weeping_vines_netherexp");
    public static final RegistryKey<PlacedFeature> CRIMSON_SPORESHROOM = registerKey("crimson_forest/crimson_sporeshroom");
    public static final RegistryKey<PlacedFeature> WEEPING_IVY = registerKey("crimson_forest/weeping_ivy");

    // WARPED FOREST

    public static final RegistryKey<PlacedFeature> WARPED_WART_BLOCK_SPOTTER_SMALL = registerKey("warped_forest/warped_wart_block_spotter_small");
    public static final RegistryKey<PlacedFeature> WARPED_WART_BLOCK_SPOTTER_MEDIUM = registerKey("warped_forest/warped_wart_block_spotter_medium");
    public static final RegistryKey<PlacedFeature> WARPED_WART_BLOCK_SPOTTER_BIG = registerKey("warped_forest/warped_wart_block_spotter_big");
    public static final RegistryKey<PlacedFeature> WARPED_WART_BEARD = registerKey("warped_forest/warped_wart_beard");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(NetherExp.MOD_ID, name));
    }
}
