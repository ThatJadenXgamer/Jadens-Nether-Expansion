package net.jadenxgamer.netherexp.registry.worldgen.biome;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.biome.nether.WrathRegion;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class NetherExpTerraBlender implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        // Generates Vent Mire
        Regions.register(new WrathRegion(new Identifier(NetherExp.MOD_ID, "wrath"), 5));
    }
}