package net.jadenxgamer.netherexp.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.client.rendering.JNERenderStateShard;
import net.jadenxgamer.netherexp.client.rendering.extensions.JNEFluidExtensions;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.io.IOException;

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

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            NetherExpClient.registerLayerDefinitions(event);
        }

        @SubscribeEvent
        public static void registerShaders(RegisterShadersEvent event) {
            try {
                event.registerShader(new ShaderInstance(event.getResourceProvider(), NetherExp.idPath(NetherExp.MOD_ID, "rendertype_no_shade_entity_cutout"), DefaultVertexFormat.NEW_ENTITY), JNERenderStateShard::setRenderTypeNoShadeEntityCutout);
                event.registerShader(new ShaderInstance(event.getResourceProvider(), NetherExp.idPath(NetherExp.MOD_ID, "rendertype_no_shade_entity_cutout_no_cull"), DefaultVertexFormat.NEW_ENTITY), JNERenderStateShard::setRenderTypeNoShadeEntityCutoutNoCull);
            } catch (IOException exception) {
                NetherExp.LOGGER.error("Failed to load Shader Instances, {}", exception);
            }
        }

        @SubscribeEvent
        private static void clientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(JNEFluidExtensions.ectoplasmExt, JNEFluids.ECTOPLASM_TYPE.get());
        }
    }
}
