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
                    .comment("Makes obtaining the tears music disc harder, as now it requires you to slay a ghast in the overworld \n" +
                             "If disabled then the disc is obtained by redirecting a fireball into a ghast like in vanilla")
                    .worldRestart()
                    .define("tweakObtainingTearsMusicDisc", true);
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
        }
    }


    public static class WorldSettings {

        public static void init(ModConfigSpec.Builder builder) {

        }
    }


    public static class VisualAndSoundSettings {

        public static void init(ModConfigSpec.Builder builder) {
            BLACK_ICE_PARTICLES = builder
                    .comment("Black ice blocks will produce black flake particles underneath")
                    .define("blackIceParticles", true);
            ECTOPLASM_PARTICLES = builder
                    .comment("Ectoplasm will produce rays of light and rising particles from its surface")
                    .define("ectoplasmParticles", true);
            ECTOPLASM_SOUNDS = builder
                    .comment("Ectoplasm will whisper incomprehensible gibberish to players")
                    .define("ectoplasmSounds", true);
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
        }
    }
}
