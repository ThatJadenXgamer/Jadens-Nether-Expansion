package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNECreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

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
    }
}
