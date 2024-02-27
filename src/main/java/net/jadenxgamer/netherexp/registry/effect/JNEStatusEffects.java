package net.jadenxgamer.netherexp.registry.effect;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class JNEStatusEffects {

    public static final StatusEffect FOGSIGHT = registerStatusEffect("fogsight",
            new FogSightEffect(StatusEffectCategory.BENEFICIAL, 10442064));

    public static final StatusEffect UNBOUNDED_SPEED = registerStatusEffect("unbounded_speed",
            new UnboundedSpeed(StatusEffectCategory.BENEFICIAL, 1787717));

    // IMMUNITY EFFECTS

    public static final StatusEffect SPEED_IMMUNITY = registerStatusEffect("speed_immunity",
            new ImmunityEffect(StatusEffectCategory.HARMFUL, 3402751, StatusEffects.SPEED));

    private static StatusEffect registerStatusEffect(String name, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(NetherExp.MOD_ID, name), entry);
    }

    public static void registerStatusEffects() {
        NetherExp.LOGGER.debug("Registering ModStatusEffects for " + NetherExp.MOD_ID);
    }
}
