package net.jadenxgamer.netherexp.config;

import net.jadenxgamer.netherexp.config.enums.BansheeRedirectConfig;
import net.jadenxgamer.netherexp.config.enums.ProfanityConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class JNEConfigs {

    // BLOCK
    public static ModConfigSpec.DoubleValue FOSSIL_ORE_CONVERSION_CHANCE;
    public static ModConfigSpec.DoubleValue SOUL_GLASS_MOVEMENT_SLOWDOWN;
    public static ModConfigSpec.IntValue SOUL_SWIRLS_COOLDOWN;
    public static ModConfigSpec.IntValue UNBOUNDED_SPEED_DURATION;
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

    // ITEM
    public static ModConfigSpec.IntValue IMMUNITY_CONSUMPTION;
    public static ModConfigSpec.BooleanValue AMPLIFIER_SCALES_IMMUNITY_CONSUMPTION;
    public static ModConfigSpec.BooleanValue BACKPORT_TEARS_MUSIC_DISC;
    public static ModConfigSpec.BooleanValue TWEAK_OBTAINING_TEARS_MUSIC_DISC;
    public static ModConfigSpec.IntValue SHOTGUN_FIST_BULLETS;
    public static ModConfigSpec.IntValue PUMP_CHARGE_SHOTGUN_BULLETS;
    public static ModConfigSpec.IntValue WILL_O_WISP_STACK_SIZE;

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
    public static ModConfigSpec.DoubleValue VESSEL_UNLEASHING_ODDS;
    public static ModConfigSpec.DoubleValue STAMPEDE_UNLEASHING_ODDS;
    public static ModConfigSpec.DoubleValue BANSHEE_UNLEASHING_ODDS;
    public static ModConfigSpec.DoubleValue STAMPEDE_STRIDITE_SHEDDING_CHANCE;
    public static ModConfigSpec.IntValue MIN_STAMPEDE_STRIDITE_DROPS;
    public static ModConfigSpec.IntValue MAX_STAMPEDE_STRIDITE_DROPS;

    // WORLD SETTINGS
    public static ModConfigSpec.BooleanValue NETHER_WORLDGEN_OVERHAUL;
    public static ModConfigSpec.BooleanValue BRIGHTER_NETHER_FOG;
    public static ModConfigSpec.BooleanValue RED_NETHER_WASTES_FOG;
    public static ModConfigSpec.BooleanValue BETTER_SOUL_SAND_VALLEY_PARTICLES;

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
    public static ModConfigSpec.DoubleValue SOUL_SAND_VALLEY_WIND_SPEED;
    public static ModConfigSpec.DoubleValue WINDY_ASH_SCALE_MULTIPLIER;
    public static ModConfigSpec.IntValue DRIFTING_SOULS_SPAWN_QUANTITY;
    public static ModConfigSpec.BooleanValue WILL_O_WISP_PARTICLES;
    public static ModConfigSpec.BooleanValue WILL_O_WISP_SOUNDS;
    public static ModConfigSpec.BooleanValue ENABLE_JNE_SPLASH_TEXTS;
    public static ModConfigSpec.BooleanValue RED_SPLASH_TEXT;
    public static ModConfigSpec.EnumValue<ProfanityConfig> PROFANITY;

    // GAME MECHANICS
    public static ModConfigSpec.BooleanValue ECTOPLASM_FREEZES;
    public static ModConfigSpec.BooleanValue ECTOPLASM_RUSTS_NETHERITE;
    public static ModConfigSpec.DoubleValue SILVER_PARANORMAL_DAMAGE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue SILVER_PARANORMAL_PROTECTION_DAMAGE;
    public static ModConfigSpec.BooleanValue SILVER_PARANORMAL_INFLICTS_SLOWNESS;
    public static ModConfigSpec.BooleanValue DEVELOPER_MODE;
}
