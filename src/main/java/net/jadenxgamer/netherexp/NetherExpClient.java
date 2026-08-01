package net.jadenxgamer.netherexp;

import net.jadenxgamer.netherexp.client.particle.*;
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
import net.jadenxgamer.netherexp.core.entity.Carcass;
import net.jadenxgamer.netherexp.core.entity.Stampede;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SplashParticle;
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
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

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
        EntityRenderers.register(JNEEntityType.ECTO_SLAB.get(), EctoSlabRenderer::new);
        EntityRenderers.register(JNEEntityType.BANSHEE.get(), BansheeRenderer::new);
        EntityRenderers.register(JNEEntityType.STAMPEDE.get(), StampedeRenderer::new);
        EntityRenderers.register(JNEEntityType.CARCASS.get(), CarcassRenderer::new);
        EntityRenderers.register(JNEEntityType.PHASMO_ARROW.get(), PhasmoArrowRenderer::new);
        EntityRenderers.register(JNEEntityType.SHOTGUN_PELLET.get(), ShotgunPelletRenderer::new);
        EntityRenderers.register(JNEEntityType.SLUG_PELLET.get(), SlugPelletRenderer::new);
        EntityRenderers.register(JNEEntityType.PHASMO_PELLET.get(), PhasmoPelletRenderer::new);
        EntityRenderers.register(JNEEntityType.WILL_O_WISP.get(), WillOWispRenderer::new);
        EntityRenderers.register(JNEEntityType.PORTAL_GLOW.get(), PortalGlowRenderer::new);
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
        event.registerLayerDefinition(EctoSlabRenderer.EctoSlabModel.LAYER, EctoSlabRenderer.EctoSlabModel::createBodyLayer);
        event.registerLayerDefinition(BansheeRenderer.BansheeModel.LAYER, BansheeRenderer.BansheeModel::createBodyLayer);
        event.registerLayerDefinition(StampedeRenderer.StampedeModel.LAYER, StampedeRenderer.StampedeModel::createBodyLayer);
        event.registerLayerDefinition(StampedeRenderer.StampedeModel.SADDLE_LAYER, StampedeRenderer.StampedeModel::createBodyLayer);
        event.registerLayerDefinition(CarcassRenderer.CarcassModel.LAYER, CarcassRenderer.CarcassModel::createBodyLayer);
        event.registerLayerDefinition(WillOWispRenderer.WillOWispModel.LAYER, WillOWispRenderer.WillOWispModel::createBodyLayer);
        event.registerLayerDefinition(WillOWispRenderer.WillOWispItemModel.LAYER, WillOWispRenderer.WillOWispItemModel::createOrbLayer);
        event.registerLayerDefinition(WillOWispRenderer.WillOWispItemModel.LAYER_HAND, WillOWispRenderer.WillOWispItemModel::createHandLayer);
        event.registerLayerDefinition(ShotgunFistModel.LAYER, ShotgunFistModel::createBodyLayer);
        event.registerLayerDefinition(PumpChargeShotgunModel.LAYER, PumpChargeShotgunModel::createBodyLayer);
        event.registerLayerDefinition(PelletRenderer.ShotgunPelletModel.LAYER, PelletRenderer.ShotgunPelletModel::createBodyLayer);
        event.registerLayerDefinition(PortalGlowRenderer.PortalGlowModel.LAYER, PortalGlowRenderer.PortalGlowModel::createBodyLayer);
    }

    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(JNEParticleTypes.SOUL_SWIRL_POP.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.WISP.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
        event.registerSpriteSet(JNEParticleTypes.IMMUNITY_EFFECT.get(), SpellParticle.MobEffectProvider::new);
        event.registerSpriteSet(JNEParticleTypes.SOUL_MAGMA.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.CRIMSON_SMOG.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.WARPED_SMOG.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.BLACK_SMOKE.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.WHITE_SMOKE.get(), SmogParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.BLACK_FLAKE.get(), BlackFlakeParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.DRIPPING_ECTOPLASM.get(), JNEDripHangParticle.EctoplasmProvider::new);
        event.registerSpriteSet(JNEParticleTypes.FALLING_ECTOPLASM.get(), JNEFallAndLandParticle.EctoplasmProvider::new);
        event.registerSpriteSet(JNEParticleTypes.ECTOSPLASH.get(), SplashParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.ECTOPLASM_RAYS.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.WIND_TRAIL.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.GLOWING_DOT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.GLOWING_DOT_COIL.get(), CoilParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SOUL_CLOUD.get(), JNEPoofParticle.SoulProvider::new);
        event.registerSpriteSet(JNEParticleTypes.SILVER_GLIMMER.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.TREACHEROUS_FLAME.get(), FlameParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.POSSESSION.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SHOTGUN_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.NETHER_FOG.get(), ProximityFadeParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.LIGHTSPORE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.NIGHTSPORE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.WINDY_ASH.get(), WindBlownParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.DRIFTING_SOUL.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.WILL_O_WISP_IMPACT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.REDUX_DUST_BLOB.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.REDUX_DUST_STAR.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.REDUX_POOF.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.REDUX_POOF_BLOB.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.REDUX_POOF_STAR.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.GENERIC_GLOW.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SPARKLE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SHOTGUN_FLASH.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.PUMP_SHOTGUN_FLASH.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.PELLET_HIT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.RED_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        event.registerSpecial(JNEParticleTypes.RED_EXPLOSION_EMITTER.get(), (new RedExplosionEmitterParticle.Factory()));
        event.registerSpriteSet(JNEParticleTypes.JNE_PORTAL.get(), JNEPortalParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.PORTAL_MIST.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.ECTO_SHARD.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.LARGE_BURST.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.BURN_DROPLET.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.BURN_SIDE.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.BURN_DROPLET_TINT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.BURN_SIDE_TINT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.RED_HAZE.get(), RedHazeParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.RED_SPARKLE.get(), FallingParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.CANDLE_BURST.get(), BurstParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.BLOT_DROP.get(), LodestoneDrippingParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.GLACIER_EFFECT.get(), GlacierEffectParticle.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.BLACK_FLAKE_WORLD.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.COLD_FOG.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.BLOT_DROP.get(), LodestoneWorldParticleType.Factory::new);
    }

    public static class HandlePostShaders {

        public static void tick(Minecraft client) {
            Entity player = client.getCameraEntity();
            if (player == null) return;
            BlockState state = player.level().getBlockState(client.gameRenderer.getMainCamera().getBlockPosition());
            var biome = player.level().getBiome(client.gameRenderer.getMainCamera().getBlockPosition());

            // Implementations
            SoulGlassPostProcessor.INSTANCE.setActive(JNEConfigs.ENABLE_SOUL_GLASS_SCREEN_FILTER.get() && state.is(JNETags.Blocks.SOUL_GLASSES));
            SoulGlassPostProcessor.tick(state);
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
            isInsideSoulGlass = state.is(JNETags.Blocks.SOUL_GLASSES);

            if (isInsideSoulGlass && !wasInsideSoulGlass) {
                level.playLocalSound(player, JNESoundEvents.SOUL_GLASS_ENTER.get(), SoundSource.AMBIENT, 1.0f, 1.0f);
                client.getSoundManager().play(new InsideFluidAmbientSoundInstance(player, JNESoundEvents.SOUL_GLASS_SUBMERGED.get(), 0.8f, p -> isInsideSoulGlass));
            } else if (!isInsideSoulGlass && wasInsideSoulGlass) level.playLocalSound(player, JNESoundEvents.SOUL_GLASS_EXIT.get(), SoundSource.AMBIENT, 1.0f, 1.0f);

            wasInsideSoulGlass = isInsideSoulGlass;
        }
    }
}