package net.jadenxgamer.netherexp;

import net.jadenxgamer.netherexp.client.particle.*;
import net.jadenxgamer.netherexp.client.rendering.block_entity.DiscernmentGlassBlockRenderer;
import net.jadenxgamer.netherexp.client.rendering.block_entity.JNECampfireRenderer;
import net.jadenxgamer.netherexp.client.rendering.block_entity.SuspiciousSoulSandBlockRenderer;
import net.jadenxgamer.netherexp.client.rendering.entity.*;
import net.jadenxgamer.netherexp.client.rendering.item.ShotgunFistModel;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
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
        event.registerSpriteSet(JNEParticleTypes.GLOWING_DOT_COIL.get(), CoilParticleEffect.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SOUL_CLOUD.get(), JNEPoofParticle.SoulProvider::new);
        event.registerSpriteSet(JNEParticleTypes.SILVER_GLIMMER.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.TREACHEROUS_FLAME.get(), FlameParticle.Provider::new);
        event.registerSpriteSet(JNEParticleTypes.POSSESSION.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.SHOTGUN_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(JNEParticleTypes.NETHER_FOG.get(), LodestoneWorldParticleType.Factory::new);
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
        event.registerSpriteSet(JNEParticleTypes.PELLET_HIT.get(), LodestoneWorldParticleType.Factory::new);
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
        event.registerLayerDefinition(PelletRenderer.ShotgunPelletModel.LAYER, PelletRenderer.ShotgunPelletModel::createBodyLayer);
    }
}
