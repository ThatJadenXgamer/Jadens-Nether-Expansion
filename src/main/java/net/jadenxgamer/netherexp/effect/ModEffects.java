package net.jadenxgamer.netherexp.effect;

import net.jadenxgamer.netherexp.NetherExp;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static StatusEffect Hallucination;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(NetherExp.MOD_ID, name),
                new HallucinationEffect(StatusEffectCategory.HARMFUL, 10106549));
    }

    public static void registerEffects() {
        Hallucination = registerStatusEffect("hallucination");
    }

}
