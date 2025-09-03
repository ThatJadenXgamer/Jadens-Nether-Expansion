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

    public enum AllFluids implements StringRepresentable {
        AIR("air"),
        WATER("water"),
        LAVA("lava"),
        ECTOPLASM("ectoplasm");

        private final String name;

        AllFluids(String name) {
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