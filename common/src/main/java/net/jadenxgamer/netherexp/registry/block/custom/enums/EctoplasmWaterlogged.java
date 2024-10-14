package net.jadenxgamer.netherexp.registry.block.custom.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EctoplasmWaterlogged implements StringRepresentable {
    AIR("air"),
    WATER("water"),
    ECTOPLASM("ectoplasm");

    private final String name;

    EctoplasmWaterlogged(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}