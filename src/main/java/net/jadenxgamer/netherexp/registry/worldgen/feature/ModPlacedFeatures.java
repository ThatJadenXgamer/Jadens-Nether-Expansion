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
    public static final RegistryKey<PlacedFeature> FOSSIL_FUEL_ORE = registerKey("soul_sand_valley/fossil_fuel_ore");
    public static final RegistryKey<PlacedFeature> BLACK_ICE = registerKey("soul_sand_valley/black_ice");
    public static final RegistryKey<PlacedFeature> ECTOPLASM_LAKE = registerKey("soul_sand_valley/ectoplasm_lake");
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
    public static final RegistryKey<PlacedFeature> WARPED_SPORESHROOM = registerKey("warped_forest/warped_sporeshroom");
    public static final RegistryKey<PlacedFeature> TWISTING_IVY = registerKey("warped_forest/twisting_ivy");

    // BASALT DELTAS

    public static final RegistryKey<PlacedFeature> WHITE_ASH = registerKey("basalt_deltas/white_ash");
    public static final RegistryKey<PlacedFeature> BASALTIC_GEYSER = registerKey("basalt_deltas/basaltic_geyser");


    // VENT MIRE

    public static final RegistryKey<PlacedFeature> SMOKESTALK = registerKey("vent_mire/smokestalk");
    public static final RegistryKey<PlacedFeature> IGNEOUS_REEDS = registerKey("vent_mire/igneous_reeds");
    public static final RegistryKey<PlacedFeature> BLACKSTONE_PATCH = registerKey("vent_mire/blackstone_patch");
    public static final RegistryKey<PlacedFeature> VENT_DELTA = registerKey("vent_mire/vent_delta");

    ///////////////////////
    // MOD COMPATIBILITY //
    ///////////////////////

    // LUMINOUS GROVE

    public static final RegistryKey<PlacedFeature> UMBRAL_SPORESHROOM = registerKey("luminous_grove/umbral_sporeshroom");
    public static final RegistryKey<PlacedFeature> TWILIGHT_VINES = registerKey("luminous_grove/twilight_vines");
    public static final RegistryKey<PlacedFeature> TWILIGHT_IVY = registerKey("luminous_grove/twilight_ivy");


    // ASHY SHOALS

    public static final RegistryKey<PlacedFeature> ASHEN_GEYSER = registerKey("ashy_shoals/ashen_geyser");
    public static final RegistryKey<PlacedFeature> ASHY_SHOALS_SHALE_SWIRLS_CEILING = registerKey("ashy_shoals/shale_swirls_ceiling");
    public static final RegistryKey<PlacedFeature> ASHY_SHOALS_SHALE_SWIRLS_FLOOR = registerKey("ashy_shoals/shale_swirls_floor");


    // BLACKSTONE SHALES

    public static final RegistryKey<PlacedFeature> BLACKSTONIC_GEYSER = registerKey("blackstone_shales/blackstonic_geyser");
    public static final RegistryKey<PlacedFeature> BLACKSTONE_SHALES_SHALE_SWIRLS_CEILING = registerKey("blackstone_shales/shale_swirls_ceiling");
    public static final RegistryKey<PlacedFeature> BLACKSTONE_SHALES_SHALE_SWIRLS_FLOOR = registerKey("blackstone_shales/shale_swirls_floor");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(NetherExp.MOD_ID, name));
    }
}
