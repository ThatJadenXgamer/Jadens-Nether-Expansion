package net.jadenxgamer.netherexp;

import com.google.common.base.Suppliers;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrarManager;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.item.JNECreativeModeTabs;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;

import java.util.function.Supplier;

public class NetherExp {
    public static final String MOD_ID = "netherexp";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static void init() {
        JNECreativeModeTabs.init();
        JNESoundEvents.init();
        JNEParticleTypes.init();

        JNEBlocks.init();
        JNEItems.init();
    }

    public static boolean checkModCompatCinderscapes() {
        return Platform.isModLoaded("cinderscapes");
    }

    public static boolean checkModCompatGardensOfTheDead() {
        return Platform.isModLoaded("gardens_of_the_dead");
    }
}
