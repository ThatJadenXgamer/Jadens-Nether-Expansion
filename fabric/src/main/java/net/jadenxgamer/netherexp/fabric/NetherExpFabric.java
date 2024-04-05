package net.jadenxgamer.netherexp.fabric;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.jadenxgamer.netherexp.NetherExp;
import net.fabricmc.api.ModInitializer;
import net.jadenxgamer.netherexp.fabric.item.brewing.JNEPotionRecipeFabric;
import net.jadenxgamer.netherexp.fabric.worldgen.SoulSandValleyFeatures;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityTypes;
import net.jadenxgamer.netherexp.registry.entity.custom.Apparition;

public class NetherExpFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NetherExp.init();
        SoulSandValleyFeatures.generateFeatures();
        JNEPotionRecipeFabric.init();

        FabricDefaultAttributeRegistry.register(JNEEntityTypes.APPARITION.get(), Apparition.createAttributes());
    }
}
