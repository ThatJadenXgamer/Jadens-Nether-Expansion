package net.jadenxgamer.netherexp;

import dev.architectury.platform.Platform;
import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.item.JNECreativeModeTabs;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotions;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;

public class NetherExp {
    public static final String MOD_ID = "netherexp";

    public static void init() {
        JNECriteriaTriggers.init();
        JNECreativeModeTabs.init();
        JNESoundEvents.init();
        JNEParticleTypes.init();
        JNEEnchantments.init();
        JNEMobEffects.init();

        JNEEntityType.init();
        JNEBlocks.init();
        JNEBlockEntityType.init();
        JNEItems.init();
        JNEPotions.init();
    }

    // MOD COMPATIBILITY CHECKS

    public static boolean compatCinderscapes() {
        return Platform.isModLoaded("cinderscapes");
    }

    public static boolean compatGardensOfTheDead() {
        return Platform.isModLoaded("gardens_of_the_dead");
    }

    public static boolean compatInfernalExpansion() {
        return Platform.isModLoaded("infernalexp");
    }

    public static boolean compatGreedAndBleed() {
        return Platform.isModLoaded("greedandbleed");
    }

    public static boolean compatBiomesOPlenty() {
        return Platform.isModLoaded("biomesoplenty");
    }

    public static boolean compatQuark() {
        return Platform.isModLoaded("quark");
    }

    public static boolean compatDiceyVentures() {
        return Platform.isModLoaded("dicey_ventures");
    }
}
