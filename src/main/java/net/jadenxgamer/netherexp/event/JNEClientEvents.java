package net.jadenxgamer.netherexp.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.jadenxgamer.elysium_api.impl.client.assetdriven.lightmap_settings.LightmapSettingsManager;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.client.JNEFogRenderer;
import net.jadenxgamer.netherexp.client.gui.BetaPopupWarning;
import net.jadenxgamer.netherexp.client.rendering.JNERenderStateShard;
import net.jadenxgamer.netherexp.client.rendering.extensions.JNEFluidExtensions;
import net.jadenxgamer.netherexp.client.rendering.extensions.JNEItemExtensions;
import net.jadenxgamer.netherexp.client.shader.NetherHeatDistortionPostprocessor;
import net.jadenxgamer.netherexp.client.shader.SoulGlassPostProcessor;
import net.jadenxgamer.netherexp.core.block.MagmaCreamBlock;
import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

import java.io.IOException;

import static net.jadenxgamer.netherexp.NetherExpClient.shouldShowBetaPopup;
import static net.jadenxgamer.netherexp.config.JNEConfigs.ENABLE_NETHER_BIOME_LIGHTMAPS;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = NetherExp.MOD_ID, value = Dist.CLIENT)
public class JNEClientEvents {

    @SubscribeEvent
    public static void onClientTickPre(ClientTickEvent.Pre event) {
        var client = Minecraft.getInstance();
        NetherExpClient.SubmergedStates.tick();
        NetherExpClient.HandlePostShaders.tick(client);
    }

    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        if (shouldShowBetaPopup) {
            Minecraft client = Minecraft.getInstance();
            if (client.player != null && client.screen == null) {
                client.setScreen(new BetaPopupWarning());
                shouldShowBetaPopup = false;
            }
        }
        if (ENABLE_NETHER_BIOME_LIGHTMAPS.get()) LightmapSettingsManager.disableEventFlag(NetherExp.netherexpPath("disable_jne_lightmaps"));
        else LightmapSettingsManager.enableEventFlag(NetherExp.netherexpPath("disable_jne_lightmaps"));
    }

    @SubscribeEvent
    public static void onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
//        Minecraft client = Minecraft.getInstance();
//        if (JNEConfigs.SHOW_BETA_WARNING_POPUP.get()) shouldShowBetaPopup = true;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void fogRender(ViewportEvent.RenderFog event) {
        JNEFogRenderer.fogRender(event);
    }

    @SubscribeEvent
    public static void fogColor(ViewportEvent.ComputeFogColor event) {
        JNEFogRenderer.fogColor(event);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NetherExpClient.registerRenderers();
        PostProcessHandler.addInstance(SoulGlassPostProcessor.INSTANCE);
        PostProcessHandler.addInstance(NetherHeatDistortionPostprocessor.INSTANCE);
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
            event.registerShader(new ShaderInstance(event.getResourceProvider(), NetherExp.idPath(NetherExp.MOD_ID, "rendertype_entity_additive"), DefaultVertexFormat.NEW_ENTITY), JNERenderStateShard::setRenderTypeEntityAdditive);
        } catch (IOException exception) {
            NetherExp.LOGGER.error("Failed to load Shader Instances, {}", exception.getMessage());
        }
    }

    @SubscribeEvent
    private static void clientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(JNEFluidExtensions.ectoplasmExt, JNEFluids.ECTOPLASM_TYPE.get());
        event.registerItem(JNEItemExtensions.itemExt, JNEItems.WILL_O_WISP.get());
        event.registerItem(JNEItemExtensions.itemExt, JNEItems.SHOTGUN_FIST.get());
        event.registerItem(JNEItemExtensions.itemExt, JNEItems.PUMP_CHARGE_SHOTGUN.get());
    }

    @SubscribeEvent
    public static void itemTints(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tint) -> tint > 0 ? -1 : AntidoteContents.getColor(stack), JNEItems.ANTIDOTE.get());
        event.register((stack, tint) -> tint > 0 ? -1 : AntidoteContents.getColor(stack), JNEItems.GRENADE_ANTIDOTE.get());
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        var player = event.getEntity();
        var state = player.getBlockStateOn();
        if (state.is(JNEBlocks.MAGMA_CREAM_BLOCK.get()) && state.getValue(MagmaCreamBlock.UP)) {
            var input = event.getInput();

            input.forwardImpulse = 0.0F;
            input.leftImpulse = 0.0F;

            input.up = false;
            input.down = false;
            input.left = false;
            input.right = false;
        }
    }
}
