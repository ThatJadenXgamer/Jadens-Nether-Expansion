package net.jadenxgamer.netherexp.core.misc;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class JNEFoods {
    public static final FoodProperties HOGHAM = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 1200, 0), 0.9f).build();
    public static final FoodProperties COOKED_HOGHAM = new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).build();
    public static final FoodProperties GLOWCHEESE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 5), 0.6f).build();
    public static final FoodProperties PIZZA_SLICE = new FoodProperties.Builder().nutrition(6).saturationModifier(1.0f).effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).build();
    public static final FoodProperties WRAITHING_FLESH = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 2), 0.8F).build();
    public static final FoodProperties CEREBRAGE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).fast().build();
    public static final FoodProperties ROASTED_BONE = new FoodProperties.Builder().nutrition(9).saturationModifier(0.8f).fast().effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 80, 0), 0.5F).build();
}