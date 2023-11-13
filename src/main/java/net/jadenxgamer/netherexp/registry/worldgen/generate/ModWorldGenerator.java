package net.jadenxgamer.netherexp.registry.worldgen.generate;

import net.jadenxgamer.netherexp.NetherExp;

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
        }
    }
}
