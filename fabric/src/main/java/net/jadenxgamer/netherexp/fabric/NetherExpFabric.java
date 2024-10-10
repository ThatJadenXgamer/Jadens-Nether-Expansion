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

        JNEBuiltinPacks.init();
    }
}
