package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = NetherExp.MOD_ID, value = Dist.CLIENT)
public class JNEClientEvents {

    @SubscribeEvent
    public static void onClientTickPre(ClientTickEvent.Pre event) {

    }

    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {

    }

    @EventBusSubscriber(modid = NetherExp.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModBusClientEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            NetherExpClient.registerEntityRenderers();
        }

        @SubscribeEvent
        public static void renderParticles(RegisterParticleProvidersEvent event) {
            NetherExpClient.registerParticles(event);
        }
    }
}
