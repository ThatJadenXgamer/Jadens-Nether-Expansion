package net.jadenxgamer.netherexp.registry.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class JNEFoodComponents {
    public static final FoodComponent HOGHAM = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).meat().statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 680, 0), 0.9f).meat().build();
    public static final FoodComponent COOKED_HOGHAM = new FoodComponent.Builder().hunger(8).saturationModifier(1.0f).meat().build();
    public static final FoodComponent GLOWCHEESE = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 120, 4), 0.6f).build();
    public static final FoodComponent PIZZA_SLICE = new FoodComponent.Builder().hunger(6).saturationModifier(1.0f).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 0), 1.0f).build();
    public static final FoodComponent WRAITHING_FLESH = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 2), 0.8F).meat().build();
}
