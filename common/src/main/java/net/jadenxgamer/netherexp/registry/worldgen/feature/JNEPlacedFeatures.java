package net.jadenxgamer.netherexp.registry.worldgen.feature;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class JNEPlacedFeatures {

    // NETHER WASTES

    public static final ResourceKey<PlacedFeature> QUARTZ_CRYSTAL = registerKey("nether_wastes/quartz_crystal");
    public static final ResourceKey<PlacedFeature> QUARTZ_CRYSTAL_EXTRA = registerKey("nether_wastes/quartz_crystal_extra");

    // SOUL SAND VALLEY

    public static final ResourceKey<PlacedFeature> ECTO_SOUL_SAND = registerKey("soul_sand_valley/ecto_soul_sand");
    public static final ResourceKey<PlacedFeature> BONE_ROD = registerKey("soul_sand_valley/bone_rod");
    public static final ResourceKey<PlacedFeature> PALE_SOUL_SLATE = registerKey("soul_sand_valley/pale_soul_slate");
    public static final ResourceKey<PlacedFeature> PALE_SOUL_SLATE_SURFACE = registerKey("soul_sand_valley/pale_soul_slate_surface");
    public static final ResourceKey<PlacedFeature> FOSSIL_FUEL_ORE = registerKey("soul_sand_valley/fossil_fuel_ore");
    public static final ResourceKey<PlacedFeature> FOSSIL_ORE = registerKey("soul_sand_valley/fossil_ore");
    public static final ResourceKey<PlacedFeature> BLACK_ICE = registerKey("soul_sand_valley/black_ice");
    public static final ResourceKey<PlacedFeature> ECTOPLASM_LAKE = registerKey("soul_sand_valley/ectoplasm_lake");
    public static final ResourceKey<PlacedFeature> ORE_SOUL_MAGMA = registerKey("soul_sand_valley/ore_soul_magma");
    public static final ResourceKey<PlacedFeature> SOUL_MAGMA_CLUSTER = registerKey("soul_sand_valley/soul_magma_cluster");
    public static final ResourceKey<PlacedFeature> SOUL_SWIRLS_CEILING = registerKey("soul_sand_valley/soul_swirls_ceiling");
    public static final ResourceKey<PlacedFeature> SOUL_SWIRLS_FLOOR = registerKey("soul_sand_valley/soul_swirls_floor");

    // CRIMSON FOREST
    public static final ResourceKey<PlacedFeature> CRIMSON_SPORESHROOM = registerKey("crimson_forest/crimson_sporeshroom");
    public static final ResourceKey<PlacedFeature> WEEPING_IVY = registerKey("crimson_forest/weeping_ivy");

    // WARPED FOREST

    public static final ResourceKey<PlacedFeature> WARPED_SPORESHROOM = registerKey("warped_forest/warped_sporeshroom");
    public static final ResourceKey<PlacedFeature> TWISTING_IVY = registerKey("warped_forest/twisting_ivy");

    // BASALT DELTAS
    public static final ResourceKey<PlacedFeature> BASALTIC_GEYSER = registerKey("basalt_deltas/basaltic_geyser");

    // VENT MIRE

    public static final ResourceKey<PlacedFeature> SMOKESTALK = registerKey("vent_mire/smokestalk");
    public static final ResourceKey<PlacedFeature> IGNEOUS_REEDS = registerKey("vent_mire/igneous_reeds");
    public static final ResourceKey<PlacedFeature> BLACKSTONE_PATCH = registerKey("vent_mire/blackstone_patch");
    public static final ResourceKey<PlacedFeature> VENT_DELTA = registerKey("vent_mire/vent_delta");

    ///////////////////////
    // MOD COMPATIBILITY //
    ///////////////////////

    // LUMINOUS GROVE

    public static final ResourceKey<PlacedFeature> UMBRAL_SPORESHROOM = registerKey("luminous_grove/umbral_sporeshroom");
    public static final ResourceKey<PlacedFeature> TWILIGHT_VINES = registerKey("luminous_grove/twilight_vines");
    public static final ResourceKey<PlacedFeature> TWILIGHT_IVY = registerKey("luminous_grove/twilight_ivy");


    // ASHY SHOALS

    public static final ResourceKey<PlacedFeature> ASHEN_GEYSER = registerKey("ashy_shoals/ashen_geyser");
    public static final ResourceKey<PlacedFeature> ASHY_SHOALS_SHALE_SWIRLS_CEILING = registerKey("ashy_shoals/shale_swirls_ceiling");
    public static final ResourceKey<PlacedFeature> ASHY_SHOALS_SHALE_SWIRLS_FLOOR = registerKey("ashy_shoals/shale_swirls_floor");


    // BLACKSTONE SHALES

    public static final ResourceKey<PlacedFeature> BLACKSTONIC_GEYSER = registerKey("blackstone_shales/blackstonic_geyser");
    public static final ResourceKey<PlacedFeature> BLACKSTONE_SHALES_SHALE_SWIRLS_CEILING = registerKey("blackstone_shales/shale_swirls_ceiling");
    public static final ResourceKey<PlacedFeature> BLACKSTONE_SHALES_SHALE_SWIRLS_FLOOR = registerKey("blackstone_shales/shale_swirls_floor");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(NetherExp.MOD_ID, name));
    }
}
