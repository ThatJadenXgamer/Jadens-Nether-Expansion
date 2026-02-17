package net.jadenxgamer.netherexp.config.enums;

import net.minecraft.util.StringRepresentable;

public enum BansheeRedirectConfig implements StringRepresentable {
    DO_NOTHING("do_nothing"),
    STUN("stun"),
    INSTAKILL("instakill");

    private final String name;

    BansheeRedirectConfig(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
