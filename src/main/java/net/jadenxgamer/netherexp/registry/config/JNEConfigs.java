package net.jadenxgamer.netherexp.registry.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.config.other.blocks.*;
import net.jadenxgamer.netherexp.registry.config.other.entities.BlazeConfigs;
import net.jadenxgamer.netherexp.registry.config.other.entities.MagmaCubeConfigs;
import net.jadenxgamer.netherexp.registry.config.other.entities.StriderConfigs;
import net.jadenxgamer.netherexp.registry.config.other.gamemechanics.SoulSpeedConfigs;
import net.jadenxgamer.netherexp.registry.config.other.items.MistChargeConfigs;
import net.jadenxgamer.netherexp.registry.config.other.modcompat.CinderscapesConfigs;
import net.jadenxgamer.netherexp.registry.config.other.sounds.BlockSoundsConfigs;
import net.jadenxgamer.netherexp.registry.config.other.worldgen.*;

@Config(name = NetherExp.MOD_ID)
@Config.Gui.Background("netherexp:textures/block/pyrite_nether_bricks_left.png")
public class JNEConfigs implements ConfigData {

    // BLOCKS //

    @ConfigEntry.Gui.CollapsibleObject()
    public Blocks blocks = new Blocks();

    public static class Blocks {
        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public DecayableConfigs decayableConfigs = new DecayableConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public PathBlockConfigs pathBlockConfigs = new PathBlockConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public WitherBoneBlockConfigs witherBoneBlockConfigs = new WitherBoneBlockConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public FossilOreConfigs fossilOreConfigs = new FossilOreConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public SoulGlassConfigs soulGlassConfigs = new SoulGlassConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public GeyserConfigs geyserConfigs = new GeyserConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public SoulSwirlsConfigs soulSwirlsConfigs = new SoulSwirlsConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public EctoSoulSandConfigs ectoSoulSandConfigs = new EctoSoulSandConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public RenewableConfigs renewableConfigs = new RenewableConfigs();
    }

    // ENTITY //

    @ConfigEntry.Gui.CollapsibleObject()
    public Entities entities = new Entities();

    public static class Entities {
        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public BlazeConfigs blazeConfigs = new BlazeConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public MagmaCubeConfigs magmaCubeConfigs = new MagmaCubeConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public StriderConfigs striderConfigs = new StriderConfigs();
    }

    // ITEM //

    @ConfigEntry.Gui.CollapsibleObject()
    public Items items = new Items();

    public static class Items {
        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public MistChargeConfigs mistChargeConfigs = new MistChargeConfigs();
    }

    // GAME MECHANICS //

    @ConfigEntry.Gui.CollapsibleObject()
    public GameMechanics gamemechanics = new GameMechanics();

    public static class GameMechanics {
        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public SoulSpeedConfigs soulSpeedConfigs = new SoulSpeedConfigs();

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.RequiresRestart
        public boolean enable_unfinished_items = false;
    }

    // SOUNDS //

    @ConfigEntry.Gui.CollapsibleObject()
    public Sounds sounds = new Sounds();

    public static class Sounds {
        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public BlockSoundsConfigs blockSoundsConfigs = new BlockSoundsConfigs();
    }

    // VISUAL EFFECTS //

    @ConfigEntry.Gui.CollapsibleObject()
    public VisualEffects visualeffects = new VisualEffects();

    public static class VisualEffects {
        @ConfigEntry.Gui.Tooltip
        public boolean ectoplasm_particles = true;

        @ConfigEntry.Gui.Tooltip
        public boolean black_ice_particles = true;

        @ConfigEntry.Gui.Tooltip
        public boolean improved_soul_fire_particles = true;
    }

    // WORLDGEN //

    @ConfigEntry.Gui.CollapsibleObject()
    public WorldGen worldgen = new WorldGen();

    public static class WorldGen {
        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public SoulSandValleyConfigs soulSandValleyConfigs = new SoulSandValleyConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public WarpedForestConfigs warpedForestConfigs = new WarpedForestConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public CrimsonForestConfigs crimsonForestConfigs = new CrimsonForestConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public BasaltDeltasConfigs basaltDeltasConfigs = new BasaltDeltasConfigs();

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip
        public NetherWastesConfigs netherWastesConfigs = new NetherWastesConfigs();
    }

    // MOD COMPAT //

    @ConfigEntry.Gui.CollapsibleObject()
    public ModCompat modcompat = new ModCompat();

    public static class ModCompat {
        @ConfigEntry.Gui.Tooltip()
        @ConfigEntry.Gui.RequiresRestart
        public boolean retexture_modded_woodsets = false;

        @ConfigEntry.Gui.CollapsibleObject()
        @ConfigEntry.Gui.Tooltip(count = 2)
        public CinderscapesConfigs cinderscapesConfigs = new CinderscapesConfigs();
    }
}
