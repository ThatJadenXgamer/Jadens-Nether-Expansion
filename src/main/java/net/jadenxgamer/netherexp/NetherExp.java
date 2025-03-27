package net.jadenxgamer.netherexp;

import com.mojang.logging.LogUtils;
import net.jadenxgamer.netherexp.config.JNEForgeConfigs;
import net.jadenxgamer.netherexp.registry.advancements.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.enchantment.JNEEnchantments;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.JNECreativeModeTabs;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.loot.JNELootModifiers;
import net.jadenxgamer.netherexp.registry.misc_registry.JNEPaintings;
import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundEvents;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.worldgen.JNEBiomeModifiers;
import net.jadenxgamer.netherexp.registry.worldgen.feature.JNEFeature;
import net.jadenxgamer.netherexp.registry.worldgen.structure.JNEStructureType;
import net.minecraft.core.RegistryAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NetherExp.MOD_ID)
public class NetherExp {
    public static final String MOD_ID = "netherexp";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RegistryAccess registryAccess;

    public NetherExp() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> NetherExpClient::init);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, JNEForgeConfigs.COMMON);
        MinecraftForge.EVENT_BUS.register(this);

        JNECriteriaTriggers.init();
        JNECreativeModeTabs.init(modEventBus);
        JNESoundEvents.init(modEventBus);
        JNEParticleTypes.init(modEventBus);
        JNEEnchantments.init(modEventBus);
        JNEMobEffects.init(modEventBus);

        JNEEntityType.init(modEventBus);
        JNEStructureType.init(modEventBus);
        JNEFluids.init(modEventBus);
        JNEPaintings.init(modEventBus);
        JNEBlocks.init(modEventBus);
        JNEFeature.init(modEventBus);
        JNEBlockEntityType.init(modEventBus);
        JNEItems.init(modEventBus);

        JNEBiomeModifiers.init(modEventBus);
        JNELootModifiers.init(modEventBus);
    }
}
