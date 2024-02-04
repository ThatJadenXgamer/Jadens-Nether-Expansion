package net.jadenxgamer.netherexp.registry.misc_registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.util.Identifier;

public class ModResourcePacks {

    @SuppressWarnings("deprecation")
    public static void registerModResourcePacks() {
        // TODO: Add functionality to Vanilla and Emissive Packs
        Identifier vanillaPackId = new Identifier(NetherExp.MOD_ID, "vanilla_nether_expansion");
        Identifier emissivePackId = new Identifier(NetherExp.MOD_ID, "emissive_nether_expansion");
        Identifier retextureModdedWoodsetsPackId = new Identifier(NetherExp.MOD_ID, "retexture_modded_woodsets");
        Identifier cinderscapesCompatId = new Identifier(NetherExp.MOD_ID, "cinderscapes_compat_datapack");

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
        -> ResourceManagerHelper.registerBuiltinResourcePack(vanillaPackId, container, "Vanilla Nether Expansion", ResourcePackActivationType.NORMAL));

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
        -> ResourceManagerHelper.registerBuiltinResourcePack(emissivePackId, container, "Emissive Nether Expansion", ResourcePackActivationType.NORMAL));

        if (NetherExp.getConfig().modcompat.retexture_modded_woodsets && NetherExp.checkAnyModWithWood()) {
            FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
                    -> ResourceManagerHelper.registerBuiltinResourcePack(retextureModdedWoodsetsPackId, container, "Retexture Modded Woodsets", ResourcePackActivationType.ALWAYS_ENABLED));
        }

        if (NetherExp.checkModCompatCinderscapes()) {
            FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container
                    -> ResourceManagerHelper.registerBuiltinResourcePack(cinderscapesCompatId, container, "Cinderscapes Compat Datapack", ResourcePackActivationType.ALWAYS_ENABLED));
        }
        NetherExp.LOGGER.info("Registering Built-in ResourcePacks for " + NetherExp.MOD_ID);
    }
}
