package net.jadenxgamer.netherexp.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.config.enums.NetherFogDistance;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.client.AgitatedOverlay;
import net.jadenxgamer.netherexp.registry.effect.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.custom.AntidoteItem;
import net.jadenxgamer.netherexp.util.DimensionEffectUtil;
import net.jadenxgamer.netherexp.util.NetherFogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = NetherExp.MOD_ID, value = Dist.CLIENT)
public class JNEClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (NetherExpClient.loadShaderAttempt > 0) {
                NetherExpClient.loadShaderAttempt--;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void fogRenderer(ViewportEvent.RenderFog event) {
        if (event.isCanceled() || event.getType() != FogType.NONE) return;
        Entity player = Minecraft.getInstance().getCameraEntity();
        BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
        float fogStart = RenderSystem.getShaderFogStart();
        float fogEnd = RenderSystem.getShaderFogEnd();
        float viewDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
        if (player.getEyeInFluidType().equals(JNEFluids.ECTOPLASM_TYPE.get())) {
            event.setCanceled(true);
            event.setNearPlaneDistance(-8.0F);
            event.setFarPlaneDistance(8.0F);
            return;
        }
        else if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            event.setCanceled(true);
            event.setNearPlaneDistance(2.0F);
            event.setFarPlaneDistance(6.0F);
            return;
        }
        else if (player instanceof LivingEntity livingEntity && livingEntity.hasEffect(JNEMobEffects.BETRAYED.get())) {
            event.setCanceled(true);
            event.setNearPlaneDistance(1.5f);
            event.setFarPlaneDistance(18.0F);
        }
        else if (player instanceof LivingEntity livingEntity && JNEConfigs.NETHER_FOG_DISTANCE.get() != NetherFogDistance.VANILLA) {
            if (livingEntity.hasEffect(MobEffects.BLINDNESS) || livingEntity.hasEffect(MobEffects.DARKNESS)) return;
            if (!DimensionEffectUtil.isNether(livingEntity.level())) return;

            event.setCanceled(true);
            event.setNearPlaneDistance(NetherFogUtil.getFogStart());
            event.setFarPlaneDistance(NetherFogUtil.getFogEnd(viewDistance));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void fogColor(ViewportEvent.ComputeFogColor event) {
        Entity player = Minecraft.getInstance().player;
        BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
        if (player.getEyeInFluidType() != null && player.getEyeInFluidType().equals(JNEFluids.ECTOPLASM_TYPE.get())) {
            event.setRed(0.02f);
            event.setGreen(0.333f);
            event.setBlue(0.357f);
        }
        else if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            event.setRed(0.106f);
            event.setGreen(0.278f);
            event.setBlue(0.271f);
        }
        else if (player instanceof LivingEntity livingEntity && livingEntity.hasEffect(JNEMobEffects.BETRAYED.get())) {
            event.setRed(0.639f);
            event.setGreen(0.0f);
            event.setBlue(0.0f);
        }
    }

    @SubscribeEvent
    public static void postEffectRender(RenderLevelStageEvent event) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            GameRenderer renderer = Minecraft.getInstance().gameRenderer;
            if (player instanceof LivingEntity livingEntity && livingEntity.hasEffect(JNEMobEffects.BETRAYED.get()) && JNEConfigs.TREACHEROUS_CANDLE_RED_LIGHTS.get()) {
                if (renderer.currentEffect() == null || !NetherExpClient.BETRAYED_SHADER.toString().equals(renderer.currentEffect().getName())) {
                    NetherExpClient.attemptLoadShader(NetherExpClient.BETRAYED_SHADER);
                }
            }
            else if (renderer.currentEffect() != null && NetherExpClient.BETRAYED_SHADER.toString().equals(renderer.currentEffect().getName())) {
                renderer.checkEntityPostEffect(null);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = NetherExp.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("agitated", AgitatedOverlay.HUD);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            NetherExpClient.registerEntityRenderers();
            NetherExpClient.registerEnqueuedWorkers(event);
        }

        @SubscribeEvent
        public static void itemTints(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tint) -> tint > 0 ? -1 : AntidoteItem.getColor(stack), JNEItems.ANTIDOTE.get());
            event.register((stack, tint) -> tint > 0 ? -1 : AntidoteItem.getColor(stack), JNEItems.GRENADE_ANTIDOTE.get());
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            NetherExpClient.registerLayerDefinitions(event);
        }

        @SubscribeEvent
        public static void renderParticles(RegisterParticleProvidersEvent event) {
            NetherExpClient.registerParticles(event);
            NetherExpClient.registerModCompatParticles(event);
        }
    }
}
