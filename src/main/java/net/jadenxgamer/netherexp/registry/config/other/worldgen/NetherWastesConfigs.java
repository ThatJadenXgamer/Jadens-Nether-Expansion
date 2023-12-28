package net.jadenxgamer.netherexp.registry.config.other.worldgen;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class NetherWastesConfigs {

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_quartz_crystals = true;
}
