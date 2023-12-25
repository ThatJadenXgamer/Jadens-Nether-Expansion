package net.jadenxgamer.netherexp.registry.config.other.worldgen;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class WarpedForestConfigs {

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_sporeshroom = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_wart_beard = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_spotted_wart_blocks = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_twisting_ivy = true;
}
