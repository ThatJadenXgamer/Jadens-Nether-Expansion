package net.jadenxgamer.netherexp.data.worldgen;

import net.jadenxgamer.netherexp.data.worldgen.placement.JNEMiscPlacement;
import net.jadenxgamer.netherexp.data.worldgen.placement.JNESoulSandValleyPlacement;
import net.jadenxgamer.netherexp.data.worldgen.placement.JNEVanillaPlacement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class JNEPlacement {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        JNEMiscPlacement.bootstrap(context);
        JNESoulSandValleyPlacement.bootstrap(context);
        JNEVanillaPlacement.bootstrap(context);
    }
}
