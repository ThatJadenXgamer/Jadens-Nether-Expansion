package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class BrewingIngredientItem implements BrewingIngredient{
    private final Item item;

    public BrewingIngredientItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.isOf(item);
    }

    @Override
    public List<ItemStack> matchingStacks() {
        return Collections.singletonList(item.getDefaultStack());
    }
}
