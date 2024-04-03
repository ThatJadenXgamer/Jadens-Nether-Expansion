package net.jadenxgamer.netherexp;

import dev.architectury.platform.Platform;
import net.jadenxgamer.netherexp.mixin.brewing.PotionBrewingAccessor;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.item.JNECreativeModeTabs;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.brewing.JNEPotions;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

public class NetherExp {
    public static final String MOD_ID = "netherexp";

    public static void init() {
        JNECreativeModeTabs.init();
        JNESoundEvents.init();
        JNEParticleTypes.init();
        JNEEnchantments.init();
        JNEMobEffects.init();

        JNEBlocks.init();
        JNEItems.init();
        JNEPotions.init();
    }

    public static boolean checkModCompatCinderscapes() {
        return Platform.isModLoaded("cinderscapes");
    }

    public static boolean checkModCompatGardensOfTheDead() {
        return Platform.isModLoaded("gardens_of_the_dead");
    }
}
