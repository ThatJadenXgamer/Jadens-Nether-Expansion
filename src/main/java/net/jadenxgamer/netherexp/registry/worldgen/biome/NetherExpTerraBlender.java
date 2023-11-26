package net.jadenxgamer.netherexp.registry.worldgen.biome;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.worldgen.biome.nether.WrathScapesRegion;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class NetherExpTerraBlender implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new WrathScapesRegion(new Identifier(NetherExp.MOD_ID, "wrath_scapes"), 20));
    }
}