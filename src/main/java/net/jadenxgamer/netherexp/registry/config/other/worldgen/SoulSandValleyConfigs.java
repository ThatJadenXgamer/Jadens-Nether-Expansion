package net.jadenxgamer.netherexp.registry.config.other.worldgen;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class SoulSandValleyConfigs {

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_jagged_soul_slate = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_soul_magma_blobs = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_bone_rods = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_fossil_fuel_ore = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_soul_swirls = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_ecto_soul_sand = true;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean generate_ectoplasm_lakes = true;
}
