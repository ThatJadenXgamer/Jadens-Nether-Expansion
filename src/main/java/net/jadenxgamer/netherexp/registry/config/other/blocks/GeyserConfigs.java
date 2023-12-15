package net.jadenxgamer.netherexp.registry.config.other.blocks;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class GeyserConfigs {

    @ConfigEntry.Gui.Tooltip
    public boolean geyser_deals_damage = true;

    @ConfigEntry.Gui.Tooltip
    public boolean geyser_cooldown = true;

    @ConfigEntry.Gui.Tooltip
    public int geyser_cooldown_delay_ticks = 100;
}
