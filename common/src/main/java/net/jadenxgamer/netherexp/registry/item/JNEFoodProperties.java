package net.jadenxgamer.netherexp.registry.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class JNEFoodProperties {
    public static final FoodProperties HOGHAM = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).meat().effect(new MobEffectInstance(MobEffects.WEAKNESS, 1200, 0), 0.9f).meat().build();
    public static final FoodProperties COOKED_HOGHAM = new FoodProperties.Builder().nutrition(8).saturationMod(0.8f).meat().build();
    public static final FoodProperties GLOWCHEESE = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).effect(new MobEffectInstance(MobEffects.HUNGER, 300, 5), 0.6f).build();
    public static final FoodProperties PIZZA_SLICE = new FoodProperties.Builder().nutrition(6).saturationMod(1.0f).effect(new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).build();
    public static final FoodProperties WRAITHING_FLESH = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 2), 0.8F).meat().build();
}
