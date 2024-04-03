package net.jadenxgamer.netherexp.fabric;

import net.jadenxgamer.netherexp.NetherExp;
import net.fabricmc.api.ModInitializer;
import net.jadenxgamer.netherexp.fabric.item.brewing.JNEPotionRecipeFabric;
import net.jadenxgamer.netherexp.mixin.brewing.PotionBrewingAccessor;
import net.jadenxgamer.netherexp.fabric.worldgen.SoulSandValleyFeatures;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class NetherExpFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NetherExp.init();
        SoulSandValleyFeatures.generateFeatures();
        JNEPotionRecipeFabric.init();
    }
}
