package net.jadenxgamer.netherexp.registry.block.custom;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum GargoyleStatueTypes implements StringRepresentable {

    OSSIFIED("ossified"),
    TRAMPLE("trample"),
    PHASE("phase"),
    GHOUL("ghoul"),
    WRETCHED("wretched"),
    TREACHEROUS("treacherous"),
    CIRRIPEDIA("ciripedia"),
    OCCULT("occult"),
    SEALED("sealed"),
    CYPHER("cypher"),
    OBFUSCATED("obfuscated"),
    ANGEL("angel");

    private final String name;

    GargoyleStatueTypes(String name) {
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
