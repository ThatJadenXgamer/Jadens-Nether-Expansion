package net.jadenxgamer.netherexp.registry.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent HOGHAM = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).meat().statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 680, 0), 0.9f).meat().build();
    public static final FoodComponent COOKED_HOGHAM = new FoodComponent.Builder().hunger(8).saturationModifier(1.0f).meat().build();
    public static final FoodComponent GLOW_CHEESE = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build();
}
