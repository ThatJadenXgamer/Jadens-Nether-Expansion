package net.jadenxgamer.netherexp.registry.worldgen.generate;

public class ModWorldGenerator {
    public static void generateModWorldGen() {
        NetherWastesFeatures.generateFeatures();
        SoulSandValleyFeatures.generateFeatures();
        CrimsonForestFeatures.generateFeatures();
        WarpedForestFeatures.generateFeatures();
    }
}
