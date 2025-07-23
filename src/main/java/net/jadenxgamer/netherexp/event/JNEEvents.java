package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.datadriven.WispArchaeology;
import net.jadenxgamer.netherexp.registry.JNECreativeModeTabs;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = NetherExp.MOD_ID)
public class JNEEvents {

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        NetherExp.registryAccess = event.getServer().registryAccess();
    }

    @EventBusSubscriber(modid = NetherExp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event) {

        }

        @SubscribeEvent
        public static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
            JNECreativeModeTabs.addToExistingTabs(event);
        }

        @SubscribeEvent
        public static void datapackRegistry(DataPackRegistryEvent.NewRegistry event) {
            event.dataPackRegistry(JNERegistries.WISP_ARCHAEOLOGY, WispArchaeology.CODEC);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            JNEEntityType.registerAttributes(event);
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            JNEEntityType.registerSpawnPlacements(event);
        }

        @SubscribeEvent(priority = EventPriority.LOW)
        public static void registerEvent(RegisterEvent event) {
            JNEItems.backportRegistries(event);
        }
    }
}
