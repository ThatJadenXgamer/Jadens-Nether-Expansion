package net.jadenxgamer.netherexp.misc_registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.util.Identifier;

public class ModResourcePacks {

    public static void init() {

        Identifier packId = new Identifier(NetherExp.MOD_ID, "vanilla_nether");
        Identifier packId2 = new Identifier(NetherExp.MOD_ID, "emissive_nether_expansion");

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
        -> ResourceManagerHelper.registerBuiltinResourcePack(packId, container, "Vanilla Nether", ResourcePackActivationType.NORMAL));

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
        -> ResourceManagerHelper.registerBuiltinResourcePack(packId2, container, "Emissive Nether Expansion", ResourcePackActivationType.NORMAL));

    }

}
