package net.jadenxgamer.netherexp.registry.item.brewing;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.mixin.brewing.BrewingRecipeRegistryAccessor;
import net.jadenxgamer.netherexp.registry.effect.JNEStatusEffects;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class JNEPotions {

    public static Potion FOGSIGHT_POTION = registerPotion("fogsight",
            new Potion(new StatusEffectInstance(JNEStatusEffects.FOGSIGHT, 18000)));

    public static Potion LONG_FOGSIGHT_POTION = registerPotion("long_fogsight",
            new Potion("fogsight", new StatusEffectInstance(JNEStatusEffects.FOGSIGHT, 36000)));

    private static Potion registerPotion(String name, Potion potion) {
        return Registry.register(Registries.POTION, new Identifier(NetherExp.MOD_ID, name), potion);
    }

    private static void registerPotionRecipe() {
        BrewingRecipeRegistryAccessor.netherexp$invokeRegisterPotionRecipe(Potions.AWKWARD, JNEItems.FOGGY_ESSENCE, FOGSIGHT_POTION);
        BrewingRecipeRegistryAccessor.netherexp$invokeRegisterPotionRecipe(Potions.AWKWARD, JNEItems.FOGGY_ESSENCE, FOGSIGHT_POTION);
        BrewingRecipeRegistryAccessor.netherexp$invokeRegisterPotionRecipe(FOGSIGHT_POTION, Items.REDSTONE, LONG_FOGSIGHT_POTION);
        BrewingRecipeRegistryAccessor.netherexp$invokeRegisterPotionRecipe(Potions.AWKWARD, JNEItems.WARPHOPPER_FUR, Potions.INVISIBILITY);
    }

    public static void registerPotions() {
        NetherExp.LOGGER.debug("Registering Potions for " + NetherExp.MOD_ID);
        registerPotionRecipe();
    }
}
