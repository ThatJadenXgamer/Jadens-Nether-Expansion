package net.jadenxgamer.netherexp.registry.item.brewing;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class JNEPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(NetherExp.MOD_ID, Registries.POTION);

    public static final RegistrySupplier<Potion> FOGSIGHT = POTIONS.register("fogsight", () ->
            new Potion(new MobEffectInstance(JNEMobEffects.FOGSIGHT.get(), 18000)));

    public static final RegistrySupplier<Potion> LONG_FOGSIGHT = POTIONS.register("long_fogsight", () ->
            new Potion("fogsight", new MobEffectInstance(JNEMobEffects.FOGSIGHT.get(), 36000)));

    public static void init() {
        POTIONS.register();
    }
}
