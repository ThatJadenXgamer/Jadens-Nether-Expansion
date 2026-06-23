package net.jadenxgamer.netherexp.config;

import net.jadenxgamer.netherexp.config.enums.BansheeRedirectConfig;
import net.jadenxgamer.netherexp.config.enums.ProfanityConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class JNEConfigs {

    // BLOCK
    public static ModConfigSpec.DoubleValue FOSSIL_ORE_CONVERSION_CHANCE;
    public static ModConfigSpec.DoubleValue SOUL_GLASS_MOVEMENT_SLOWDOWN;
    public static ModConfigSpec.IntValue SOUL_SWIRLS_COOLDOWN;
    public static ModConfigSpec.IntValue SOUL_SPEED_DURATION;
    public static ModConfigSpec.BooleanValue BONE_MEAL_SOUL_SWIRLS;
    public static ModConfigSpec.BooleanValue BRUSH_WISPS_OUT;
    public static ModConfigSpec.DoubleValue WISP_EMERGING_CHANCE;
    public static ModConfigSpec.DoubleValue WISP_EMERGING_CHANCE_BRUSH;
    public static ModConfigSpec.IntValue ECTO_SOUL_SAND_BRUSH_DAMAGE;
    public static ModConfigSpec.BooleanValue CONVERTS_TO_SUSPICIOUS_SOUL_SAND;
    public static ModConfigSpec.ConfigValue<String> WISP_ARCHAEOLOGY_DEFAULT_LOOT_TABLE;
    public static ModConfigSpec.DoubleValue SUSPICIOUS_SOUL_SAND_DECAY_CHANCE;
    public static ModConfigSpec.IntValue SUSPICIOUS_SOUL_SAND_MAX_DECAY;
    public static ModConfigSpec.DoubleValue LESION_GROWTH_CHANCE;
    public static ModConfigSpec.IntValue LESION_DROPS_PER_HARVEST;
    public static ModConfigSpec.DoubleValue SPORESHROOM_HEIGHT_VELOCITY;
    public static ModConfigSpec.DoubleValue GEYSER_HEIGHT_VELOCITY;
    public static ModConfigSpec.IntValue GEYSER_COOLDOWN;
    public static ModConfigSpec.DoubleValue BLACK_ICICLE_GROWTH_CHANCE;
    public static ModConfigSpec.DoubleValue THIN_ICE_BREAKING_CHANCE;
    public static ModConfigSpec.DoubleValue THIN_ICE_BREAKING_CHANCE_SPRINTING;
    public static ModConfigSpec.DoubleValue NEARBY_THIN_ICE_BREAKING_CHANCE;
    public static ModConfigSpec.DoubleValue BLACK_ICE_FROSTS_WATER_CHANCE;
    public static ModConfigSpec.BooleanValue BLACK_ICE_TAINTING;
    public static ModConfigSpec.BooleanValue SORROWSQUASH_TAINTING;
    public static ModConfigSpec.BooleanValue SHOULD_SORROWSQUASH_FALL;
    public static ModConfigSpec.DoubleValue SORROWSQUISHED_DAMAGE_MULTIPLIER;
    public static ModConfigSpec.IntValue SORROWSQUISHED_MAX_DAMAGE;
    public static ModConfigSpec.DoubleValue SORROWSQUASH_GROWTH_CHANCE;
    public static ModConfigSpec.DoubleValue CEREBRAGE_GROWTH_CHANCE;
    public static ModConfigSpec.IntValue MIN_CEREBRAGE_DROPPED;
    public static ModConfigSpec.IntValue MAX_CEREBRAGE_DROPPED;
    public static ModConfigSpec.DoubleValue CEREBRAGE_SEEDS_DROP_CHANCE;
    public static ModConfigSpec.BooleanValue CEREBRAGE_GROWS_BRAIN_TREES;
    public static ModConfigSpec.IntValue SHOTGUN_BARREL_BULLETS;
    public static ModConfigSpec.BooleanValue CAN_ANYTHING_BREAK_FROGMIST;
    public static ModConfigSpec.IntValue CIERGE_OF_TREACHERY_COMPLETION_COOLDOWN;
    public static ModConfigSpec.IntValue HAZE_BLOCK_COOLDOWN;

    // ITEM
    public static ModConfigSpec.IntValue IMMUNITY_CONSUMPTION;
    public static ModConfigSpec.BooleanValue AMPLIFIER_SCALES_IMMUNITY_CONSUMPTION;
    public static ModConfigSpec.BooleanValue TWEAK_OBTAINING_TEARS_MUSIC_DISC;
    public static ModConfigSpec.DoubleValue SHOTGUN_SELF_RECOIL;
    public static ModConfigSpec.DoubleValue POINT_BLANK_SELF_RECOIL_BONUS;
    public static ModConfigSpec.DoubleValue POINT_BLANK_SELF_RECOIL_DISTANCE;
    public static ModConfigSpec.IntValue SHOTGUN_FIST_BULLETS;
    public static ModConfigSpec.IntValue SHOTGUN_FIST_COOLDOWN;
    public static ModConfigSpec.IntValue PUMP_CHARGE_SHOTGUN_BULLETS;
    public static ModConfigSpec.IntValue PUMP_CHARGE_SHOTGUN_COOLDOWN;
    public static ModConfigSpec.DoubleValue SLUG_BLOCK_DAMAGE_STRENGTH;
    public static ModConfigSpec.IntValue COUNTERFORCE_IFRAMES;
    public static ModConfigSpec.IntValue WILL_O_WISP_STACK_SIZE;
    public static ModConfigSpec.BooleanValue STACKABLE_POTIONS;

    // ENTITY
    public static ModConfigSpec.DoubleValue WISP_BOREDOM_CHANCE;
    public static ModConfigSpec.IntValue BLACK_ICE_FREEZING_TICKS;
    public static ModConfigSpec.BooleanValue SKELETON_FOSSILIZATION;
    public static ModConfigSpec.BooleanValue WITHER_SKELETON_FOSSILIZATION;
    public static ModConfigSpec.IntValue WISPS_DROPPED_BY_APPARITION;
    public static ModConfigSpec.IntValue APPARITION_POSSESSION_COOLDOWN;
    public static ModConfigSpec.BooleanValue APPARITIONS_CAN_BE_SALTED;
    public static ModConfigSpec.BooleanValue APPARITIONS_CAN_POSSESS_MOBS;
    public static ModConfigSpec.BooleanValue APPARITIONS_CAN_POSSESS_GARGOYLES;
    public static ModConfigSpec.BooleanValue POSSESSED_MOBS_UNLEASH_APPARITION;
    public static ModConfigSpec.DoubleValue HARD_DIFFICULTY_UNLEASHING_MULTIPLIER;
    public static ModConfigSpec.BooleanValue PROJECTILES_PHASE_THROUGH_GHOSTS;
    public static ModConfigSpec.BooleanValue DIMINISHING_BLAZES;
    public static ModConfigSpec.IntValue MIN_VESSEL_BULLETS;
    public static ModConfigSpec.IntValue MAX_VESSEL_BULLETS;
    public static ModConfigSpec.IntValue VESSEL_ATTACK_TIME;
    public static ModConfigSpec.IntValue VESSEL_SHOOTS_AT_ATTACK_TIME;
    public static ModConfigSpec.IntValue VESSEL_ACCURATE_DISTANCE;
    public static ModConfigSpec.BooleanValue BANSHEE_TELEPORTS_AFTER_HIT;
    public static ModConfigSpec.IntValue BANSHEE_ANCHOR_INTERVAL;
    public static ModConfigSpec.IntValue BANSHEE_ATTACK_INTERVAL;
    public static ModConfigSpec.IntValue BANSHEE_ATTACK_INTERVAL_BONUS;
    public static ModConfigSpec.IntValue BANSHEE_ATTACK_INTERVAL_STAGGER;
    public static ModConfigSpec.IntValue BANSHEE_STUN_TIMER;
    public static ModConfigSpec.EnumValue<BansheeRedirectConfig> BANSHEE_REDIRECT_STUNS;
    public static ModConfigSpec.DoubleValue GENERIC_WILL_O_WISP_MANEUVERABILITY;
    public static ModConfigSpec.DoubleValue BANSHEE_WILL_O_WISP_MANEUVERABILITY;
    public static ModConfigSpec.BooleanValue MANEUVERABILITY_AFFECTED_BY_DIFFICULTY;
    public static ModConfigSpec.BooleanValue WILL_O_WISP_WIND_PROPULSION;
    public static ModConfigSpec.BooleanValue ECTO_SLAB_PETRIFY_WITH_GHAST_TEAR;
    public static ModConfigSpec.IntValue PETRIFIED_ECTO_SLAB_SHATTER_DISTURBANCE;
    public static ModConfigSpec.BooleanValue ECTO_SLAB_FORCE_OUT_WITH_SHOVEL;
    public static ModConfigSpec.BooleanValue FORCED_OUT_ECTO_SLAB_FRIENDLY_FIRE;
    public static ModConfigSpec.BooleanValue EXPLOSION_SHATTERS_ECTO_SLAB_STACK;
    public static ModConfigSpec.IntValue EXPLOSION_SHATTER_COOLDOWN;
    public static ModConfigSpec.BooleanValue PETRIFICATION_PETRIFIES_SWIRLS;
    public static ModConfigSpec.IntValue PETRIFICATION_SWIRLS_RANGE;
    public static ModConfigSpec.IntValue PETRIFICATION_SWIRLS_RANGE_PER_STACK;
    public static ModConfigSpec.DoubleValue NATURAL_PETRIFIED_ECTO_SLAB_CHANCE;
    public static ModConfigSpec.IntValue NATURAL_PETRIFIED_ECTO_SLAB_MIN_STACK;
    public static ModConfigSpec.IntValue NATURAL_PETRIFIED_ECTO_SLAB_MAX_STACK;
    public static ModConfigSpec.IntValue NATURAL_ECTO_SLAB_PACK_MIN;
    public static ModConfigSpec.IntValue NATURAL_ECTO_SLAB_PACK_MAX;
    public static ModConfigSpec.DoubleValue EMERGE_BURST_DAMAGE_MULTIPLIER;
    public static ModConfigSpec.IntValue ABSOLUTE_MAXIMUM_STACK_SIZE;
    public static ModConfigSpec.IntValue ECTO_SLAB_EXTRA_JUMP_DELAY;
    public static ModConfigSpec.IntValue ECTO_SLAB_EXTRA_BURROW_COOLDOWN;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_SPEED_PER_STACK;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_SPEED_CAP;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_DAMAGE_PER_STACK;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_DAMAGE_CAP;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_KNOCKBACK_RESISTANCE_PER_STACK;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_KNOCKBACK_RESISTANCE_CAP;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_JUMP_STRENGTH_PER_STACK;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_JUMP_STRENGTH_CAP;
    public static ModConfigSpec.IntValue ECTO_SLAB_MAX_STACK_SIZE;
    public static ModConfigSpec.IntValue ECTO_SLAB_MAX_DIG_TIME;
    public static ModConfigSpec.DoubleValue VESSEL_UNLEASHING_ODDS;
    public static ModConfigSpec.DoubleValue ECTO_SLAB_UNLEASHING_ODDS;
    public static ModConfigSpec.DoubleValue BANSHEE_UNLEASHING_ODDS;
    public static ModConfigSpec.DoubleValue STAMPEDE_UNLEASHING_ODDS;
    public static ModConfigSpec.DoubleValue STAMPEDE_STRIDITE_SHEDDING_CHANCE;
    public static ModConfigSpec.IntValue MIN_STAMPEDE_STRIDITE_DROPS;
    public static ModConfigSpec.IntValue MAX_STAMPEDE_STRIDITE_DROPS;

    // WORLD SETTINGS
    public static ModConfigSpec.BooleanValue NETHER_WORLDGEN_OVERHAUL;
    public static ModConfigSpec.BooleanValue IMPROVED_NETHER_BIOME_SOURCE;
    public static ModConfigSpec.BooleanValue BRIGHTER_NETHER_FOG;
    public static ModConfigSpec.BooleanValue RED_NETHER_WASTES_FOG;
    public static ModConfigSpec.BooleanValue BETTER_SOUL_SAND_VALLEY_PARTICLES;

    // BIOME FEATURES
    public static ModConfigSpec.BooleanValue BONE_PIKE;
    public static ModConfigSpec.BooleanValue ECTO_SOUL_SAND;
    public static ModConfigSpec.BooleanValue ECTOPLASM_LAKE;
    public static ModConfigSpec.BooleanValue FOSSIL_FUEL_ORE;
    public static ModConfigSpec.BooleanValue FOSSIL_ORE;
    public static ModConfigSpec.BooleanValue MOUND;
    public static ModConfigSpec.BooleanValue SOUL_MAGMA;
    public static ModConfigSpec.BooleanValue PALE_SOUL_SLATE;
    public static ModConfigSpec.BooleanValue SOUL_SWIRLS;
    public static ModConfigSpec.BooleanValue BLOTTED_NETHERRACK;
    public static ModConfigSpec.BooleanValue SILTMARRAM;
    public static ModConfigSpec.BooleanValue NETHERRACK_SPELEOTHEM;
    public static ModConfigSpec.BooleanValue PYROCLAST_CRUSTS;
    public static ModConfigSpec.BooleanValue SILT_FLINT_ORE;
    public static ModConfigSpec.BooleanValue REMOVE_NETHER_NOODLE_CAVES;

    // VISUAL AND SOUND
    public static ModConfigSpec.BooleanValue UNCAPPED_NETHER_FOG_DISTANCE;
    public static ModConfigSpec.BooleanValue BLACK_ICE_PARTICLES;
    public static ModConfigSpec.BooleanValue ECTOPLASM_PARTICLES;
    public static ModConfigSpec.BooleanValue ECTOPLASM_SOUNDS;
    public static ModConfigSpec.BooleanValue NETHER_MIST_PARTICLES;
    public static ModConfigSpec.IntValue NETHER_MIST_SPAWN_RATE;
    public static ModConfigSpec.DoubleValue NETHER_MIST_MIN_DISTANCE;
    public static ModConfigSpec.DoubleValue NETHER_MIST_MAX_DISTANCE;
    public static ModConfigSpec.DoubleValue NETHER_MIST_SCALE;
    public static ModConfigSpec.DoubleValue NETHER_MIST_OPACITY;
    public static ModConfigSpec.DoubleValue NETHER_MIST_MOTION_MULTIPLIER;
    public static ModConfigSpec.DoubleValue NETHER_MIST_DISSIPATE_DISTANCE;
    public static ModConfigSpec.DoubleValue SOUL_SAND_VALLEY_WIND_SPEED;
    public static ModConfigSpec.DoubleValue WINDY_ASH_SCALE_MULTIPLIER;
    public static ModConfigSpec.IntValue DRIFTING_SOULS_SPAWN_QUANTITY;
    public static ModConfigSpec.IntValue DRIFTING_SOULS_SPAWN_RADIUS;
    public static ModConfigSpec.BooleanValue WILL_O_WISP_PARTICLES;
    public static ModConfigSpec.BooleanValue WILL_O_WISP_SOUNDS;
    public static ModConfigSpec.BooleanValue IMPROVED_FIRE_PARTICLES;
    public static ModConfigSpec.BooleanValue FIRE_SMOKE_PARTICLES;
    public static ModConfigSpec.BooleanValue FIRE_EMBER_PARTICLES;
    public static ModConfigSpec.BooleanValue LAVA_PERLIN_NOISE_GRADIENT;
    public static ModConfigSpec.ConfigValue<String> LAVA_GRADIENT_COLOR_A;
    public static ModConfigSpec.ConfigValue<String> LAVA_GRADIENT_COLOR_B;
    public static ModConfigSpec.DoubleValue LAVA_GRADIENT_NOISE_SCALE;
    public static ModConfigSpec.BooleanValue SHOTGUN_SCREENSHAKE;
    public static ModConfigSpec.BooleanValue ECTO_SLAB_EMERGE_BURST_SCREENSHAKE;
    public static ModConfigSpec.BooleanValue ECTO_SLAB_PETRIFICATION_SCREENSHAKE;
    public static ModConfigSpec.BooleanValue ENABLE_HEAT_DISTORTION;
    public static ModConfigSpec.BooleanValue BIOME_HEAT_DISTORTION;
    public static ModConfigSpec.BooleanValue LAVA_HEAT_DISTORTION;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_INTENSITY;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_SPEED;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_MIN_DISTANCE;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_MAX_DISTANCE;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_LAVA_INTENSITY;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_LAVA_SPEED;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_LAVA_MIN_DISTANCE;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_LAVA_MAX_DISTANCE;
    public static ModConfigSpec.IntValue HEAT_DISTORTION_LAVA_CHECK_PERIOD;
    public static ModConfigSpec.DoubleValue HEAT_DISTORTION_LAVA_PROXIMITY;
    public static ModConfigSpec.IntValue REQUIRED_SOURCE_BLOCKS_FOR_LAVA_HEAT_DISTORTION;
    public static ModConfigSpec.BooleanValue ENABLE_SOUL_GLASS_SCREEN_FILTER;
    public static ModConfigSpec.BooleanValue NO_SOUL_GLASS_RIPPLE;
    public static ModConfigSpec.BooleanValue IMPROVED_BREWING_STAND_PARTICLES;
    public static ModConfigSpec.BooleanValue POTION_CONSUMPTION_PARTICLES;
    public static ModConfigSpec.BooleanValue IMPROVED_NETHER_PORTAL_PARTICLES;
    public static ModConfigSpec.BooleanValue ENABLE_NETHER_BIOME_LIGHTMAPS;
    public static ModConfigSpec.BooleanValue CIERGE_OF_TREACHERY_RED_LIGHTS;
    public static ModConfigSpec.BooleanValue CIERGE_OF_TREACHERY_FOG;
    public static ModConfigSpec.BooleanValue CIERGE_OF_TREACHERY_PARTICLES;

    // GAME MECHANICS
    public static ModConfigSpec.BooleanValue ECTOPLASM_FREEZES;
    public static ModConfigSpec.BooleanValue ECTOPLASM_RUSTS_NETHERITE;
    public static ModConfigSpec.DoubleValue SILVER_PARANORMAL_DAMAGE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue SILVER_PARANORMAL_PROTECTION_DAMAGE;
    public static ModConfigSpec.BooleanValue SILVER_PARANORMAL_INFLICTS_SLOWNESS;
    public static ModConfigSpec.BooleanValue DEVELOPER_MODE;
    public static ModConfigSpec.BooleanValue SHOW_BETA_WARNING_POPUP;

    // STARTUP
    public static ModConfigSpec.BooleanValue ENABLE_JNE_SPLASH_TEXTS;
    public static ModConfigSpec.BooleanValue RED_SPLASH_TEXT;
    public static ModConfigSpec.EnumValue<ProfanityConfig> PROFANITY;

    // PACK
    public static ModConfigSpec.BooleanValue SOUL_CAMPFIRE_SOUL_SOIL;
}
