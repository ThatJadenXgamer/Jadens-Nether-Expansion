package net.jadenxgamer.netherexp.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.jadenxgamer.netherexp.NetherExpClient;

public class NetherExpFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetherExpClient.init();
    }
}
