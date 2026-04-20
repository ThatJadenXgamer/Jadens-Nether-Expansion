package net.jadenxgamer.netherexp;

import net.jadenxgamer.netherexp.client.rendering.block_entity.DiscernmentGlassBlockRenderer;
import net.jadenxgamer.netherexp.client.rendering.block_entity.JNECampfireRenderer;
import net.jadenxgamer.netherexp.client.rendering.block_entity.SuspiciousSoulSandBlockRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.*;
import net.jadenxgamer.netherexp.client.rendering.item.PumpChargeShotgunModel;
import net.jadenxgamer.netherexp.client.rendering.item.ShotgunFistModel;
import net.jadenxgamer.netherexp.client.shader.NetherHeatDistortionPostprocessor;
import net.jadenxgamer.netherexp.client.shader.SoulGlassPostProcessor;
import net.jadenxgamer.netherexp.client.sound.InsideFluidAmbientSoundInstance;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NetherExp.MOD_ID, dist = Dist.CLIENT)
public final class NetherExpClient {
    public static boolean shouldShowBetaPopup = false;

    public NetherExpClient(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    public static void registerRenderers() {
        EntityRenderers.register(JNEEntityType.WISP.get(), WispRenderer::new);
        EntityRenderers.register(JNEEntityType.APPARITION.get(), ApparitionRenderer::new);
        EntityRenderers.register(JNEEntityType.VESSEL.get(), VesselRenderer::new);
        EntityRenderers.register(JNEEntityType.BANSHEE.get(), BansheeRenderer::new);
        EntityRenderers.register(JNEEntityType.PHASMO_ARROW.get(), PhasmoArrowRenderer::new);
        EntityRenderers.register(JNEEntityType.SHOTGUN_PELLET.get(), ShotgunPelletRenderer::new);
        EntityRenderers.register(JNEEntityType.SLUG_PELLET.get(), SlugPelletRenderer::new);
        EntityRenderers.register(JNEEntityType.PHASMO_PELLET.get(), PhasmoPelletRenderer::new);
        EntityRenderers.register(JNEEntityType.WILL_O_WISP.get(), WillOWispRenderer::new);
        EntityRenderers.register(EntityType.BLAZE, JNEBlazeRenderer::new);
        BlockEntityRenderers.register(JNEBlockEntityType.SUSPICIOUS_SOUL_SAND.get(), SuspiciousSoulSandBlockRenderer::new);
        BlockEntityRenderers.register(JNEBlockEntityType.DISCERNMENT_GLASS.get(), DiscernmentGlassBlockRenderer::new);
        BlockEntityRenderers.register(JNEBlockEntityType.JNE_CAMPFIRE.get(), JNECampfireRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(JNEFluids.ECTOPLASM_SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(JNEFluids.ECTOPLASM_FLOWING.get(), RenderType.translucent());
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WispRenderer.WispModel.LAYER, WispRenderer.WispModel::createBodyLayer);
        event.registerLayerDefinition(ApparitionRenderer.ApparitionModel.LAYER, ApparitionRenderer.ApparitionModel::createBodyLayer);
        event.registerLayerDefinition(VesselRenderer.VesselModel.LAYER, VesselRenderer.VesselModel::createBodyLayer);
        event.registerLayerDefinition(BansheeRenderer.BansheeModel.LAYER, BansheeRenderer.BansheeModel::createBodyLayer);
        event.registerLayerDefinition(WillOWispRenderer.WillOWispModel.LAYER, WillOWispRenderer.WillOWispModel::createBodyLayer);
        event.registerLayerDefinition(WillOWispRenderer.WillOWispItemModel.LAYER, WillOWispRenderer.WillOWispItemModel::createOrbLayer);
        event.registerLayerDefinition(WillOWispRenderer.WillOWispItemModel.LAYER_HAND, WillOWispRenderer.WillOWispItemModel::createHandLayer);
        event.registerLayerDefinition(ShotgunFistModel.LAYER, ShotgunFistModel::createBodyLayer);
        event.registerLayerDefinition(PumpChargeShotgunModel.LAYER, PumpChargeShotgunModel::createBodyLayer);
        event.registerLayerDefinition(PelletRenderer.ShotgunPelletModel.LAYER, PelletRenderer.ShotgunPelletModel::createBodyLayer);
    }

    public static class HandlePostShaders {

        public static void tick(Minecraft client) {
            Entity player = client.getCameraEntity();
            if (player == null) return;
            BlockState state = player.level().getBlockState(client.gameRenderer.getMainCamera().getBlockPosition());
            var biome = player.level().getBiome(client.gameRenderer.getMainCamera().getBlockPosition());

            // Implementations
            SoulGlassPostProcessor.INSTANCE.setActive(JNEConfigs.ENABLE_SOUL_GLASS_SCREEN_FILTER.get() && state.is(JNEBlocks.SOUL_GLASS.get()));
            NetherHeatDistortionPostprocessor.INSTANCE.setActive(NetherHeatDistortionPostprocessor.shouldEnable());
            NetherHeatDistortionPostprocessor.tick(client, player.level(), player, biome);
        }
    }

    public static class SubmergedStates {
        public static boolean isInsideSoulGlass;
        public static boolean wasInsideSoulGlass;

        public static void tick() {
            var client = Minecraft.getInstance();
            Player player = client.player;
            if (!(player instanceof LocalPlayer localPlayer)) return;
            BlockPos eyePos = BlockPos.containing(player.getEyePosition());
            BlockState state = player.level().getBlockState(eyePos);

            updateSoulGlass(client, player.level(), localPlayer, state);
        }

        private static void updateSoulGlass(Minecraft client, Level level, LocalPlayer player, BlockState state) {
            isInsideSoulGlass = state.is(JNEBlocks.SOUL_GLASS.get());

            if (isInsideSoulGlass && !wasInsideSoulGlass) {
                level.playLocalSound(player, JNESoundEvents.SOUL_GLASS_ENTER.get(), SoundSource.AMBIENT, 1.0f, 1.0f);
                client.getSoundManager().play(new InsideFluidAmbientSoundInstance(player, JNESoundEvents.SOUL_GLASS_SUBMERGED.get(), 0.8f, p -> isInsideSoulGlass));
            } else if (!isInsideSoulGlass && wasInsideSoulGlass) level.playLocalSound(player, JNESoundEvents.SOUL_GLASS_EXIT.get(), SoundSource.AMBIENT, 1.0f, 1.0f);

            wasInsideSoulGlass = isInsideSoulGlass;
        }
    }
}
