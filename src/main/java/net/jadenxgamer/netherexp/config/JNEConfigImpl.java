package net.jadenxgamer.netherexp.config;

import net.jadenxgamer.netherexp.config.enums.BansheeRedirectConfig;
import net.jadenxgamer.netherexp.config.enums.ProfanityConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class JNEConfigImpl {

    public static ModConfigSpec STARTUP;
    public static ModConfigSpec COMMON;

    static {
        ModConfigSpec.Builder COMMON = new ModConfigSpec.Builder();
        ModConfigSpec.Builder STARTUP = new ModConfigSpec.Builder();

        COMMON.comment("Block Settings").push("blockSettings");
        JNEConfigImpl.BlockSettings.init(COMMON);
        COMMON.pop();

        COMMON.comment("Item Settings").push("itemSettings");
        JNEConfigImpl.ItemSettings.init(COMMON);
        COMMON.pop();

        COMMON.comment("Entity Settings").push("entitySettings");
        JNEConfigImpl.EntitySettings.init(COMMON);
        COMMON.pop();

        COMMON.comment("World Settings").push("worldSettings");
        JNEConfigImpl.WorldSettings.init(COMMON);
        COMMON.pop();

        COMMON.comment("Visual & Sound Settings").push("visualAndSoundSettings");
        JNEConfigImpl.VisualAndSoundSettings.init(COMMON);
        COMMON.pop();

        COMMON.comment("Game Mechanic Settings").push("gameMechanicSettings");
        JNEConfigImpl.GameMechanicSettings.init(COMMON);
        COMMON.pop();

        STARTUP.comment("Startup Settings").push("startupSettings");
        JNEConfigImpl.StartupSettings.init(STARTUP);
        STARTUP.pop();

        STARTUP.comment("Pack Settings").push("packSettings");
        JNEConfigImpl.PackSettings.init(STARTUP);
        STARTUP.pop();

        JNEConfigImpl.COMMON = COMMON.build();
        JNEConfigImpl.STARTUP = STARTUP.build();
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
            SOUL_SPEED_DURATION = builder
                    .comment("Duration of unbounded speed when it is inflicted from soul swirls\nSetting to 0 functionally disables it")
                    .defineInRange("soulSpeedDuration", 7, 0, Integer.MAX_VALUE);
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
                    .comment("Defines the loot table which the game will use when no registry is found for that particular biome or structure context")
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
                    .comment("The amount of wraithing flesh dropped by a lesion upon harvesting")
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
                    .comment("Damage multiplier per block the sorrowsquash falls to be inflicted as sorrowsquished")
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
            CIERGE_OF_TREACHERY_COMPLETION_COOLDOWN = builder
                    .comment("Completed Cierges of Treachery will become active again after the specified time in seconds has elapsed")
                    .defineInRange("ciergeOfTreacheryCompletionCooldown", 1800, 0, 86400);
            BRAZIER_CHEST_REFILL_COOLDOWN = builder
                    .comment("Unlocked brazier chests will extinguish after the specified time in seconds has elapsed and refill their loot")
                    .defineInRange("brazierChestRefillCooldown", 3600, 0, 86400);
            HAZE_BLOCK_COOLDOWN = builder
                    .comment("Defines how long a haze block will last after being placed in seconds")
                    .defineInRange("hazeBlockCooldown", 8, 0, Integer.MAX_VALUE);
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
            TWEAK_OBTAINING_TEARS_MUSIC_DISC = builder
                    .comment("Makes obtaining the tears music disc harder, when enabled requires you to slay a ghast in the overworld \n" +
                             "If disabled then the disc is obtained by redirecting a fireball into a ghast like in vanilla")
                    .worldRestart()
                    .define("tweakObtainingTearsMusicDisc", true);
            SHOTGUN_SELF_RECOIL = builder
                    .comment("The amount of self-pushback experienced upon firing a shotgun\n" +
                            "Enchantments can influence the final total of bullets fired")
                    .defineInRange("shotgunSelfRecoil", 0.35, 0.0, Double.MAX_VALUE);
            POINT_BLANK_SELF_RECOIL_BONUS = builder
                    .comment("The amount of additional self-recoil gained from hitting a target point-blank")
                    .defineInRange("pointBlankSelfRecoilBonus", 0.45, 0.0, Double.MAX_VALUE);
            POINT_BLANK_SELF_RECOIL_DISTANCE = builder
                    .comment("How close you need to be to a target in blocks to get a point-blank self recoil bonus")
                    .defineInRange("pointBlankSelfRecoilDistance", 2.5, 0.0, Double.MAX_VALUE);
            SHOTGUN_FIST_BULLETS = builder
                    .comment("The base amount of shotgun pellets fired from a shotgun-fist\n" +
                            "Enchantments can influence the final total of bullets fired")
                    .defineInRange("shotgunFistBullets", 25, 0, Integer.MAX_VALUE);
            SHOTGUN_FIST_COOLDOWN = builder
                    .comment("The base amount of ticks the shotgun-fist will go into cooldown upon firing\n" +
                            "Enchantments can influence the final total of cooldown")
                    .defineInRange("shotgunFistCooldown", 40, 0, Integer.MAX_VALUE);
            PUMP_CHARGE_SHOTGUN_BULLETS = builder
                    .comment("The base amount of shotgun pellets fired from a pump-charge shotgun\n" +
                            "Enchantments and pumps can influence the final total of bullets fired")
                    .defineInRange("pumpChargeShotgunBullets", 10, 0, Integer.MAX_VALUE);
            PUMP_CHARGE_SHOTGUN_COOLDOWN = builder
                    .comment("The base amount of ticks the pump-charge shotgun will go into cooldown upon firing\n" +
                            "Enchantments can influence the final total of cooldown")
                    .defineInRange("pumpChargeShotgunCooldown", 15, 0, Integer.MAX_VALUE);
            SLUG_BLOCK_DAMAGE_STRENGTH = builder
                    .comment("The amount of damage a slug pellet will deal to a block upon impact\n" +
                            "§eNOTE: Negative values will disable block destruction with slugs")
                    .defineInRange("slugBlockDamageStrength", 1.0, -1.0, Double.MAX_VALUE);
            COUNTERFORCE_IFRAMES = builder
                    .comment("When a counterforce shotgun is used it'll grant you immunity frames for the specified number of ticks")
                    .defineInRange("counterforce_iframes", 20, 0, Integer.MAX_VALUE);
            WILL_O_WISP_STACK_SIZE = builder
                    .comment("The number of will o' wisps that can be stored in a single stack")
                    .defineInRange("willOWispStackSize", 64, 1, 99);
            STACKABLE_POTIONS = builder
                    .comment("When enabled almost all types of potions can be stacked to 16 like in the combat snapshots")
                    .define("stackablePotions", true);
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
                            "Salted apparitions cannot possess any mobs, although they will still attack them")
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
            PIXEL_CONSISTENT_MAGMA_CUBES = builder
                    .comment("Magma Cube textures will scale with the model and conform to pixel consistency")
                    .define("pixelConsistentMagmaCubes", true);
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
            VESSEL_ACCURATE_DISTANCE = builder
                    .comment("When a target is at or closer to the specified distance in blocks the vessel's aim will be more accurate and will almost always hit a perfect point-blank")
                    .defineInRange("vesselAccurateDistance", 5, 0, Integer.MAX_VALUE);
            BANSHEE_TELEPORTS_AFTER_HIT = builder
                    .comment("Banshees hurt by an entity will try to teleport away to a random spot or their anchor")
                    .define("bansheeTeleportsAfterHit", true);
            BANSHEE_ANCHOR_INTERVAL = builder
                    .comment("""
                             The number of teleports a banshee can do before returning to its anchor point\s
                             This config value is influenced by the world difficulty adding:\s
                             -1 on Easy\s
                             +1 on Hard
                             """)
                    .defineInRange("bansheeAnchorInterval", 3, 1, Integer.MAX_VALUE);
            BANSHEE_ATTACK_INTERVAL = builder
                    .comment("The base attack interval of a banshee, which constantly ticks down. when the value reaches \"0\" it'll fire a will o' wisp \n" +
                            "After which the specified value will be added to the attack interval for it to continue ticking down")
                    .defineInRange("bansheeAttackInterval", 40, 0, Integer.MAX_VALUE);
            BANSHEE_ATTACK_INTERVAL_BONUS = builder
                    .comment("This value gets added on top of the base attack interval adding to further randomization in its attack patterns")
                    .defineInRange("bansheeAttackIntervalBonus", 30, 0, Integer.MAX_VALUE);
            BANSHEE_ATTACK_INTERVAL_STAGGER = builder
                    .comment("When hurt the banshee's attack interval can be staggered by the specified amount in ticks")
                    .defineInRange("bansheeAttackIntervalStagger", 10, 0, Integer.MAX_VALUE);
            BANSHEE_REDIRECT_STUNS = builder
                    .comment("Specify what behavior should take place if a banshee gets hit with its own will o' wisp projectile")
                    .defineEnum("bansheeRedirectBehavior", BansheeRedirectConfig.STUN);
            BANSHEE_STUN_TIMER = builder
                    .comment("Defines how long a banshee hit with its own projectile will be stunned for \n" +
                            "During this it is unable to fire will o' wisps")
                    .defineInRange("bansheeStunTimer", 100, 0, Integer.MAX_VALUE);
            GENERIC_WILL_O_WISP_MANEUVERABILITY = builder
                    .comment("How well a will o' wisp shot by a player or dispenser can turn sharp corners")
                    .defineInRange("genericWillOWispManoeuvrability", 0.22, 0, Double.MAX_VALUE);
            BANSHEE_WILL_O_WISP_MANEUVERABILITY = builder
                    .comment("How well a will o' wisp shot by a banshee can turn sharp corners")
                    .defineInRange("bansheeWillOWispManoeuvrability", 0.2, 0, Double.MAX_VALUE);
            MANEUVERABILITY_AFFECTED_BY_DIFFICULTY = builder
                    .comment("""
                            Weather world difficulty will have any effect on a banshee will o' wisps' manoeuvrability\s
                            The values below are added onto whatever is specifed in "bansheeWillOWispManoeuvrability"\s
                            -0.05 on Easy\s
                            +0.04 on Hard
                            """)
                    .define("manoeuvrabilityAffectedByDifficulty", true);
            WILL_O_WISP_WIND_PROPULSION = builder
                    .comment("When a will o' wisp is hit with a wind charge it'll accelerate its speed and manoeuvrability greatly")
                    .define("willOWispWindPropulsion", true);
            ECTO_SLAB_PETRIFY_WITH_GHAST_TEAR = builder
                    .comment("Whether you can use ghast tears to petrify ecto slabs")
                    .define("ectoSlabPetrifyWithGhastTear", true);
            PETRIFIED_ECTO_SLAB_SHATTER_DISTURBANCE = builder
                    .comment("The amount of disturbance needed to shatter a petrified stack")
                    .defineInRange("petrifiedEctoSlabShatterDisturbance", 20, 0, Integer.MAX_VALUE);
            ECTO_SLAB_FORCE_OUT_WITH_SHOVEL = builder
                    .comment("Whether you can force an ecto slab out with a shovel")
                    .define("ectoSlabForceOutWithShovel", true);
            FORCED_OUT_ECTO_SLAB_FRIENDLY_FIRE = builder
                    .comment("Whether forced out ecto slabs can deal friendly fire to entities not susceptible to emerge burst")
                    .define("forcedOutEctoSlabFriendlyFire", true);
            EXPLOSION_SHATTERS_ECTO_SLAB_STACK = builder
                    .comment("Whether explosion damage can shatter the ecto slab stack")
                    .define("explosionShattersEctoSlabStack", true);
            EXPLOSION_SHATTER_COOLDOWN = builder
                    .comment("How long of a cooldown ecto slabs gain upon being shattered via an explosion in ticks")
                    .defineInRange("explosionShatterCooldown", 300, 0, Integer.MAX_VALUE);
            PETRIFICATION_PETRIFIES_SWIRLS = builder
                    .comment("Whether petrification should petrify nearby swirls")
                    .define("petrificationPetrifiesSwirls", true);
            PETRIFICATION_SWIRLS_RANGE = builder
                    .comment("The range of swirls petrification")
                    .defineInRange("petrificationSwirlsRange", 32, 0, Integer.MAX_VALUE);
            PETRIFICATION_SWIRLS_RANGE_PER_STACK = builder
                    .comment("Additional range per stack for swirls petrification")
                    .defineInRange("petrificationSwirlsRangePerStack", 4, 0, Integer.MAX_VALUE);
            NATURAL_PETRIFIED_ECTO_SLAB_CHANCE = builder
                    .comment("Odds for naturally spawned ecto slabs to be petrified")
                    .defineInRange("naturalPetrifiedEctoSlabChance", 0.12, 0.0, 1.0);
            NATURAL_PETRIFIED_ECTO_SLAB_MIN_STACK = builder
                    .comment("Minimum stack size for naturally spawned petrified ecto slabs")
                    .defineInRange("naturalPetrifiedEctoSlabMinStack", 8, 0, Integer.MAX_VALUE);
            NATURAL_PETRIFIED_ECTO_SLAB_MAX_STACK = builder
                    .comment("Maximum stack size for naturally spawned petrified ecto slabs")
                    .defineInRange("naturalPetrifiedEctoSlabMaxStack", 12, 0, Integer.MAX_VALUE);
            NATURAL_ECTO_SLAB_PACK_MIN = builder
                    .comment("Minimum pack size of individual naturally spawned ecto slabs")
                    .defineInRange("naturalEctoSlabPackMin", 2, 0, Integer.MAX_VALUE);
            NATURAL_ECTO_SLAB_PACK_MAX = builder
                    .comment("Maximum pack size of individual naturally spawned ecto slabs")
                    .defineInRange("naturalEctoSlabPackMax", 5, 0, Integer.MAX_VALUE);
            EMERGE_BURST_DAMAGE_MULTIPLIER = builder
                    .comment("Emerge burst damage multiplier")
                    .defineInRange("emergeBurstDamageMultiplier", 2.0, 0.0, Double.MAX_VALUE);
            ABSOLUTE_MAXIMUM_STACK_SIZE = builder
                    .comment("""
                            The absolute maximum stack size for all ecto slabs
                            §cWARNING: Higher values can easily cause FPS lag when too many ecto slabs are present
                             \
                            Due to the additional segments needing to be added for rendering""")
                    .gameRestart()
                    .defineInRange("absoluteMaximumStackSize", 16, 1, 99);
            ECTO_SLAB_EXTRA_JUMP_DELAY = builder
                    .comment("Additional jump delay on top of the base hardcoded values")
                    .defineInRange("ectoSlabExtraJumpDelay", 0, 0, Integer.MAX_VALUE);
            ECTO_SLAB_EXTRA_BURROW_COOLDOWN = builder
                    .comment("Additional burrow cooldown on top of the base hardcoded values")
                    .defineInRange("ectoSlabExtraBurrowCooldown", 0, 0, Integer.MAX_VALUE);
            ECTO_SLAB_SPEED_PER_STACK = builder
                    .comment("Speed bonus per ecto slab stack")
                    .defineInRange("ectoSlabSpeedPerStack", 0.05, 0.0, Double.MAX_VALUE);
            ECTO_SLAB_SPEED_CAP = builder
                    .comment("Speed cap per ecto slab stack\n" +
                            "§eNOTE: Negative values disable cap")
                    .defineInRange("ectoSlabSpeedCap", 0.6, -1.0, Double.MAX_VALUE);
            ECTO_SLAB_DAMAGE_PER_STACK = builder
                    .comment("Damage bonus per ecto slab stack")
                    .defineInRange("ectoSlabDamagePerStack", 2.0, 0.0, Double.MAX_VALUE);
            ECTO_SLAB_DAMAGE_CAP = builder
                    .comment("Damage cap per ecto slab stack\n" +
                            "§eNOTE: Negative values disable cap")
                    .defineInRange("ectoSlabDamageCap", -1.0, -1.0, Double.MAX_VALUE);
            ECTO_SLAB_KNOCKBACK_RESISTANCE_PER_STACK = builder
                    .comment("Knockback resistance per ecto slab stack")
                    .defineInRange("ectoSlabKnockbackResistancePerStack", 0.15, 0.0, Double.MAX_VALUE);
            ECTO_SLAB_KNOCKBACK_RESISTANCE_CAP = builder
                    .comment("Knockback resistance cap per ecto slab stack\n" +
                            "§eNOTE: Negative values disable cap")
                    .defineInRange("ectoSlabKnockbackResistanceCap", -1.0, -1.0, Double.MAX_VALUE);
            ECTO_SLAB_JUMP_STRENGTH_PER_STACK = builder
                    .comment("Jump strength per ecto slab stack")
                    .defineInRange("ectoSlabJumpStrengthPerStack", 0.05, 0.0, Double.MAX_VALUE);
            ECTO_SLAB_JUMP_STRENGTH_CAP = builder
                    .comment("Jump strength cap per ecto slab stack\n" +
                            "§eNOTE: Negative values disable cap")
                    .defineInRange("ectoSlabJumpStrengthCap", -1.0, -1.0, Double.MAX_VALUE);
            ECTO_SLAB_MAX_STACK_SIZE = builder
                    .comment("Maximum stack size for non-petrified ecto slabs")
                    .defineInRange("ectoSlabMaxStackSize", 4, 1, 16);
            ECTO_SLAB_MAX_DIG_TIME = builder
                    .comment("How long the ecto slab can stay underground in ticks")
                    .defineInRange("ectoSlabMaxDigTime", 80, 0, Integer.MAX_VALUE);
            VESSEL_UNLEASHING_ODDS = builder
                    .comment("The chance for vessels to unleash apparitions upon death")
                    .defineInRange("vesselUnleashingOdds", 0.25, 0.0, 1.0);
            ECTO_SLAB_UNLEASHING_ODDS = builder
                    .comment("The chance for ecto slabs to unleash apparitions upon death")
                    .defineInRange("ectoSlabUnleashingOdds", 0.05, 0.0, 1.0);
            BANSHEE_UNLEASHING_ODDS = builder
                    .comment("The chance for banshees to unleash apparitions upon death")
                    .defineInRange("bansheeUnleashingOdds", 0.15, 0.0, 1.0);
            STAMPEDE_UNLEASHING_ODDS = builder
                    .comment("The chance for stampedes to unleash apparitions upon death")
                    .defineInRange("stampedeUnleashingOdds", 0.5, 0.0, 1.0);
            STAMPEDE_STRIDITE_SHEDDING_CHANCE = builder
                    .comment("Stampedes will shed stridite upon running over any entity")
                    .defineInRange("stampedeStriditeSheddingChance", 0.1, 0.0, 1.0);
            MIN_STAMPEDE_STRIDITE_DROPS = builder
                    .comment("The minimum number of stridite that can be shed by a stampede")
                    .defineInRange("minStampedeStriditeDrops", 1, 0, Integer.MAX_VALUE);
            MAX_STAMPEDE_STRIDITE_DROPS = builder
                    .comment("The maximum number of stridite that can be shed by a stampede")
                    .defineInRange("maxStampedeStriditeDrops", 5, 0, Integer.MAX_VALUE);
        }
    }

    public static class WorldSettings {

        public static void init(ModConfigSpec.Builder builder) {
            builder.comment("Biome Feature Settings").push("biomeFeaturesSettings");
            JNEConfigImpl.BiomeFeatures.init(builder);
            builder.pop();

            NETHER_WORLDGEN_OVERHAUL = builder
                    .comment("""
                            Improves the vanilla nether terrain generation with better multilayered-ness and height changes\s
                            The Height changes include:\s
                            -Base Nether being extended to 192 blocks tall\s
                            -Underlava sections extended by -32 blocks of depth\s
                            -And the area above the roof will be 64 blocks tall instead\s
                            \s
                            §cNOTE: If Amplified Nether is installed then that mod's generation will take priority regardless of config value
                            """)
                    .gameRestart()
                    .define("netherWorldGenOverhaul", true);
            IMPROVED_NETHER_BIOME_SOURCE = builder
                    .comment("""
                            Completely replaces the vanilla multi-noise biome source for Elysium API's mosaic ones\s
                            Rather than determining a biomes' position based on the terrain's noise climate. it instead uses voronoi cells with a weighted list\s
                            Mosaic biome source is remarkably stellar at keeping consistent sizes between all the biomes and equally distributes them too, no matter how many mods you have\s
                            \s
                            §cNOTE: MosaicBiomeSource is currently in BETA and in active testing. as of now sub-biome support is limited and there is no 3D biome support either
                            """)
                    .gameRestart()
                    .define("improvedNetherBiomeSource", true);
            BRIGHTER_NETHER_FOG = builder
                    .comment("Brightens up the nether fog of most vanilla and modded biomes to compliment their environments better \n" +
                            "It overall makes the nether feel warmer and helps make out shapes in the distance like actual fog")
                    .worldRestart()
                    .define("brighterNetherFog", true);
            RED_NETHER_WASTES_FOG = builder
                    .comment("Changes the nether wastes fog back to being red like in vanilla")
                    .worldRestart()
                    .define("redNetherWastesFog", false);
            BETTER_SOUL_SAND_VALLEY_PARTICLES = builder
                    .comment("Dust like particles will blow in the soul sand valley instead of the occasional falling ash")
                    .worldRestart()
                    .define("betterSoulSandValleyParticles", true);
        }
    }

    public static class BiomeFeatures {

        public static void init(ModConfigSpec.Builder builder) {
            BONE_PIKE = builder
                    .gameRestart()
                    .comment("Generates bone pikes on the soul sand valley surface")
                    .define("bonePike", true);
            ECTO_SOUL_SAND = builder
                    .gameRestart()
                    .comment("Generates ecto soul sand patches in the soul sand valley")
                    .define("ectoSoulSand", true);
            ECTOPLASM_LAKE = builder
                    .gameRestart()
                    .comment("Generates ectoplasm lakes in the soul sand valley")
                    .define("ectoplasmLake", true);
            FOSSIL_FUEL_ORE = builder
                    .gameRestart()
                    .comment("Generates fossil fuel ore deposits in the soul sand valley")
                    .define("fossilFuelOre", true);
            FOSSIL_ORE = builder
                    .gameRestart()
                    .comment("Generates fossil ore deposits in the soul sand valley")
                    .define("fossilOre", true);
            MOUND = builder
                    .gameRestart()
                    .comment("Generates mounds on the ceiling and floor of the soul sand valley")
                    .define("mound", true);
            SOUL_MAGMA = builder
                    .gameRestart()
                    .comment("Generates soul magma patches towards the upper regions of the soul sand valley")
                    .define("soulMagma", true);
            PALE_SOUL_SLATE = builder
                    .gameRestart()
                    .comment("Generates pale soul slate embedded in the walls or surface of the soul sand valley")
                    .define("paleSoulSlate", true);
            SOUL_SWIRLS = builder
                    .gameRestart()
                    .comment("Generates soul swirls in the soul sand valley floor and ceiling")
                    .define("soulSwirls", true);
            BLOTTED_NETHERRACK = builder
                    .gameRestart()
                    .comment("Generates blotted netherrack patches all throughout the nether")
                    .define("blottedNetherrack", true);
            SILTMARRAM = builder
                    .gameRestart()
                    .comment("Generates siltmarram on silt beaches and coves with a different variant based on the biome's moisture")
                    .define("siltmarram", true);
            NETHERRACK_SPELEOTHEM = builder
                    .gameRestart()
                    .comment("Generates large netherrack speleothem all throughout the nether")
                    .define("netherrackSpeleothem", true);
            PYROCLAST_CRUSTS = builder
                    .gameRestart()
                    .comment("Generates pyroclast crusts on the lava sea surfaces")
                    .define("pyroclastCrusts", true);
            SILT_FLINT_ORE = builder
                    .gameRestart()
                    .comment("Generates silt flint ore deposits in silt beaches and coves")
                    .define("siltFlintOre", true);
            REMOVE_NETHER_NOODLE_CAVES = builder
                    .gameRestart()
                    .comment("Prevents those ugly ravines and noodle caves from cutting through the nether's terrain by removing them entirely")
                    .define("removeNetherNoodleCaves", true);
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
                    .defineInRange("netherMistSpawnRate", 6, 0, Integer.MAX_VALUE);
            NETHER_MIST_MIN_DISTANCE = builder
                    .comment("Minimum distance at which the nether mist particles spawn")
                    .defineInRange("netherMistMinDistance", 32.0, 0, Double.MAX_VALUE);
            NETHER_MIST_MAX_DISTANCE = builder
                    .comment("Maximum distance at which the nether mist particles spawn")
                    .defineInRange("netherMistMaxDistance", 80.0, 0, Double.MAX_VALUE);
            NETHER_MIST_SCALE = builder
                    .comment("The size of nether mist particles")
                    .defineInRange("netherMistScale", 60.0, 0, Double.MAX_VALUE);
            NETHER_MIST_OPACITY = builder
                    .comment("The opacity of nether mist particles")
                    .defineInRange("netherMistOpacity", 0.5, 0.0, 1.0);
            NETHER_MIST_MOTION_MULTIPLIER = builder
                    .comment("Influences the nether mist particle to go off in random directions at the defined speed")
                    .defineInRange("netherMistMotionMultiplier", 0.05, 0, Double.MAX_VALUE);
            NETHER_MIST_DISSIPATE_DISTANCE = builder
                    .comment("Makes the nether mist particles disappear faster if you get close enough to them, \n" +
                            "You may disable this functionality entirely by just setting the value to 0.0")
                    .defineInRange("netherMistDissipateDistance", 28.0, 0.0, Double.MAX_VALUE);
            SOUL_SAND_VALLEY_WIND_SPEED = builder
                    .comment("Influences the wind speed of ash particles in the soul sand valley")
                    .defineInRange("soulSandValleyWindSpeed", 0.2, 0.0, Double.MAX_VALUE);
            WINDY_ASH_SCALE_MULTIPLIER = builder
                    .comment("Multiplies the size of windy ash particles")
                    .defineInRange("windyAshScaleMultiplier", 1.6, 0.0, Double.MAX_VALUE);
            DRIFTING_SOULS_SPAWN_QUANTITY = builder
                    .comment("Number of drifting soul particles which can spawn from a single block")
                    .defineInRange("driftingSoulsSpawnQuantity", 4, 0, Integer.MAX_VALUE);
            DRIFTING_SOULS_SPAWN_RADIUS = builder
                    .comment("The radius of particles a single drifting souls block can spawn around in each direction")
                    .defineInRange("driftingSoulsSpawnRadius", 32, 0, Integer.MAX_VALUE);
            WILL_O_WISP_PARTICLES = builder
                    .comment("Fired will o' wisps will produce wisp-like trail particles behind them")
                    .define("willOWispParticles", true);
            WILL_O_WISP_SOUNDS = builder
                    .comment("Fired will o' wisps will make a weeping sound that helps with knowing if one is homing in on you")
                    .define("willOWispSounds", true);
            IMPROVED_FIRE_PARTICLES = builder
                    .comment("Adds more dynamic and animated particles to all fire blocks")
                    .define("improvedFireParticles", true);
            FIRE_SMOKE_PARTICLES = builder
                    .comment("Enables new smoke particles for fire")
                    .define("fireSmokeParticles", true);
            FIRE_EMBER_PARTICLES = builder
                    .comment("Enables new ember particles for fire")
                    .define("fireEmberParticles", true);
            LAVA_PERLIN_NOISE_GRADIENT = builder
                    .comment("Adds slight color variation gradients to lava to break up the repetitive texture in large areas")
                    .define("lavaPerlinNoiseGradient", true);
            LAVA_GRADIENT_COLOR_A = builder
                    .comment("The first color that is applied to the lava perlin noise gradient\n" +
                            "Color §cMUST§r be in #RRGGBB or #AARRGGBB format")
                    .worldRestart()
                    .define("lavaGradientColorA", "#FFFFFFFF");
            LAVA_GRADIENT_COLOR_B = builder
                    .comment("The second color that is applied to the lava perlin noise gradient\n" +
                            "Color §cMUST§r be in #RRGGBB or #AARRGGBB format")
                    .worldRestart()
                    .define("LavaGradientColorB", "#FFE28001");
            LAVA_GRADIENT_NOISE_SCALE = builder
                    .comment("Scale factor that determines how big the perlin noise streaks are")
                    .worldRestart()
                    .defineInRange("LavaGradientNoiseScale", 14.0, Double.MIN_VALUE, Double.MAX_VALUE);
            SHOTGUN_SCREENSHAKE = builder
                    .comment("Toggle the screenshake when a shotgun is fired")
                    .define("shotgunScreenshake", true);
            ECTO_SLAB_EMERGE_BURST_SCREENSHAKE = builder
                    .comment("Toggle the emerge burst screen shake")
                    .define("ectoSlabEmergeBurstScreenshake", true);
            ECTO_SLAB_PETRIFICATION_SCREENSHAKE = builder
                    .comment("Toggle petrification screen shake")
                    .define("ectoSlabPetrificationScreenshake", true);
            ENABLE_HEAT_DISTORTION = builder
                    .comment("Toggles all heat distortion effects in JNE; To disable individual heat distortion effects check the below configs")
                    .define("enableHeatDistortion", true);
            BIOME_HEAT_DISTORTION = builder
                    .comment("Heat distortion is applied to far away objects when in certain biomes such as the nether")
                    .define("biomeHeatDistortion", true);
            LAVA_HEAT_DISTORTION = builder
                    .comment("A much stronger heat distortion is applied when close to lava source blocks")
                    .define("lavaHeatDistortion", true);
            HEAT_DISTORTION_INTENSITY = builder
                    .comment("Intensity of heat distortion wobbling")
                    .defineInRange("heatDistortionIntensity", 0.003, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_SPEED = builder
                    .comment("Speed of heat distortion wobbling")
                    .defineInRange("heatDistortionSpeed", 2.0, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_MIN_DISTANCE = builder
                    .comment("Minimum distance from the camera that heat distortion starts at")
                    .defineInRange("heatDistortionMinDistance", 12.0, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_MAX_DISTANCE = builder
                    .comment("Maximum distance from the camera that heat distortion reaches its max intensity at")
                    .defineInRange("heatDistortionMaxDistance", 128.0, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_LAVA_INTENSITY = builder
                    .comment("Intensity of heat distortion wobbling when close to lava sources")
                    .defineInRange("heatDistortionLavaIntensity", 0.006f, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_LAVA_SPEED = builder
                    .comment("Speed of heat distortion wobbling when close to lava sources")
                    .defineInRange("heatDistortionLavaSpeed", 2.0, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_LAVA_MIN_DISTANCE = builder
                    .comment("Minimum distance from the camera that heat distortion starts at when close to lava sources")
                    .defineInRange("heatDistortionLavaMinDistance", 0.0, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_LAVA_MAX_DISTANCE = builder
                    .comment("Maximum distance from the camera that heat distortion reaches its max intensity at when close to lava sources")
                    .defineInRange("heatDistortionLavaMaxDistance", 64.0, 0.0, Double.MAX_VALUE);
            HEAT_DISTORTION_LAVA_CHECK_PERIOD = builder
                    .comment("How often the game will check around the player for lava heat distortion in ticks \n" +
                            "§cWARNING: Lower numbers can increase lag especially when paired with high proximity distances to iterate through")
                    .defineInRange("heatDistortionLavaCheckPeriod", 100, 0, Integer.MAX_VALUE);
            HEAT_DISTORTION_LAVA_PROXIMITY = builder
                    .comment("How far the game will check around the player for heat distortion lava sources \n" +
                            "§cWARNING: Increasing the distance can add some latency to performance! as it needs to iterate through all the specified blocks in-range")
                    .defineInRange("heatDistortionLavaProximity", 2.0, 0.0, Double.MAX_VALUE);
            REQUIRED_SOURCE_BLOCKS_FOR_LAVA_HEAT_DISTORTION = builder
                    .comment("The number of source blocks that need to be around the player to cause heat distortion")
                    .defineInRange("requiredSourceBlocksForLavaHeatDistortion", 12, 0, Integer.MAX_VALUE);
            ENABLE_SOUL_GLASS_SCREEN_FILTER = builder
                    .comment("Toggles the on-screen filter when submerged inside soul glass")
                    .define("enableSoulGlassScreenFilter", true);
            NO_SOUL_GLASS_RIPPLE = builder
                    .comment("Removes the ripple effect from soul glass entirely and leaves just the frosted glass effect for people who find the distortion uneasy")
                    .define("noSoulGlassRipple", false);
            IMPROVED_BREWING_STAND_PARTICLES = builder
                    .comment("Improves the brewing stand's particles")
                    .define("improvedBrewingStandParticles", true);
            POTION_CONSUMPTION_PARTICLES = builder
                    .comment("Adds a particle effect around the player after drinking from a potion or antidote similar to Minecraft Dungeons")
                    .define("potionConsumptionParticles", true);
            IMPROVED_NETHER_PORTAL_PARTICLES = builder
                    .comment("Improves the nether portal's particles")
                    .define("improvedNetherPortalParticles", true);
            ENABLE_NETHER_BIOME_LIGHTMAPS = builder
                    .comment("Gives every nether biome a custom lightmap that compliments its environment")
                    .define("enableNetherBiomeLightmaps", true);
            CIERGE_OF_TREACHERY_RED_LIGHTS = builder
                    .comment("When the betrayed effect is active, it'll turn all lighting red")
                    .define("ciergeOfTreacheryRedLights", true);
            CIERGE_OF_TREACHERY_FOG = builder
                    .comment("When the betrayed effect is active, a dense red fog will appear")
                    .define("ciergeOfTreacheryFog", true);
            CIERGE_OF_TREACHERY_PARTICLES = builder
                    .comment("Cierge of Treachery will produce red haze and sparkle particles")
                    .define("ciergeOfTreacheryParticles", true);
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
                    .defineInRange("silverParanormalDamageMultiplier", 1.8, Double.MIN_VALUE, Double.MAX_VALUE);
            SILVER_PARANORMAL_PROTECTION_DAMAGE = builder
                    .comment("Silver armor will damage possessed and ghost mobs which damaged you in melee")
                    .defineInRange("silverParanormalProtectionDamage", 5, 0, Double.MAX_VALUE);
            SILVER_PARANORMAL_INFLICTS_SLOWNESS = builder
                    .comment("Silver armor and weapons will inflict possessed and ghost mobs with slowness")
                    .define("silverInflictsParanormalSlowness", true);
            SHOW_BETA_WARNING_POPUP = builder
                    .comment("Pretty self-explanatory; When running beta builds of JNE, a pop-up will appear on screen warning you upon joining worlds")
                    .define("showBetaWarningPopUp", true);
        }
    }


    public static class StartupSettings {

        public static void init(ModConfigSpec.Builder builder) {
            ENABLE_JNE_SPLASH_TEXTS = builder
                    .comment("Adds new JNE inspired splash texts alongside the vanilla ones if enabled")
                    .define("enableJNESplashTexts", true);
            RED_SPLASH_TEXT = builder
                    .comment("Splash texts added by JNE will be a wonderful red color opposed to the usual yellow")
                    .define("redSplashText", true);
            PROFANITY = builder
                    .comment("JNE has swearing, and lots of it too but is normally censored \n" +
                            "If you'd like to disable profanity entirely or uncensor it you may do so with this config")
                    .defineEnum("profanity", ProfanityConfig.CENSORED);
            DEVELOPER_MODE = builder
                    .comment("Turns on various developer loggers, technical information and such for debugging purposes \n" +
                            "I suggest you keep this disabled if you're just a casual player... or don't, I'm not your mother")
                    .define("developerMode", false);
        }
    }


    public static class PackSettings {

        public static void init(ModConfigSpec.Builder builder) {
            SOUL_CAMPFIRE_SOUL_SOIL = builder
                    .comment("Changes the soot part of the soul campfire texture to become soul soil instead")
                    .define("soulCampfireSoulSoil", true);
        }
    }
}