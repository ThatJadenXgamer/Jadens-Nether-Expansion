package net.jadenxgamer.netherexp.config;

import net.jadenxgamer.netherexp.config.enums.*;

import java.util.function.Supplier;

public class JNEConfigs {
    // Blocks
    public static Supplier<Boolean> SHOULD_NETHER_VINES_GROW_SPORES = () -> true;
    public static Supplier<Boolean> RENEWABLE_FOSSIL_FUEL = () -> true;
    public static Supplier<Integer> GEYSER_COOLDOWN = () -> 5;
    public static Supplier<Double> GEYSER_PUSH_VELOCITY = () -> 1.2;
    public static Supplier<Double> SPORESHROOM_PUSH_VELOCITY = () -> 1.0;
    public static Supplier<Integer> BRAZIER_CHEST_REFILL_COOLDOWN = () -> 3600;
    public static Supplier<Integer> TREACHEROUS_CANDLE_COMPLETION_COOLDOWN = () -> 1800;
    public static Supplier<Boolean> FROGMIST_BREAKABLE_BY_FIST = () -> false;
    public static Supplier<Boolean> MAGMA_CREAM_BLOCK_DOUSES_FIRE = () -> true;
    public static Supplier<Integer> SHOTGUN_BARREL_BULLETS = () -> 10;
    public static Supplier<SoulMagmaDamageType> SOUL_MAGMA_DAMAGE_TYPE = () -> SoulMagmaDamageType.SPRINTING;
    public static Supplier<Integer> SOUL_SWIRLS_COOLDOWN = () -> 50;
    public static Supplier<Integer> UNBOUNDED_SPEED_DURATION = () -> 10;
    public static Supplier<SoulSwirlsBoneMeal> SOUL_SWIRLS_BONE_MEAL_BEHAVIOR = () -> SoulSwirlsBoneMeal.DUPLICATES;
    public static Supplier<Integer> SCULK_GRINDER_EXPERIENCE = () -> 450;
    public static Supplier<Boolean> SHOULD_SORROWSQUASH_FALL = () -> true;
    public static Supplier<Double> SORROWSQUASH_GROWTH_CHANCE = () -> 0.2;
    public static Supplier<Double> SORROWSQUISHED_DAMAGE_MULTIPLIER = () -> 1.5;
    public static Supplier<Integer> SORROWSQUISHED_MAX_DAMAGE = () -> 30;
    public static Supplier<Integer> HAZE_BLOCK_COOLDOWN = () -> 8;

    // Items
    public static Supplier<Integer> WILL_O_WISP_STACK_SIZE = () -> 16;
    public static Supplier<Integer> POTION_STACK_SIZE = () -> 16;
    public static Supplier<Boolean> FORCE_DISABLE_POTION_STACK_SIZE = () -> false;
    public static Supplier<Double> JACKHAMMER_FIST_MAX_DAMAGE = () -> 40.0;
    public static Supplier<Integer> BLACK_ICICLE_FREEZE_TICKS = () -> 100;
    public static Supplier<Integer> SHOTGUN_FIST_BULLETS = () -> 25;
    public static Supplier<Integer> PUMP_CHARGE_SHOTGUN_BULLETS = () -> 10;

    // Entities
    public static Supplier<Boolean> DIMINISHING_BLAZES = () -> true;
    public static Supplier<Boolean> PIXEL_CONSISTENT_MAGMA_CUBES = () -> true;
    public static Supplier<Boolean> SKELETON_FOSSILIZATION = () -> true;
    public static Supplier<Boolean> WITHER_SKELETON_FOSSILIZATION = () -> true;
    public static Supplier<Boolean> PHASMOPHOBIC_MOBS = () -> true;
    public static Supplier<Boolean> HOGLIN_DROPS_HOGHAM = () -> true;
    public static Supplier<Boolean> WITHER_SKELETON_DROPS_FOSSIL_FUEL = () -> false;
    public static Supplier<Integer> WISP_EMERGING_CHANCE = () -> 50;
    public static Supplier<Boolean> SUSPICIOUS_SOUL_SAND_FROM_WISP_EMERGING = () -> true;
    public static Supplier<Boolean> SUSPICIOUS_SOUL_SAND_DECAYS = () -> true;
    public static Supplier<Integer> SUSPICIOUS_SOUL_SAND_DECAY_ODDS = () -> 2;
    public static Supplier<String> SUSPICIOUS_SOUL_SAND_DEFAULT_LOOT_TABLE = () -> "archaeology/wisp_arch_default";
    public static Supplier<EctoSlabEmerging> ECTO_SLAB_EMERGING_BEHAVIOR = () -> EctoSlabEmerging.UNBOUNDED_SPEED_ONLY;
    public static Supplier<Integer> ECTO_SLAB_EMERGING_CHANCE = () -> 40;
    public static Supplier<Integer> ECTO_SLAB_EMERGING_CHANCE_WITH_UNBOUNDED_SPEED = () -> 10;

    // Game Mechanics
    public static Supplier<NetherFogDistance> NETHER_FOG_DISTANCE = () -> NetherFogDistance.MEDIUM;
    public static Supplier<Boolean> LARGER_NETHER_BIOMES = () -> false;
    public static Supplier<Boolean> AMPLIFIER_IMMUNITY_REDUCTION = () -> false;
    public static Supplier<Boolean> ECTOPLASM_RUSTS_NETHERITE = () -> true;
    public static Supplier<Boolean> ECTOPLASM_FREEZING_DAMAGE = () -> true;
    public static Supplier<Boolean> REDUCE_SOUL_SAND_SLOWNESS = () -> false;
    public static Supplier<Boolean> REMOVE_SOUL_SPEED_DURABILITY_PENALTY = () -> false;
    public static Supplier<Boolean> DEV_TEST_MODE = () -> false;

    // Sub Biomes

    public static Supplier<Boolean> ENABLE_SUB_BIOMES = () -> true;
    public static Supplier<Boolean> BLACK_ICE_GLACIERS = () -> true;
    public static Supplier<Double> BLACK_ICE_GLACIERS_RARITY = () -> 0.085;
    public static Supplier<Integer> BLACK_ICE_GLACIERS_SIZE = () -> 128;

    // Visuals & Sounds
    public static Supplier<Boolean> IMPROVED_FIREBALL_PARTICLES = () -> true;
    public static Supplier<Boolean> IMPROVED_SOUL_FIRE_PARTICLES = () -> true;
    public static Supplier<Boolean> ENABLE_BLACK_ICE_PARTICLES = () -> true;
    public static Supplier<Boolean> ENABLE_ECTOPLASM_PARTICLES = () -> true;
    public static Supplier<Boolean> ENABLE_ECTOPLASM_SOUNDS = () -> true;
    public static Supplier<Boolean> TREACHEROUS_CANDLE_RED_LIGHTS = () -> true;
    public static Supplier<Boolean> TREACHEROUS_CANDLE_FOG = () -> true;
    public static Supplier<Boolean> TREACHEROUS_CANDLE_PARTICLES = () -> true;
}
