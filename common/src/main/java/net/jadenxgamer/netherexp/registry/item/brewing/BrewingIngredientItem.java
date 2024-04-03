package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class BrewingIngredientItem implements BrewingIngredient{
    private final Item item;

    public BrewingIngredientItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.is(item);
    }

    @Override
    public List<ItemStack> matchingStacks() {
        return Collections.singletonList(item.getDefaultInstance());
    }
}
