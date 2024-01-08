package net.jadenxgamer.netherexp.registry.config.other.blocks;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class SoulSwirlsConfigs {

    @ConfigEntry.Gui.Tooltip
    public boolean soul_swirls_boosting = true;

    @ConfigEntry.Gui.Tooltip
    public int soul_swirls_cooldown_ticks = 1000;

    @ConfigEntry.Gui.Tooltip
    public int unbounded_speed_duration = 10;
}
