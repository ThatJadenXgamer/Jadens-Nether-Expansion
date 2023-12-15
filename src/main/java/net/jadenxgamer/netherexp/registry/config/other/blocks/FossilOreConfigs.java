package net.jadenxgamer.netherexp.registry.config.other.blocks;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class FossilOreConfigs {

    @ConfigEntry.Gui.Tooltip
    public boolean skeleton_fossilization = true;

    @ConfigEntry.Gui.Tooltip
    public boolean wither_skeleton_fossilization = true;

    @ConfigEntry.Gui.Tooltip
    public boolean enable_fossil_ore_conversion = true;

    @ConfigEntry.Gui.Tooltip
    public int fossil_ore_conversion_odds = 50;
}
