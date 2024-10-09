package net.jadenxgamer.netherexp.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.fabric.loot.JNELootModifiers;
import net.jadenxgamer.netherexp.fabric.worldgen.*;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotionRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.TerraBlenderApi;

public class NetherExpFabric implements ModInitializer, TerraBlenderApi {
    @Override
    public void onInitialize() {
        NetherExp.init();
        JNEConfigHelperFabric.registerConfigs();
        NetherWastesFeatures.generateFeatures();
        SoulSandValleyFeatures.generateFeatures();
        CrimsonForestFeatures.generateFeatures();
        WarpedForestFeatures.generateFeatures();
        BasaltDeltasFeatures.generateFeatures();
        EntityGeneration.generateEntities();
        JNEPotionRecipe.addInvokerPotionRecipes();
        JNELootModifiers.modifyLootTables();

        FabricDefaultAttributeRegistry.register(
            JNEEntityType.APPARITION.get(), Apparition.createAttributes());
        FabricDefaultAttributeRegistry.register(JNEEntityType.WISP.get(), Wisp.createAttributes());
        FabricDefaultAttributeRegistry.register(
            JNEEntityType.VESSEL.get(), Vessel.createAttributes());
        FabricDefaultAttributeRegistry.register(
            JNEEntityType.ECTO_SLAB.get(), EctoSlab.createAttributes());
        FabricDefaultAttributeRegistry.register(
            JNEEntityType.BANSHEE.get(), Banshee.createAttributes());
        FabricDefaultAttributeRegistry.register(
            JNEEntityType.STAMPEDE.get(), Stampede.createAttributes());
        FabricDefaultAttributeRegistry.register(
            JNEEntityType.CARCASS.get(), Carcass.createAttributes());

        ResourceLocation rpJNERetextures = new ResourceLocation(NetherExp.MOD_ID, "jne_retextures");
        ResourceLocation rpConflictingRetextures =
            new ResourceLocation(NetherExp.MOD_ID, "conflicting_retextures");
        ResourceLocation rpUniqueNetherWood =
            new ResourceLocation(NetherExp.MOD_ID, "unique_nether_wood");
        ResourceLocation dpLargerNetherBiomes =
            new ResourceLocation(NetherExp.MOD_ID, "larger_nether_biomes");
        ResourceLocation dpNetherDelightsCompat =
            new ResourceLocation(NetherExp.MOD_ID, "nethers_delight_compat");

        FabricLoader.getInstance().getModContainer(NetherExp.MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(rpJNERetextures, container,
                Component.literal("JNE-Retextures"), ResourcePackActivationType.ALWAYS_ENABLED);
            ResourceManagerHelper.registerBuiltinResourcePack(rpConflictingRetextures, container,
                Component.literal("Conflicting Retextures"), ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(rpUniqueNetherWood, container,
                Component.literal("Unique Nether Wood"), ResourcePackActivationType.NORMAL);
            if (JNEConfigs.LARGER_NETHER_BIOMES.get()) {
                ResourceManagerHelper.registerBuiltinResourcePack(dpLargerNetherBiomes, container,
                    Component.literal("Larger Nether Biomes"),
                    ResourcePackActivationType.ALWAYS_ENABLED);
            }
            if (NetherExp.compatNethersDelight()) {
                ResourceManagerHelper.registerBuiltinResourcePack(dpNetherDelightsCompat, container,
                    Component.literal("My Nethers Delight Compat"),
                    ResourcePackActivationType.ALWAYS_ENABLED);
            }
        });
    }
}
