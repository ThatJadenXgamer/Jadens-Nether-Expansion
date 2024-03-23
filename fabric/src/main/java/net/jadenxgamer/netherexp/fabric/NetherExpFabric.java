package net.jadenxgamer.netherexp.fabric;

import net.jadenxgamer.netherexp.NetherExp;
import net.fabricmc.api.ModInitializer;

public class NetherExpFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NetherExp.init();
    }
}
