package net.jadenxgamer.netherexp.registry.config.other.items;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class MistChargeConfigs {

    @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
    @ConfigEntry.Gui.Tooltip
    public int mist_charge_stack_size = 16;
}
