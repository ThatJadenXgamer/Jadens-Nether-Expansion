package net.jadenxgamer.netherexp.registry.config.other.modcompat;

import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.jadenxgamer.netherexp.registry.config.other.modcompat.cinderscapes.AshyShoalsConfigs;
import net.jadenxgamer.netherexp.registry.config.other.modcompat.cinderscapes.BlackstoneShalesConfigs;
import net.jadenxgamer.netherexp.registry.config.other.modcompat.cinderscapes.LuminousGroveConfigs;

public class CinderscapesConfigs {
    @ConfigEntry.Gui.CollapsibleObject()
    @ConfigEntry.Gui.Tooltip
    public LuminousGroveConfigs luminousGroveConfigs = new LuminousGroveConfigs();
    @ConfigEntry.Gui.CollapsibleObject()
    @ConfigEntry.Gui.Tooltip
    public AshyShoalsConfigs ashyShoalsConfigs = new AshyShoalsConfigs();
    @ConfigEntry.Gui.CollapsibleObject()
    @ConfigEntry.Gui.Tooltip
    public BlackstoneShalesConfigs blackstoneShalesConfigs = new BlackstoneShalesConfigs();
}
