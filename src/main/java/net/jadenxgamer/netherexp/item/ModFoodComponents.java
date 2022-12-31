package net.jadenxgamer.netherexp.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent HOGHAM = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).meat().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3f).meat().build();
    public static final FoodComponent COOKED_HOGHAM = new FoodComponent.Builder().hunger(5).saturationModifier(0.7f).meat().build();
    public static final FoodComponent SPORES = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40,1),0.7f).build();
}
