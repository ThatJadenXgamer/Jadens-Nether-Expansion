package net.jadenxgamer.netherexp.registry.item.brewing;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;

import java.util.ArrayList;
import java.util.List;

public class BrewingIngredientPotion implements BrewingIngredient {
    private final Potion potion;

    public BrewingIngredientPotion(Potion potion) {
        this.potion = potion;
    }

    @Override
    public boolean matches(ItemStack stack) {
        Potion stackPotion = PotionUtil.getPotion(stack);
        return stackPotion == potion;
    }

    @Override
    public List<ItemStack> matchingStacks() {
        List<ItemStack> list = new ArrayList<>();
        list.add(PotionUtil.setPotion(Items.POTION.getDefaultStack(), potion));
        list.add(PotionUtil.setPotion(Items.SPLASH_POTION.getDefaultStack(), potion));
        list.add(PotionUtil.setPotion(Items.LINGERING_POTION.getDefaultStack(), potion));
        return list;
    }
}
