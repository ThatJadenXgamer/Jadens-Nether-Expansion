package net.jadenxgamer.netherexp.registry.worldgen.generate;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.generate.biome.*;
import net.jadenxgamer.netherexp.registry.worldgen.generate.mod_compat.AshyShoalsFeatures;
import net.jadenxgamer.netherexp.registry.worldgen.generate.mod_compat.BlackstoneShalesFeatures;
import net.jadenxgamer.netherexp.registry.worldgen.generate.mod_compat.LuminousGroveFeatures;

public class ModWorldGenerator {
    public static void generateModWorldGen() {
        NetherWastesFeatures.generateFeatures();
        SoulSandValleyFeatures.generateFeatures();
        CrimsonForestFeatures.generateFeatures();
        WarpedForestFeatures.generateFeatures();
        BasaltDeltasFeatures.generateFeatures();
        VentMireFeatures.generateFeatures();

        if (NetherExp.checkModCompatCinderscapes()) {
            LuminousGroveFeatures.generateFeatures();
            AshyShoalsFeatures.generateFeatures();
            BlackstoneShalesFeatures.generateFeatures();
        }
    }
}
