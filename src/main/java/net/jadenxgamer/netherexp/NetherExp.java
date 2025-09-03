package net.jadenxgamer.netherexp;

import net.jadenxgamer.netherexp.compat.registry.SupplementariesCompat;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.registry.*;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(NetherExp.MOD_ID)
public final class NetherExp {
    public static final String MOD_ID = "netherexp";
    public static final Logger LOGGER = LoggerFactory.getLogger("Jaden's Nether Expansion");
    public static RegistryAccess registryAccess;

    public NetherExp(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, JNEConfigImpl.CONFIG);

        JNERegistries.init(modEventBus);
        JNEParticleTypes.init(modEventBus);
        JNESoundEvents.init(modEventBus);
        JNECreativeModeTabs.init(modEventBus);
        JNECriteriaTriggers.init(modEventBus);

        JNEEntityType.init(modEventBus);
        JNEMobEffects.init(modEventBus);
        JNEFluids.init(modEventBus);
        JNEBlocks.init(modEventBus);
        JNEItems.init(modEventBus);
        JNEBlockEntityType.init(modEventBus);
        initModCompat();
    }

    private static void initModCompat() {
        if (CompatUtil.SUPPLEMENTARIES) SupplementariesCompat.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation idVanilla(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }

    public static ResourceLocation idPath(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
