package net.jadenxgamer.netherexp.fabric;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class JNEBuiltinPacks {
    public static void init() {
        ResourceLocation rpJNERetextures = new ResourceLocation(NetherExp.MOD_ID, "jne_retextures");
        ResourceLocation rpConflictingRetextures = new ResourceLocation(NetherExp.MOD_ID, "conflicting_retextures");
        ResourceLocation rpUniqueNetherWood = new ResourceLocation(NetherExp.MOD_ID, "unique_nether_wood");
        ResourceLocation dpLargerNetherBiomes = new ResourceLocation(NetherExp.MOD_ID, "larger_nether_biomes");
        ResourceLocation dpNetherDelightsCompat = new ResourceLocation(NetherExp.MOD_ID, "nethers_delight_compat");

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container -> {
            // ResourcePacks
            ResourceManagerHelper.registerBuiltinResourcePack(rpJNERetextures, container, Component.literal("JNE-Retextures"), ResourcePackActivationType.ALWAYS_ENABLED);
            ResourceManagerHelper.registerBuiltinResourcePack(rpConflictingRetextures, container, Component.literal("Conflicting Retextures"), ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(rpUniqueNetherWood, container, Component.literal("Unique Nether Wood"), ResourcePackActivationType.NORMAL);

            // DataPacks
            if (JNEConfigs.LARGER_NETHER_BIOMES.get()) {
                ResourceManagerHelper.registerBuiltinResourcePack(dpLargerNetherBiomes, container, Component.literal("Larger Nether Biomes"), ResourcePackActivationType.ALWAYS_ENABLED);
            }
            if (NetherExp.compatNethersDelight()) {
                ResourceManagerHelper.registerBuiltinResourcePack(dpNetherDelightsCompat, container, Component.literal("My Nethers Delight Compat"), ResourcePackActivationType.ALWAYS_ENABLED);
            }
        });
    }
}
