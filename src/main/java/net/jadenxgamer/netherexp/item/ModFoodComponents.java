package net.jadenxgamer.netherexp.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent HOGHAM = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).meat().statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 680, 0), 0.3f).meat().build();
    public static final FoodComponent COOKED_HOGHAM = new FoodComponent.Builder().hunger(7).saturationModifier(0.8f).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 0),0.3f).meat().build();
}
