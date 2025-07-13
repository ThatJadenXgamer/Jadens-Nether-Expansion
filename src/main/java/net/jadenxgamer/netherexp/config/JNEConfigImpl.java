package net.jadenxgamer.netherexp.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import static net.jadenxgamer.netherexp.config.JNEConfigs.*;

public class JNEConfigImpl {

    public static ModConfigSpec CONFIG;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        BUILDER.comment("Block Settings").push("Block Settings");
        JNEConfigImpl.BlockSettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Item Settings").push("Item Settings");
        JNEConfigImpl.ItemSettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Entity Settings").push("Entity Settings");
        JNEConfigImpl.EntitySettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("World Settings").push("World Settings");
        JNEConfigImpl.WorldSettings.init(BUILDER);
        BUILDER.pop();

        BUILDER.comment("Overhaul Settings").push("Overhaul Settings");
        JNEConfigImpl.OverhaulSettings.init(BUILDER);
        BUILDER.pop();

        CONFIG = BUILDER.build();
    }

    public static class BlockSettings {

        public static void init(ModConfigSpec.Builder builder) {
            FOSSIL_ORE_CONVERSION_ODDS = builder
                    .comment("The chance for buried fossil ore to convert into fossil fuel every random tick")
                    .defineInRange("fossilOreConversionOdds", 0.02, 0.0, 1.0);
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
            WISP_EMERGING_ODDS = builder
                    .comment("The chance for a wisp to come out of ecto soul sand")
                    .defineInRange("wispEmergingOdds", 0.02, 0.0, 1.0);
            WISP_EMERGING_ODDS_BRUSH = builder
                    .comment("The chance for a wisp to be tickled out of ecto soul sand with a brush")
                    .defineInRange("wispEmergingOddsBrush", 0.02, 0.0, 1.0);
            ECTO_SOUL_SAND_BRUSH_DAMAGE = builder
                    .comment("Durability penalty for tickling ecto soul sand to force wisps out")
                    .defineInRange("ectoSoulSandBrushDamage", 4, 0, Integer.MAX_VALUE);
            CONVERTS_TO_SUSPICIOUS_SOUL_SAND = builder
                    .comment("Weather ecto soul sand converts to suspicious soul sand by exiting wisps")
                    .define("ectoSoulSandBrushDamage", true);
            WISP_ARCHAEOLOGY_DEFAULT_LOOT_TABLE = builder
                    .comment("Weather soul swirls can be bone-mealed to duplicate or not")
                    .define("wispArchaeologyDefaultLootTable", "netherexp:archaeology/wisp_arch_default");
            SUSPICIOUS_SOUL_SAND_DECAY_ODDS = builder
                    .comment("The chance for a non-persistent suspicious soul sand block to decay\nif set to 0.0 the block won't decay at all")
                    .defineInRange("suspiciousSoulSandDecayOdds", 0.05, 0.0, 1.0);
            SUSPICIOUS_SOUL_SAND_MAX_DECAY = builder
                    .comment("The amount of decay required for the suspicious soul sand to revert back to soul sand")
                    .defineInRange("suspiciousSoulSandDecayOdds", 5, 0, Integer.MAX_VALUE);
        }
    }

    public static class ItemSettings {

        public static void init(ModConfigSpec.Builder builder) {

        }
    }

    public static class EntitySettings {

        public static void init(ModConfigSpec.Builder builder) {
            WISP_BOREDOM_ODDS = builder
                    .comment("The chance for a wisp's boredom counter to increase each tick")
                    .defineInRange("wispBoredomOdds", 0.2, 0.0, 1.0);
        }
    }


    public static class WorldSettings {

        public static void init(ModConfigSpec.Builder builder) {

        }
    }


    public static class OverhaulSettings {

        public static void init(ModConfigSpec.Builder builder) {

        }
    }
}
