package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.List;

public class BrewingIngredientPotion implements BrewingIngredient {
    private final Potion potion;

    public BrewingIngredientPotion(Potion potion) {
        this.potion = potion;
    }

    @Override
    public boolean matches(ItemStack stack) {
        Potion stackPotion = PotionUtils.getPotion(stack);
        return stackPotion == potion;
    }

    @Override
    public List<ItemStack> matchingStacks() {
        List<ItemStack> list = new ArrayList<>();
        list.add(PotionUtils.setPotion(Items.POTION.getDefaultInstance(), potion));
        list.add(PotionUtils.setPotion(Items.SPLASH_POTION.getDefaultInstance(), potion));
        list.add(PotionUtils.setPotion(Items.LINGERING_POTION.getDefaultInstance(), potion));
        return list;
    }
}
