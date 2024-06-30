package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface BrewingIngredient {
    /*
     * Based on Cobblemon's Brewing Ingredient code that I converted to Java
     * https://gitlab.com/cable-mc/cobblemon/-/blob/main/common/src/main/kotlin/com/cobblemon/mod/common/brewing/ingredient/CobblemonIngredient.kt
     * https://modrinth.com/mod/cobblemon
     */

    boolean matches(ItemStack stack);

    List<ItemStack> matchingStacks();
}
