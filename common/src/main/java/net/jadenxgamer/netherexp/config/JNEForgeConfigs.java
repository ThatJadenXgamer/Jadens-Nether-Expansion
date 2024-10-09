package net.jadenxgamer.netherexp.config;

import net.jadenxgamer.netherexp.config.enums.*;
import net.minecraftforge.common.ForgeConfigSpec;
import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class JNEForgeConfigs {
    public static ForgeConfigSpec COMMON;

    private static void registerBlockConfigs(ForgeConfigSpec.Builder BUILDER) {
        SHOULD_NETHER_VINES_GROW_SPORES = BUILDER
                .comment("Nether vines have a random chance to grow their biomes' respective spores if they haven't reached maturity")
                .define("should_nether_vines_grow_spores", true);
        ENABLE_NYLIUM_PATHS = BUILDER
                .comment("Nylium can be flattened with a shovel to make Nylium Paths")
                .define("enable_nylium_paths", true);
        ENABLE_SOUL_PATHS = BUILDER
                .comment("Soul Soil can be flattened with a shovel to make Soul Paths")
                .define("enable_soul_paths", true);
        RENEWABLE_FOSSIL_FUEL = BUILDER
                .comment("Fossil Ore that is covered on all faces slowly turns to Fossil Fuel")
                .define("renewable_fossil_fuel", true);
        GEYSER_COOLDOWN = BUILDER
                .comment("Defines how many seconds a geyser will go into cooldown for \nsetting to \"0\" functionally disables the cooldown mechanic")
                .define("geyser_cooldown", 5);
        GEYSER_PUSH_VELOCITY = BUILDER
                .comment("Defines how much vertical velocity you'll gain with a geyser")
                .defineInRange("geyser_push_velocity", 1.2, 0.0, 5.0);
        SPORESHROOM_PUSH_VELOCITY = BUILDER
                .comment("Defines how much vertical velocity you'll gain with a sporeshroom")
                .defineInRange("sporeshroom_push_velocity", 1.0, 0.0, 5.0);
        BRAZIER_CHEST_REFILL_COOLDOWN = BUILDER
                .comment("Unlocked Brazier Chests will refill loot after the specified time in seconds has elapsed")
                .defineInRange("brazier_chest_refill_cooldown", 3600, 0, 86400);
        TREACHEROUS_CANDLE_COMPLETION_COOLDOWN = BUILDER
                .comment("Completed Treacherous Candles will become active again after the specified time in seconds has elapsed")
                .defineInRange("treacherous_candle_completion_cooldown", 1800, 0, 86400);
        FROGMIST_BREAKABLE_BY_FIST = BUILDER
                .comment("If enabled frogmist blocks can be broken by hand otherwise it's hitbox only shows up when holding a hoe or frogmist in hand")
                .define("frogmist_breakable_by_fist", false);
        MAGMA_CREAM_BLOCK_DOUSES_FIRE = BUILDER
                .comment("Stepping into Magma Cream Block will put you out if you're on fire")
                .define("magma_cream_block_douses_fire", true);
        SHOTGUN_BARREL_BULLETS = BUILDER
                .comment("Defines how many bullets will be shot when a Shotgun Barrel is powered")
                .define("shotgun_barrel_bullets", 10);
        SOUL_MAGMA_DAMAGE_TYPE = BUILDER
                .comment("Defines what causes Soul Magma Blocks to damage you \nSPRINTING - Only damages when sprinting on the block \nALWAYS - Damages you unless sneaking \n ")
                .defineEnum("soul_magma_damage_type", SoulMagmaDamageType.SPRINTING);
        SOUL_SWIRLS_COOLDOWN = BUILDER
                .comment("Defines how many seconds a Soul Swirl is in cooldown for when activated")
                .define("soul_swirls_cooldown", 50);
        UNBOUNDED_SPEED_DURATION = BUILDER
                .comment("Defines how many seconds the player will be inflicted with Unbounded Speed when passing Soul Swirls")
                .define("unbounded_speed_duration", 10);
        SOUL_SWIRLS_BONE_MEAL_BEHAVIOR = BUILDER
                .comment("Defines what happens when Soul Swirls are bone mealed \nDROPS - Drops more of itself when bone mealed \nDUPLICATES - Places another soul swirl beside it \nDISABLED - Disables bone mealing soul swirls \n ")
                .defineEnum("soul_swirls_bone_meal_behavior", SoulSwirlsBoneMeal.DUPLICATES);
        SCULK_GRINDER_EXPERIENCE = BUILDER
                .comment("Defines how much experience is dropped when treacherous flames are grinded down")
                .define("sculk_grinder_experience", 450);
    }

    private static void registerItemConfigs(ForgeConfigSpec.Builder BUILDER) {
        POTION_STACK_SIZE = BUILDER
                .comment("Defines the stack size for all Potions & Antidotes \nVanilla value is \"1\"")
                .defineInRange("potion_stack_size", 16, 1, 64);
        WILL_O_WISP_STACK_SIZE = BUILDER
                .comment("Defines the stack size for Will O' Wisps")
                .defineInRange("will_o_wisp_stack_size", 16, 1, 64);
    }

    private static void registerEntityConfigs(ForgeConfigSpec.Builder BUILDER) {
        REDESIGNED_FIREBALLS = BUILDER
                .comment("Ghast and Blaze Fireballs will be redesigned if enabled")
                .define("redesigned_fireballs", true);
        DIMINISHING_BLAZES = BUILDER
                .comment("Blazes get dimmer as their health decreases like in Minecraft Dungeons")
                .define("diminishing_blazes", true);
        PIXEL_CONSISTENT_MAGMA_CUBES = BUILDER
                .comment("Magma Cube texture changes depending on it's size so that it's no longer mixels \nThe New Textures used are from FaithFul 32x and 64x respectively")
                .define("pixel_consistent_magma_cubes", true);
        SKELETON_FOSSILIZATION = BUILDER
                .comment("Skeletons when killed fossilize soul soil blocks underneath into Fossil Ore")
                .define("skeleton_fossilization", true);
        WITHER_SKELETON_FOSSILIZATION = BUILDER
                .comment("Wither Skeletons when killed fossilize soul soil blocks underneath into Fossil Fuel Ore")
                .define("wither_skeleton_fossilization", true);
        PHASMOPHOBIC_MOBS = BUILDER
                .comment("Mobs will be scared to pathfind through Soul Glass")
                .define("phasmophobic_mobs", true);
        HOGLIN_DROPS_HOGHAM = BUILDER
                .comment("Hoglins will drop hogham instead of porkchops")
                .define("hoglin_drops_hogham", true);
        WITHER_SKELETON_DROPS_FOSSIL_FUEL = BUILDER
                .comment("Wither Skeletons will drop fossil fuel instead of coal")
                .define("wither_skeleton_drops_fossil_fuel", false);
        WISP_EMERGING_CHANCE = BUILDER
                .comment("Defines 1 in specified chance for how often wisps emerge from ecto soul sand blocks")
                .define("wisp_emerging_chance", 50);
        SUSPICIOUS_SOUL_SAND_FROM_WISP_EMERGING = BUILDER
                .comment("Wisps will turn the soul sand they emerged from into Suspicious Soul Sand")
                .define("suspicious_soul_sand_from_wisp_emerging", true);
        ECTO_SLAB_EMERGING_BEHAVIOR = BUILDER
                .comment("Defines how ecto slabs will emerge from soul swirls \nALWAYS - has a chance to emerge whenever passed through \nUNBOUNDED_SPEED_ONLY - can emerge only if inflicted with unbounded speed \nNEVER - never emerges from soul swirls \n ")
                .defineEnum("ecto_slab_emerging_behavior", EctoSlabEmerging.UNBOUNDED_SPEED_ONLY);
        ECTO_SLAB_EMERGING_CHANCE = BUILDER
                .comment("Defines 1 in specified chance for how often Ecto Slabs emerge from soul swirls ")
                .define("ecto_slab_emerging_chance", 40);
        ECTO_SLAB_EMERGING_CHANCE_WITH_UNBOUNDED_SPEED = BUILDER
                .comment("Defines 1 in specified chance for how often Ecto Slabs emerge from soul swirls if the player has Unbounded Speed")
                .define("ecto_slab_emerging_with_unbounded_speed", 5);
    }

    private static void registerGameMechanicConfigs(ForgeConfigSpec.Builder BUILDER) {
        NETHER_FOG_DISTANCE = BUILDER
                .comment("Changes how close the nether fog is to the player \nVANILLA - Fog caps at 12 chunks \nMEDIUM - Changes with render distance \nFAR - Behaves like Medium but with even greater distance \nDISABLED - Turns off the nether fog \n ")
                .defineEnum("nether_fog_distance", NetherFogDistance.MEDIUM);
        LARGER_NETHER_BIOMES = BUILDER
                .comment("Increases the size of all nether biomes, great for experiencing the new biome overhauls \nWARNING: this setting has to be enabled before you generate a new world \nonce enabled in a world it cannot be disabled without deleting DIM-1 in the world folder")
                .define("larger_nether_biomes", false);
        AMPLIFIER_IMMUNITY_REDUCTION = BUILDER
                .comment("Defines if Immunity Effect duration reduction is scaled by the incoming effect amplifier")
                .define("amplifier_immunity_reduction", true);
        ECTOPLASM_RUSTS_NETHERITE = BUILDER
                .comment("Ectoplasm will rust all nearby plated netherite blocks it comes in contact with")
                .define("ectoplasm_rusts_netherite", true);
        REDUCE_SOUL_SAND_SLOWNESS = BUILDER
                .comment("Soul Sand movement speed reduction will be less intrusive when enabled")
                .define("reduce_soul_sand_slowness", false);
        REMOVE_SOUL_SPEED_DURABILITY_PENALTY = BUILDER
                .comment("Soul Speed will no longer damage your boots when enabled")
                .define("remove_soul_speed_durability_penalty", false);
    }

    private static void registerParticlesAndSoundsConfigs(ForgeConfigSpec.Builder BUILDER) {
        IMPROVED_SOUL_FIRE_PARTICLES = BUILDER
                .comment("Soul Fire will emit unique particles instead of smoke")
                .define("improved_soul_fire_particles", true);
        ENABLE_BLACK_ICE_PARTICLES = BUILDER
                .comment("Black will produce aerosol particles")
                .define("enable_black_ice_particles", true);
        ENABLE_ECTOPLASM_PARTICLES = BUILDER
                .comment("Ectoplasm will produce light rays and rising particles")
                .define("enable_ectoplasm_particles", true);
        ENABLE_ECTOPLASM_SOUNDS = BUILDER
                .comment("Ectoplasm will occasionally produce whispering sounds")
                .define("enable_ectoplasm_sounds", true);
        BLACKSTONE_SOUNDS = BUILDER
                .comment("Blackstone will produce unique sounds")
                .define("blackstone_sounds", true);
        POLISHED_BLACKSTONE_SOUNDS = BUILDER
                .comment("Polished Blackstone will produce unique sounds")
                .define("polished_blackstone_sounds", true);
        MAGMA_BLOCK_SOUNDS = BUILDER
                .comment("Magma Blocks will produce unique sounds")
                .define("magma_block_sounds", true);
        GLOWSTONE_SOUNDS = BUILDER
                .comment("Glowstone and Redstone Lamps will produce unique sounds")
                .define("glowstone_sounds", true);
        QUARTZ_BLOCK_SOUNDS = BUILDER
                .comment("Quartz Blocks will produce unique sounds")
                .define("quartz_sounds", true);
    }

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        BUILDER.comment("Block Settings").push("blocks");
        registerBlockConfigs(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Item Settings").push("items");
        registerItemConfigs(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Entity Settings").push("entities");
        registerEntityConfigs(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Game Mechanic Settings").push("game_mechanics");
        registerGameMechanicConfigs(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Particle & Sound Settings").push("particles_and_sounds");
        registerParticlesAndSoundsConfigs(BUILDER);
        BUILDER.pop();

        COMMON = BUILDER.build();
    }
}
