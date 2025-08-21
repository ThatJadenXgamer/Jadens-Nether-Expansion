package net.jadenxgamer.netherexp.core.block.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public class Liquidlogged {

    public enum WaterEctoplasm implements StringRepresentable {
        AIR("air"),
        WATER("water"),
        ECTOPLASM("ectoplasm");

        private final String name;

        WaterEctoplasm(String name) {
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
}