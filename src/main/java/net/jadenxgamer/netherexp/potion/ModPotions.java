package net.jadenxgamer.netherexp.potion;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.effect.ModStatusEffects;
import net.jadenxgamer.netherexp.mixin.item.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions{

    public static Potion FOGSIGHT_POTION = registerPotion("fogsight",
            new Potion(new StatusEffectInstance(ModStatusEffects.FOGSIGHT, 18000)));

    public static Potion LONG_FOGSIGHT_POTION = registerPotion("long_fogsight",
            new Potion("fogsight", new StatusEffectInstance(ModStatusEffects.FOGSIGHT, 36000)));

    private static Potion registerPotion(String name, Potion potion) {
        return Registry.register(Registries.POTION, new Identifier(NetherExp.MOD_ID, name), potion);
    }

    public static void registerModPotions() {
        NetherExp.LOGGER.debug("Registering Potions for " + NetherExp.MOD_ID);
    }
}
