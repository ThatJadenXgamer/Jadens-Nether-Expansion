package net.jadenxgamer.netherexp.registry.effect;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.effect.custom.FogSightEffect;
import net.jadenxgamer.netherexp.registry.effect.custom.ImmunityEffect;
import net.jadenxgamer.netherexp.registry.effect.custom.UnboundedSpeed;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class JNEStatusEffects {

    public static final StatusEffect FOGSIGHT = registerStatusEffect("fogsight",
            new FogSightEffect(StatusEffectCategory.BENEFICIAL, 10442064));

    public static final StatusEffect UNBOUNDED_SPEED = registerStatusEffect("unbounded_speed",
            new UnboundedSpeed(StatusEffectCategory.BENEFICIAL, 1787717));

    // IMMUNITY EFFECTS

    public static final StatusEffect SPEED_IMMUNITY = registerStatusEffect("speed_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 3402751, StatusEffects.SPEED));

    public static final StatusEffect SLOWNESS_IMMUNITY = registerStatusEffect("slowness_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 9154528, StatusEffects.SLOWNESS));

    public static final StatusEffect STRENGTH_IMMUNITY = registerStatusEffect("strength_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 16762624, StatusEffects.STRENGTH));

    public static final StatusEffect JUMP_BOOST_IMMUNITY = registerStatusEffect("jump_boost_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 16646020, StatusEffects.JUMP_BOOST));

    public static final StatusEffect REGENERATION_IMMUNITY = registerStatusEffect("regeneration_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 13458603, StatusEffects.REGENERATION));

    public static final StatusEffect FIRE_RESISTANCE_IMMUNITY = registerStatusEffect("fire_resistance_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 16750848, StatusEffects.FIRE_RESISTANCE));

    public static final StatusEffect WATER_BREATHING_IMMUNITY = registerStatusEffect("water_breathing_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 10017472, StatusEffects.WATER_BREATHING));

    public static final StatusEffect INVISIBILITY_IMMUNITY = registerStatusEffect("invisibility_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 16185078, StatusEffects.INVISIBILITY));

    public static final StatusEffect WEAKNESS_IMMUNITY = registerStatusEffect("weakness_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 4738376, StatusEffects.WEAKNESS));

    public static final StatusEffect POISON_IMMUNITY = registerStatusEffect("poison_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 8889187, StatusEffects.POISON));

    public static final StatusEffect RESISTANCE_IMMUNITY = registerStatusEffect("resistance_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 9520880, StatusEffects.RESISTANCE));

    public static final StatusEffect ABSORPTION_IMMUNITY = registerStatusEffect("absorption_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 2445989, StatusEffects.ABSORPTION));

    public static final StatusEffect HASTE_IMMUNITY = registerStatusEffect("haste_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 14270531, StatusEffects.HASTE));

    public static final StatusEffect MINING_FATIGUE_IMMUNITY = registerStatusEffect("mining_fatigue_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 4866583, StatusEffects.MINING_FATIGUE));

    public static final StatusEffect DARKNESS_IMMUNITY = registerStatusEffect("darkness_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 2696993, StatusEffects.DARKNESS));

    public static final StatusEffect LEVITATION_IMMUNITY = registerStatusEffect("levitation_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 13565951, StatusEffects.LEVITATION));

    public static final StatusEffect HUNGER_IMMUNITY = registerStatusEffect("hunger_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 5797459, StatusEffects.HUNGER));

    public static final StatusEffect WITHER_IMMUNITY = registerStatusEffect("wither_immunity",
            new ImmunityEffect(StatusEffectCategory.BENEFICIAL, 7561558, StatusEffects.WITHER));

    private static StatusEffect registerStatusEffect(String name, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(NetherExp.MOD_ID, name), entry);
    }

    public static void registerStatusEffects() {
        NetherExp.LOGGER.debug("Registering ModStatusEffects for " + NetherExp.MOD_ID);
    }
}
