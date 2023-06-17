package net.jadenxgamer.netherexp.misc_registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.util.Identifier;

public class ModResourcePacks {

    public static void init() {
        Identifier classicPackId = new Identifier(NetherExp.MOD_ID, "vanilla_nether");
        Identifier EmissivePackId = new Identifier(NetherExp.MOD_ID, "emissive_nether_expansion");

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
        -> ResourceManagerHelper.registerBuiltinResourcePack(classicPackId, container, "Classic Nether", ResourcePackActivationType.NORMAL));

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
        -> ResourceManagerHelper.registerBuiltinResourcePack(EmissivePackId, container, "Emissive Nether Expansion", ResourcePackActivationType.NORMAL));


        NetherExp.LOGGER.info("Registering Built-in ResourcePacks for " + NetherExp.MOD_ID);
    }

}
