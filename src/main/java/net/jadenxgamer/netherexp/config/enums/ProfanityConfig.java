package net.jadenxgamer.netherexp.config.enums;

import net.minecraft.util.StringRepresentable;

public enum ProfanityConfig implements StringRepresentable {
    DISABLED("disabled"),
    CENSORED("censored"),
    UNFILTERED("unfiltered");

    private final String name;

    ProfanityConfig(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
