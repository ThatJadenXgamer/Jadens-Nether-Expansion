package net.jadenxgamer.netherexp.mixin.brewing;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryAccessor {

    @Invoker("registerPotionRecipe")
    static void netherexp$invokeRegisterPotionRecipe(Potion input, Item item, Potion output) {
        throw new AssertionError();
    }

    @Invoker("registerItemRecipe")
    static void netherexp$invokeRegisterItemRecipe(Item input, Item ingredient, Item output) {
        throw new AssertionError();
    }
}
