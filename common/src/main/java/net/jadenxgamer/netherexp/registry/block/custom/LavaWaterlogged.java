package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LavaWaterlogged implements StringRepresentable {
    AIR("air"),
    WATER("water"),
    LAVA("lava");

    private final String name;

    LavaWaterlogged(String name) {
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