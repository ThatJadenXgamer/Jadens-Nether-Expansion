package net.jadenxgamer.netherexp.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.fabric.worldgen.*;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.custom.*;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotionRecipe;
import terrablender.api.TerraBlenderApi;

public class NetherExpFabric implements ModInitializer, TerraBlenderApi {
    @Override
    public void onInitialize() {
        NetherExp.init();
        NetherWastesFeatures.generateFeatures();
        SoulSandValleyFeatures.generateFeatures();
        CrimsonForestFeatures.generateFeatures();
        WarpedForestFeatures.generateFeatures();
        BasaltDeltasFeatures.generateFeatures();
        EntityGeneration.generateEntities();
        JNEPotionRecipe.addInvokerPotionRecipes();

        FabricDefaultAttributeRegistry.register(JNEEntityType.APPARITION.get(), Apparition.createAttributes());
        FabricDefaultAttributeRegistry.register(JNEEntityType.WISP.get(), Wisp.createAttributes());
        FabricDefaultAttributeRegistry.register(JNEEntityType.VESSEL.get(), Vessel.createAttributes());
        FabricDefaultAttributeRegistry.register(JNEEntityType.ECTO_SLAB.get(), EctoSlab.createAttributes());
        FabricDefaultAttributeRegistry.register(JNEEntityType.STAMPEDE.get(), Stampede.createAttributes());
    }
}
