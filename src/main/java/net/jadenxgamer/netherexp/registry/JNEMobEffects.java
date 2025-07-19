package net.jadenxgamer.netherexp.registry;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.effect.ImmunityEffect;
import net.jadenxgamer.netherexp.core.effect.IncurableEffect;
import net.jadenxgamer.netherexp.core.effect.JNEMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class JNEMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, NetherExp.MOD_ID);

    public static final Holder<MobEffect> UNBOUNDED_SPEED = MOB_EFFECTS.register("unbounded_speed", () ->
            new JNEMobEffect(MobEffectCategory.BENEFICIAL, 1787717));

    public static final Holder<MobEffect> BETRAYED = MOB_EFFECTS.register("betrayed", () ->
            new IncurableEffect(MobEffectCategory.NEUTRAL, 11730944));

    /**
     * Immunity Effects
     */

    public static final Holder<MobEffect> SPEED_IMMUNITY = MOB_EFFECTS.register("speed_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "speed")));

    public static final Holder<MobEffect> SLOWNESS_IMMUNITY = MOB_EFFECTS.register("slowness_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "slowness")));

    public static final Holder<MobEffect> STRENGTH_IMMUNITY = MOB_EFFECTS.register("strength_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "strength")));

    public static final Holder<MobEffect> JUMP_BOOST_IMMUNITY = MOB_EFFECTS.register("jump_boost_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "jump_boost")));

    public static final Holder<MobEffect> REGENERATION_IMMUNITY = MOB_EFFECTS.register("regeneration_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "regeneration")));

    public static final Holder<MobEffect> FIRE_RESISTANCE_IMMUNITY = MOB_EFFECTS.register("fire_resistance_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "fire_resistance")));

    public static final Holder<MobEffect> WATER_BREATHING_IMMUNITY = MOB_EFFECTS.register("water_breathing_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "water_breathing")));

    public static final Holder<MobEffect> INVISIBILITY_IMMUNITY = MOB_EFFECTS.register("invisibility_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "invisibility")));

    public static final Holder<MobEffect> WEAKNESS_IMMUNITY = MOB_EFFECTS.register("weakness_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "weakness")));

    public static final Holder<MobEffect> POISON_IMMUNITY = MOB_EFFECTS.register("poison_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "poison")));

    public static final Holder<MobEffect> RESISTANCE_IMMUNITY = MOB_EFFECTS.register("resistance_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "resistance")));

    public static final Holder<MobEffect> ABSORPTION_IMMUNITY = MOB_EFFECTS.register("absorption_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "absorption")));

    public static final Holder<MobEffect> HASTE_IMMUNITY = MOB_EFFECTS.register("haste_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "haste")));

    public static final Holder<MobEffect> MINING_FATIGUE_IMMUNITY = MOB_EFFECTS.register("mining_fatigue_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "mining_fatigue")));

    public static final Holder<MobEffect> DARKNESS_IMMUNITY = MOB_EFFECTS.register("darkness_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "darkness")));

    public static final Holder<MobEffect> LEVITATION_IMMUNITY = MOB_EFFECTS.register("levitation_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "levitation")));

    public static final Holder<MobEffect> HUNGER_IMMUNITY = MOB_EFFECTS.register("hunger_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "hunger")));

    public static final Holder<MobEffect> WITHER_IMMUNITY = MOB_EFFECTS.register("wither_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "wither")));

    public static final Holder<MobEffect> LUCK_IMMUNITY = MOB_EFFECTS.register("luck_immunity", () ->
            new ImmunityEffect(MobEffectCategory.HARMFUL, NetherExp.idPath("minecraft", "luck")));

    public static final Holder<MobEffect> UNLUCK_IMMUNITY = MOB_EFFECTS.register("unluck_immunity", () ->
            new ImmunityEffect(MobEffectCategory.BENEFICIAL, NetherExp.idPath("minecraft", "unluck")));


    public static void init(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
