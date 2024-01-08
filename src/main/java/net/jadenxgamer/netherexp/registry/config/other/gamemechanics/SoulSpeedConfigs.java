package net.jadenxgamer.netherexp.registry.config.other.gamemechanics;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class SoulSpeedConfigs {
    @ConfigEntry.Gui.Tooltip
    public boolean nerfed_soul_sand_slowness = false;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart(false)
    public boolean no_soul_speed_degradation = true;
}
