package net.jadenxgamer.netherexp.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DimensionEffectUtil {

    public static boolean isOverworld(Level level) {
        return level.dimensionType().effectsLocation().equals(new ResourceLocation("minecraft", "overworld"));
    }
    public static boolean isNether(Level level) {
        return level.dimensionType().effectsLocation().equals(new ResourceLocation("minecraft", "the_nether"));
    }
    public static boolean isTheEnd(Level level) {
        return level.dimensionType().effectsLocation().equals(new ResourceLocation("minecraft", "the_end"));
    }
    public static boolean isCustomDimension(Level level, String modId, String id) {
        return level.dimensionType().effectsLocation().equals(new ResourceLocation(modId, id));
    }
}
