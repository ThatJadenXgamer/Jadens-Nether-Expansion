package net.jadenxgamer.netherexp.forge.item.brewing;

import net.jadenxgamer.netherexp.mixin.brewing.PotionBrewingAccessor;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class JNEPotionRecipeForge {

    public static void init() {
        PotionBrewingAccessor.netherexp$addMix(Potions.AWKWARD, JNEItems.FOGGY_ESSENCE.get(), JNEPotions.FOGSIGHT.get());
        PotionBrewingAccessor.netherexp$addMix(JNEPotions.FOGSIGHT.get(), Items.REDSTONE, JNEPotions.LONG_FOGSIGHT.get());
    }
}
