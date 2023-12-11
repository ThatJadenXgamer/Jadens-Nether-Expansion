package net.jadenxgamer.netherexp.registry.effect;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {

    public static final StatusEffect FOGSIGHT = registerStatusEffect("fogsight",
            new FogSightEffect(StatusEffectCategory.BENEFICIAL, 10442064));

    public static final StatusEffect SOUL_SHIELD = registerStatusEffect("soul_shield",
            new SoulShieldEffect(StatusEffectCategory.BENEFICIAL, 1717297));

    private static StatusEffect registerStatusEffect(String name, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(NetherExp.MOD_ID, name), entry);
    }

    public static void registerModStatusEffects() {
        NetherExp.LOGGER.debug("Registering ModStatusEffects for " + NetherExp.MOD_ID);
    }
}
