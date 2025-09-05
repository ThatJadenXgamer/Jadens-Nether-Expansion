package net.jadenxgamer.netherexp.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class JNEConfigImpl {

    public static ModConfigSpec CONFIG;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        BUILDER.comment("Block Settings").push("blockSettings");
        JNEConfigImpl.BlockSettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Item Settings").push("itemSettings");
        JNEConfigImpl.ItemSettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Entity Settings").push("entitySettings");
        JNEConfigImpl.EntitySettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("World Settings").push("worldSettings");
        JNEConfigImpl.WorldSettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Visual & Sound Settings").push("visualAndSoundSettings");
        JNEConfigImpl.VisualAndSoundSettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Game Mechanic Settings").push("gameMechanicSettings");
        JNEConfigImpl.GameMechanicSettings.init(BUILDER);
        BUILDER.pop();

        CONFIG = BUILDER.build();
    }

    public static class BlockSettings {

        public static void init(ModConfigSpec.Builder builder) {
            FOSSIL_ORE_CONVERSION_CHANCE = builder
                    .comment("The chance for buried fossil ore to convert into fossil fuel every random tick")
                    .defineInRange("fossilOreConversionChance", 0.02, 0.0, 1.0);
            SOUL_GLASS_MOVEMENT_SLOWDOWN = builder
                    .comment("Intensity of movement speed reduction when submerged in soul glass")
                    .defineInRange("soulGlassMovementSlowdown", 0.6, 0.0, 1.0);
            SOUL_SWIRLS_COOLDOWN = builder
                    .comment("Defines how long it should take for the soul swirls to deactivate in seconds")
                    .defineInRange("soulSwirlsCooldown", 50, 0, Integer.MAX_VALUE);
            UNBOUNDED_SPEED_DURATION = builder
                    .comment("Duration of unbounded speed when it is inflicted from soul swirls\nSetting to 0 functionally disables it")
                    .defineInRange("unboundedSpeedDuration", 10, 0, Integer.MAX_VALUE);
            BONE_MEAL_SOUL_SWIRLS = builder
                    .comment("Weather soul swirls can be bone-mealed to duplicate or not")
                    .define("boneMealSoulSwirls", true);
            BRUSH_WISPS_OUT = builder
                    .comment("If enabled wisps can be forced out of ecto soul sand with a brush")
                    .define("brushWispsOut", true);
            WISP_EMERGING_CHANCE = builder
                    .comment("The chance for a wisp to come out of ecto soul sand")
                    .defineInRange("wispEmergingChance", 0.02, 0.0, 1.0);
            WISP_EMERGING_CHANCE_BRUSH = builder
                    .comment("The chance for a wisp to be tickled out of ecto soul sand with a brush")
                    .defineInRange("wispEmergingChanceBrush", 0.07, 0.0, 1.0);
            ECTO_SOUL_SAND_BRUSH_DAMAGE = builder
                    .comment("Durability penalty for tickling ecto soul sand to force wisps out")
                    .defineInRange("ectoSoulSandBrushDamage", 4, 0, Integer.MAX_VALUE);
            CONVERTS_TO_SUSPICIOUS_SOUL_SAND = builder
                    .comment("Weather ecto soul sand converts to suspicious soul sand by exiting wisps")
                    .define("convertsToSuspiciousSoulSand", true);
            WISP_ARCHAEOLOGY_DEFAULT_LOOT_TABLE = builder
                    .comment("Weather soul swirls can be bone-mealed to duplicate or not")
                    .define("wispArchaeologyDefaultLootTable", "netherexp:archaeology/wisp_arch_default");
            SUSPICIOUS_SOUL_SAND_DECAY_CHANCE = builder
                    .comment("The chance for non-persistent suspicious soul sand block to decay")
                    .defineInRange("suspiciousSoulSandDecayChance", 0.05, 0.0, 1.0);
            SUSPICIOUS_SOUL_SAND_MAX_DECAY = builder
                    .comment("The amount of decay required for suspicious soul sand to revert back to soul sand")
                    .defineInRange("suspiciousSoulSandMaxDecay", 5, 0, Integer.MAX_VALUE);
            LESION_GROWTH_CHANCE = builder
                    .comment("The chance for lesion blocks to grow a layer back")
                    .defineInRange("lesionGrowthChance", 0.1, 0.0, 1.0);
            LESION_DROPS_PER_HARVEST = builder
                    .comment("The amount of decay required for suspicious soul sand to revert back to soul sand")
                    .defineInRange("lesionDropsPerHarvest", 12, 0, Integer.MAX_VALUE);
            SPORESHROOM_HEIGHT_VELOCITY = builder
                    .comment("Vertical velocity gained from bouncing on a sporeshroom block")
                    .defineInRange("sporeshroomHeightVelocity", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            GEYSER_HEIGHT_VELOCITY = builder
                    .comment("Vertical velocity gained from stepping on a geyser block")
                    .defineInRange("geyserHeightVelocity", 1.2, Double.MIN_VALUE, Double.MAX_VALUE);
            GEYSER_COOLDOWN = builder
                    .comment("Defines how long it should take for the geyser to become active again in seconds")
                    .defineInRange("geyserCooldown", 5, 0, Integer.MAX_VALUE);
            BLACK_ICICLE_GROWTH_CHANCE = builder
                    .comment("When submerged underwater black icicles will have the specified chance to grow longer")
                    .defineInRange("blackIcicleGrowthChance", 0.28, 0.0, 1.0);
            THIN_ICE_BREAKING_CHANCE = builder
                    .comment("Chance for thin black ice to break when stood on")
                    .defineInRange("thinIceBreakingChance", 0.015, 0.0, 1.0);
            THIN_ICE_BREAKING_CHANCE_SPRINTING = builder
                    .comment("Chance for thin black ice to break when sprinting on")
                    .defineInRange("thinIceBreakingChanceSprinting", 0.2, 0.0, 1.0);
            NEARBY_THIN_ICE_BREAKING_CHANCE = builder
                    .comment("Chance for nearby thin black ice to shatter")
                    .defineInRange("nearbyThinIceBreakingChance", 0.4, 0.0, 1.0);
            BLACK_ICE_FROSTS_WATER_CHANCE = builder
                    .comment("Chance for black ice or thin black ice to freeze nearby water into more thin black ice")
                    .defineInRange("blackIceFrostsWaterChance", 0.6, 0.0, 1.0);
            BLACK_ICE_TAINTING = builder
                    .comment("Black ice will taint netherrack into pale soul slate")
                    .define("blackIceTainting", true);
            SORROWSQUASH_TAINTING = builder
                    .comment("Sorrowsquash taints nearby soul ground blocks into sorroweed when bone-mealed")
                    .define("sorrowsquashTainting", true);
            SHOULD_SORROWSQUASH_FALL = builder
                    .comment("Weather sorrowsquashes fall if the stem it's attached to breaks")
                    .define("shouldSorrowsquashFall", true);
            SORROWSQUISHED_DAMAGE_MULTIPLIER = builder
                    .comment("Damage multiplier per block the sorrowsquash falls to inflict as sorrowsquished")
                    .defineInRange("sorrowsquishedDamageMultiplier", 1.5, 0.0, Double.MAX_VALUE);
            SORROWSQUISHED_MAX_DAMAGE = builder
                    .comment("Maximum damage that can be accumulated form sorrowsquash to be inflicted as sorrowsquished")
                    .defineInRange("sorrowsquishedMaxDamage", 30, 0, Integer.MAX_VALUE);
            SORROWSQUASH_GROWTH_CHANCE = builder
                    .comment("Chance for sorrowsquashes to grow from a sorrowsquash stem \n" +
                             "If sorrowsquash fails to grow then the stem will attempt to grow upwards instead")
                    .defineInRange("sorrowsquashGrowthChance", 0.2, 0.0, 1.0);
            CEREBRAGE_GROWTH_CHANCE = builder
                    .comment("Chance for cerebrage skulls to grow to their next stage")
                    .defineInRange("cerebrageGrowthChance", 0.1, 0.0, 1.0);
            MIN_CEREBRAGE_DROPPED = builder
                    .comment("Minimum amount of cerebrage dropped when harvesting a cerebrage skull")
                    .defineInRange("minCerebrageDropped", 3, 0, Integer.MAX_VALUE);
            MAX_CEREBRAGE_DROPPED = builder
                    .comment("Maximum amount of cerebrage dropped when harvesting a cerebrage skull")
                    .defineInRange("maxCerebrageDropped", 6, 0, Integer.MAX_VALUE);
            CEREBRAGE_SEEDS_DROP_CHANCE = builder
                    .comment("Chance for cerebrage skulls to drop an additional seed upon harvesting")
                    .defineInRange("cerebrageSeedsDropChance", 0.05, 0.0, 1.0);
            CEREBRAGE_GROWS_BRAIN_TREES = builder
                    .comment("If cerebrage skulls are bone-mealed past their last growth stage then a brain tree can grow from it")
                    .define("cerebrageGrowsBrainTrees", true);
            SHOTGUN_BARREL_BULLETS = builder
                    .comment("The amount of shotgun pellets fired from a shotgun barrel")
                    .defineInRange("shotgunBarrelBullets", 10, 0, Integer.MAX_VALUE);
            CAN_ANYTHING_BREAK_FROGMIST = builder
                    .comment("Normally frogmists can only be broken when other frogmist or a hoe is held in hand \n" +
                            "This config makes it breakable with anything regardless of what's in your hand")
                    .define("canAnythingBreakFrogmist", false);
        }
    }

    public static class ItemSettings {

        public static void init(ModConfigSpec.Builder builder) {
            IMMUNITY_CONSUMPTION = builder
                    .comment("Amount of duration depleted when immunity effects protect against infliction")
                    .defineInRange("immunityConsumption", 600, 0, Integer.MAX_VALUE);
            AMPLIFIER_SCALES_IMMUNITY_CONSUMPTION = builder
                    .comment("Weather the amount of duration depleted multiplies with the infliction's amplifier")
                    .define("amplifierScalesImmunityConsumption", true);
            BACKPORT_TEARS_MUSIC_DISC = builder
                    .comment("Backports the tears music disc from 1.21.6+")
                    .worldRestart()
                    .define("backportTearsMusicDisc", true);
            TWEAK_OBTAINING_TEARS_MUSIC_DISC = builder
                    .comment("Makes obtaining the tears music disc harder, when enabled requires you to slay a ghast in the overworld \n" +
                             "If disabled then the disc is obtained by redirecting a fireball into a ghast like in vanilla")
                    .worldRestart()
                    .define("tweakObtainingTearsMusicDisc", true);
            SHOTGUN_FIST_BULLETS = builder
                    .comment("The base amount of shotgun pellets fired from a shotgun-fist\n" +
                            "Enchantments can influence the final total of bullets fired")
                    .defineInRange("shotgunFistBullets", 25, 0, Integer.MAX_VALUE);
            PUMP_CHARGE_SHOTGUN_BULLETS = builder
                    .comment("The base amount of shotgun pellets fired from a pump-charge shotgun\n" +
                            "Enchantments and pumps can influence the final total of bullets fired")
                    .defineInRange("pumpChargeShotgunBullets", 10, 0, Integer.MAX_VALUE);
        }
    }

    public static class EntitySettings {

        public static void init(ModConfigSpec.Builder builder) {
            WISP_BOREDOM_CHANCE = builder
                    .comment("The chance for a wisp's boredom counter to increase each second")
                    .defineInRange("wispBoredomChance", 0.02, 0.0, 1.0);
            BLACK_ICE_FREEZING_TICKS = builder
                    .comment("The amount of freezing in ticks that all black ices can inflict")
                    .defineInRange("blackIceFreezingTicks", 100, 0, Integer.MAX_VALUE);
            SKELETON_FOSSILIZATION = builder
                    .comment("Upon death skeletons will fossilize soul soil blocks they are standing on into fossil ore")
                    .worldRestart()
                    .define("skeletonFossilization", true);
            WITHER_SKELETON_FOSSILIZATION = builder
                    .comment("Upon death wither skeletons will fossilize soul soil blocks they are standing on into fossil fuel ore")
                    .worldRestart()
                    .define("witherSkeletonFossilization", true);
            WISPS_DROPPED_BY_APPARITION = builder
                    .comment("The amount of wisps which disperse out of an apparition upon its death")
                    .defineInRange("wispsDroppedByApparition", 2, 0, Integer.MAX_VALUE);
            APPARITION_POSSESSION_COOLDOWN = builder
                    .comment("Cooldown in ticks for how long an apparition needs to wait before re-possessing a mob \n" +
                            "If set to -1 then apparitions unleashed from a possessed mob cannot repossess anything")
                    .defineInRange("apparitionPossessionCooldown", 300, -1, Integer.MAX_VALUE);
            APPARITIONS_CAN_BE_SALTED = builder
                    .comment("Apparitions can be salted... or erm, \"waxed\" until actual salt is added in the future \n" +
                            "Salted apparitions cannot possess any mobs, although will still attack them")
                    .define("apparitionsCanBeSalted", true);
            APPARITIONS_CAN_POSSESS_MOBS = builder
                    .comment("Apparitions can take control of certain mobs they kill and turn into possessed variants")
                    .define("apparitionsCanPossessMobs", true);
            APPARITIONS_CAN_POSSESS_GARGOYLES = builder
                    .comment("Apparitions can use certain gargoyle statues to turn themselves into their possessed variants")
                    .define("apparitionsCanPossessGargoyles", true);
            POSSESSED_MOBS_UNLEASH_APPARITION = builder
                    .comment("Upon death possessed mobs have a chance to unleash an apparition out into the world")
                    .define("possessedMobsUnleashApparition", true);
            HARD_DIFFICULTY_UNLEASHING_MULTIPLIER = builder
                    .comment("Multiplies the individual unleashing odds of a possessed mob by the specified amount")
                    .defineInRange("hardDifficultyUnleashingMultiplier", 2.0, 0.0, Double.MAX_VALUE);
            PROJECTILES_PHASE_THROUGH_GHOSTS = builder
                    .comment("Most projectiles will phase through apparitions and wisps except for a few specific kinds \n" +
                            "Projectiles specified in the \"phantasm_hull_protects_blacklist\" tag still hit these entities regardless")
                    .define("projectilesPhaseThroughGhosts", true);
            DIMINISHING_BLAZES = builder
                    .comment("Blazes will visibly become dimmer the lower their health is much like Minecraft: Dungeons")
                    .define("diminishingBlazes", true);
            MIN_VESSEL_BULLETS = builder
                    .comment("The minimum amount of shotgun pellets fired from a vessel")
                    .defineInRange("minVesselBullets", 16, 0, Integer.MAX_VALUE);
            MAX_VESSEL_BULLETS = builder
                    .comment("The maximum amount of shotgun pellets fired from a vessel")
                    .defineInRange("maxVesselBullets", 20, 0, Integer.MAX_VALUE);
            VESSEL_ATTACK_TIME = builder
                    .comment("Attack timer is a value that decrements when a vessel is aggroed \n" +
                            "It dictates the wait-time between each shotgun blast the vessel does")
                    .defineInRange("vesselAttackTime", 110, 0, Integer.MAX_VALUE);
            VESSEL_SHOOTS_AT_ATTACK_TIME = builder
                    .comment("At the specified attack time the vessel will take aim preparing to fire \n" +
                            "This value cannot go any lower than 50 to prevent animation issues")
                    .defineInRange("vesselShootsAtAttackTime", 100, 50, Integer.MAX_VALUE);
            VESSEL_UNLEASHING_ODDS = builder
                    .comment("The chance for vessels to unleash apparitions upon death")
                    .defineInRange("vesselUnleashingOdds", 0.25, 0.0, 1.0);
        }
    }

    public static class WorldSettings {

        public static void init(ModConfigSpec.Builder builder) {
            NETHER_WORLDGEN_OVERHAUL = builder
                    .comment("""
                            Improves the vanilla nether terrain generation\s
                            Heights changes:\s
                            The base nether is now 192 blocks tall\s
                            Underlava sections are now -32 blocks deep\s
                            The area above the roof will be 64 blocks tall\s
                            \s
                            §cNOTE: If Amplified Nether is installed then that mod's generation will take priority regardless of config value
                            """)
                    .gameRestart()
                    .define("netherWorldGenOverhaul", true);
        }
    }


    public static class VisualAndSoundSettings {

        public static void init(ModConfigSpec.Builder builder) {
            UNCAPPED_NETHER_FOG_DISTANCE = builder
                    .comment("Usually in vanilla the nether fog cannot exceed past 12 chunks, this config removes that limiter")
                    .define("uncappedNetherFogDistance", true);
            BLACK_ICE_PARTICLES = builder
                    .comment("Black ice blocks will produce black flake particles underneath")
                    .define("blackIceParticles", true);
            ECTOPLASM_PARTICLES = builder
                    .comment("Ectoplasm will produce rays of light and rising particles from its surface")
                    .define("ectoplasmParticles", true);
            ECTOPLASM_SOUNDS = builder
                    .comment("Ectoplasm will whisper incomprehensible gibberish to players")
                    .define("ectoplasmSounds", true);
            NETHER_MIST_PARTICLES = builder
                    .comment("Periodically misty particles will form around the distance fog")
                    .define("netherMistParticles", true);
            NETHER_MIST_SPAWN_RATE = builder
                    .comment("Controls the tick intervals of nether mist spawning")
                    .defineInRange("netherMistSpawnRate", 10, 0, Integer.MAX_VALUE);
            NETHER_MIST_MIN_DISTANCE = builder
                    .comment("Minimum distance at which the nether mist particles spawn")
                    .defineInRange("netherMistMinDistance", 32.0, 0, Double.MAX_VALUE);
            NETHER_MIST_MAX_DISTANCE = builder
                    .comment("Maximum distance at which the nether mist particles spawn")
                    .defineInRange("netherMistMaxDistance", 64.0, 0, Double.MAX_VALUE);
            NETHER_MIST_SCALE = builder
                    .comment("The size of nether mist particles")
                    .defineInRange("netherMistScale", 39.0f, 0, Double.MAX_VALUE);
            NETHER_MIST_OPACITY = builder
                    .comment("The opacity of nether mist particles")
                    .defineInRange("netherMistOpacity", 0.6f, 0.0, 1.0);
            NETHER_MIST_MOTION_MULTIPLIER = builder
                    .comment("Influences the nether mist particle to go off in random directions at the defined speed")
                    .defineInRange("netherMistMotionMultiplier", 0.03, 0, Double.MAX_VALUE);
        }
    }


    public static class GameMechanicSettings {

        public static void init(ModConfigSpec.Builder builder) {
            ECTOPLASM_FREEZES = builder
                    .comment("Ectoplasm will slowly start to deal freezing damage when submerged")
                    .define("ectoplasmFreezes", true);
            ECTOPLASM_RUSTS_NETHERITE = builder
                    .comment("Ectoplasm will rust all netherite upon contact")
                    .define("ectoplasmRustsNetherite", true);
            SILVER_PARANORMAL_DAMAGE_MULTIPLIER = builder
                    .comment("Silver weapons will deal multiplied damage to possessed and ghost mobs")
                    .defineInRange("silverParanormalDamageMultiplier", 1.5, Double.MIN_VALUE, Double.MAX_VALUE);
            SILVER_PARANORMAL_PROTECTION_DAMAGE = builder
                    .comment("Silver armor will damage possessed and ghost mobs which damaged you in melee")
                    .defineInRange("silverParanormalProtectionDamage", 5, 0, Double.MAX_VALUE);
            SILVER_PARANORMAL_INFLICTS_SLOWNESS = builder
                    .comment("Silver armor and weapons will inflict possessed and ghost mobs with slowness")
                    .define("silverInflictsParanormalSlowness", true);
            DEVELOPER_MODE = builder
                    .comment("Turns on various developer loggers, technical information and such for debugging purposes \n" +
                            "I suggest you keep this disabled if you're just a casual player")
                    .define("developerMode", false);
        }
    }
}
