package net.jadenxgamer.netherexp;

import com.mojang.logging.LogUtils;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.registry.*;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(NetherExp.MOD_ID)
public final class NetherExp {
    public static final String MOD_ID = "netherexp";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RegistryAccess registryAccess;

    public NetherExp(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, JNEConfigImpl.CONFIG);

        JNEParticleTypes.init(modEventBus);
        JNESoundEvents.init(modEventBus);
        JNECreativeModeTabs.init(modEventBus);

        JNEEntityType.init(modEventBus);
        JNEMobEffects.init(modEventBus);
        JNEBlocks.init(modEventBus);
        JNEItems.init(modEventBus);
        JNEBlockEntityType.init(modEventBus);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation idPath(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
