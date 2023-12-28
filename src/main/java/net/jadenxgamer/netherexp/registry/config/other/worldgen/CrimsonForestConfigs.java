package net.jadenxgamer.netherexp.registry.config.other.worldgen;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class CrimsonForestConfigs {

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_sporeshroom = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_weeping_ivy = true;
}
