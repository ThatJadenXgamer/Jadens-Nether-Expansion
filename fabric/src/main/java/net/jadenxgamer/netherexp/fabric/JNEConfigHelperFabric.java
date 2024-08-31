package net.jadenxgamer.netherexp.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEForgeConfigs;
import net.minecraftforge.fml.config.ModConfig;

public class JNEConfigHelperFabric {

    public static void registerConfigs() {
        ForgeConfigRegistry.INSTANCE.register(NetherExp.MOD_ID, ModConfig.Type.COMMON, JNEForgeConfigs.COMMON);
    }
}
