package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface BrewingIngredient {
    boolean matches(ItemStack stack);

    List<ItemStack> matchingStacks();
}
