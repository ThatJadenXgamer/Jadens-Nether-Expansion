package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.util.StringIdentifiable;

public enum Liquids implements StringIdentifiable {
    AIR("air"),
    WATER("water"),
    LAVA("lava"),
    ECTOPLASM("ectoplasm");

    private final String name;

    Liquids(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
