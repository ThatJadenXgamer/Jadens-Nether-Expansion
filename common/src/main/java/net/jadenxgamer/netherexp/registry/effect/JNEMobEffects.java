package net.jadenxgamer.netherexp.registry.effect;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.effect.custom.FogSightEffect;
import net.jadenxgamer.netherexp.registry.effect.custom.ImmunityEffect;
import net.jadenxgamer.netherexp.registry.effect.custom.UnboundedSpeed;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class JNEMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(NetherExp.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> FOGSIGHT = MOB_EFFECTS.register("fogsight", () ->
            new FogSightEffect(MobEffectCategory.BENEFICIAL, 6497843));

    public static final RegistrySupplier<MobEffect> UNBOUNDED_SPEED = MOB_EFFECTS.register("unbounded_speed", () ->
            new UnboundedSpeed(MobEffectCategory.BENEFICIAL, 1787717));

    // IMMUNITY EFFECTS

    public static final RegistrySupplier<MobEffect> SPEED_IMMUNITY = MOB_EFFECTS.register("speed_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 3402751, MobEffects.MOVEMENT_SPEED));

    public static final RegistrySupplier<MobEffect> SLOWNESS_IMMUNITY = MOB_EFFECTS.register("slowness_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 9154528, MobEffects.MOVEMENT_SLOWDOWN));

    public static final RegistrySupplier<MobEffect> STRENGTH_IMMUNITY = MOB_EFFECTS.register("strength_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 16762624, MobEffects.DAMAGE_BOOST));

    public static final RegistrySupplier<MobEffect> JUMP_BOOST_IMMUNITY = MOB_EFFECTS.register("jump_boost_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 16646020, MobEffects.JUMP));

    public static final RegistrySupplier<MobEffect> REGENERATION_IMMUNITY = MOB_EFFECTS.register("regeneration_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 13458603, MobEffects.REGENERATION));

    public static final RegistrySupplier<MobEffect> FIRE_RESISTANCE_IMMUNITY = MOB_EFFECTS.register("fire_resistance_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 16750848, MobEffects.FIRE_RESISTANCE));

    public static final RegistrySupplier<MobEffect> WATER_BREATHING_IMMUNITY = MOB_EFFECTS.register("water_breathing_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 10017472, MobEffects.WATER_BREATHING));

    public static final RegistrySupplier<MobEffect> INVISIBILITY_IMMUNITY = MOB_EFFECTS.register("invisibility_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 16185078, MobEffects.INVISIBILITY));

    public static final RegistrySupplier<MobEffect> WEAKNESS_IMMUNITY = MOB_EFFECTS.register("weakness_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 4738376, MobEffects.WEAKNESS));

    public static final RegistrySupplier<MobEffect> POISON_IMMUNITY = MOB_EFFECTS.register("poison_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 8889187, MobEffects.POISON));

    public static final RegistrySupplier<MobEffect> RESISTANCE_IMMUNITY = MOB_EFFECTS.register("resistance_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 9520880, MobEffects.DAMAGE_RESISTANCE));

    public static final RegistrySupplier<MobEffect> ABSORPTION_IMMUNITY = MOB_EFFECTS.register("absorption_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 2445989, MobEffects.ABSORPTION));

    public static final RegistrySupplier<MobEffect> HASTE_IMMUNITY = MOB_EFFECTS.register("haste_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, 14270531, MobEffects.DIG_SPEED));

    public static final RegistrySupplier<MobEffect> MINING_FATIGUE_IMMUNITY = MOB_EFFECTS.register("mining_fatigue_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 4866583, MobEffects.DIG_SLOWDOWN));

    public static final RegistrySupplier<MobEffect> DARKNESS_IMMUNITY = MOB_EFFECTS.register("darkness_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 2696993, MobEffects.DARKNESS));

    public static final RegistrySupplier<MobEffect> LEVITATION_IMMUNITY = MOB_EFFECTS.register("levitation_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 13565951, MobEffects.LEVITATION));

    public static final RegistrySupplier<MobEffect> HUNGER_IMMUNITY = MOB_EFFECTS.register("hunger_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 5797459, MobEffects.HUNGER));

    public static final RegistrySupplier<MobEffect> WITHER_IMMUNITY = MOB_EFFECTS.register("wither_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, 7561558, MobEffects.WITHER));

    public static void init() {
        MOB_EFFECTS.register();
    }
}
