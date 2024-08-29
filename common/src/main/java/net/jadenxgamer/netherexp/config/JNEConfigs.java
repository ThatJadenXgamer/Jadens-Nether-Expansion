package net.jadenxgamer.netherexp.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class JNEConfigs {
    public static ForgeConfigSpec COMMON;

    public static final ForgeConfigSpec.ConfigValue<Boolean> IMPROVED_SOUL_FIRE_PARTICLES;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DIMINISHING_BLAZES;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        BUILDER.comment("Jaden's Nether Expansion - Settings").push("settings");

        IMPROVED_SOUL_FIRE_PARTICLES = BUILDER
                .comment("Soul Fire will emit unique particles instead of smoke")
                .define("improved_soul_fire_particles", true);
        DIMINISHING_BLAZES = BUILDER
                .comment("Blazes get dimmer as their health decreases like in MCD")
                .define("diminishing_blazes", true);

        BUILDER.pop();
        COMMON = BUILDER.build();
    }
}
