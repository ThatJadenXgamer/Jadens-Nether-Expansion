package net.jadenxgamer.netherexp.fabric;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.fabric.client.ShotgunTemperatureOverlayFabric;
import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.block.entity.client.JNEBrushableBlockRenderer;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.client.*;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.custom.AntidoteItem;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.particle.custom.*;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class NetherExpFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetherExpClient.init();
        HudRenderCallback.EVENT.register(new ShotgunTemperatureOverlayFabric());
        
        // BLOCK OPACITY
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.NETHERITE_GRATE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.RUSTY_NETHERITE_GRATE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WARPED_WART.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_GLASS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.OCHRE_FROGMIST.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.VERDANT_FROGMIST.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.PEARLESCENT_FROGMIST.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.MAGMA_CREAM_BLOCK.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_SWIRLS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SHALE_SWIRLS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SORROWSQUASH_STEM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SORROWSQUASH_STEM_PLANT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CRIMSON_SPROUTS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WHITE_ASH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BONE_FENCE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CLARET_DOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CLARET_TRAPDOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.NETHER_WART_BEARD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WARPED_WART_BEARD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.UMBRAL_WART_BEARD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.QUARTZ_CRYSTAL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.ENIGMA_CROWN.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.ENIGMA_SHELF.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WEEPING_IVY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWISTING_IVY.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWILIGHT_IVY.get(), RenderType.cutout());
        // Todo Add twilight vines
//        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWILIGHT_VINES, RenderType.cutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWILIGHT_VINES_PLANT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.RED_SCALE_FUNGUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BLUE_SCALE_FUNGUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.VIOLET_SCALE_FUNGUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.IGNEOUS_REEDS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK_PLANT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK_DOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK_TRAPDOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SKELETON_SKULL_CANDLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_SKELETON_SKULL_CANDLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CRIMSON_SPORESHROOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WARPED_SPORESHROOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.UMBRAL_SPORESHROOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOULBLIGHT_SPORESHROOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BASALTIC_GEYSER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOULED_GEYSER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BLACKSTONIC_GEYSER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.ASHEN_GEYSER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_TORCHFLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_TORCHFLOWER_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.OSSIFIED_GARGOYLE_STATUE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TRAMPLE_GARGOYLE_STATUE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.PHASE_GARGOYLE_STATUE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.GHOUL_GARGOYLE_STATUE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BRAZIER_CHEST.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TREACHEROUS_CANDLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(),
                JNEFluids.ECTOPLASM.get(), JNEFluids.FLOWING_ECTOPLASM.get());


        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SOUL_SWIRLS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SHALE_SWIRLS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_ENIGMA_CROWN.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SMOKESTALK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_RED_SCALE_FUNGUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_BLUE_SCALE_FUNGUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SOUL_TORCHFLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_VIOLET_SCALE_FUNGUS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_CRIMSON_SPORESHROOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_WARPED_SPORESHROOM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_UMBRAL_SPORESHROOM.get(), RenderType.cutout());

        // TINTS
        ColorProviderRegistry.ITEM.register((stack, tintindex) -> tintindex > 0 ? -1 : AntidoteItem.getColor(stack), JNEItems.ANTIDOTE.get());
        ColorProviderRegistry.ITEM.register((stack, tintindex) -> tintindex > 0 ? -1 : AntidoteItem.getColor(stack), JNEItems.GRENADE_ANTIDOTE.get());

        // PARTICLES
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.ENIGMA_KERNEL.get(), EnigmaKernelParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FIRE_SPARK.get(), FireSparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.TREACHEROUS_FLAME.get(), FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.GOLD_GLIMMER.get(), GlimmerParticle.NormalFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.REDSTONE_SPARK.get(), GlimmerParticle.NormalFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.REDSTONE_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.REDSTONE_EXPLOSION_EMITTER.get(), (new RedstoneExplosionEmitterParticle.Factory()));
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FALLING_NETHER_WART.get(), FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FALLING_WARPED_WART.get(), FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FALLING_SHROOMLIGHT.get(), FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FALLING_SHROOMNIGHT.get(), FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.RISING_SHROOMNIGHT.get(), RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.CRIMSON_SMOG.get(), SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.WARPED_SMOG.get(), SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.BLACK_SMOKE.get(), SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.WHITE_SMOKE.get(), SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.RED_SMOKE.get(), SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.SOUL_EMBER.get(), SmallRisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.ECTORAYS.get(), EctoraysParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.ECTOPLASMA.get(), EctoplasmaParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.BLACK_AEROSOL.get(), AerosolParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.SWIRL_POP.get(), RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.GRASP_MIST.get(), GraspMistParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.WISP.get(), GlimmerParticle.LongFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.COLORED_WISP.get(), GlimmerParticle.ColoredFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.MAGMA_CREAM.get(), RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.IMMUNITY_EFFECT.get(), SpellParticle.MobProvider::new);

        // MOD COMPAT
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FALLING_SHROOMBLIGHT.get(), FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.UMBRAL_SMOG.get(), SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.SHALE_SWIRL_POP.get(), RisingParticle.Factory::new);

        // ENTITY
        EntityRendererRegistry.register(JNEEntityType.APPARITION.get(), ApparitionRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.WISP.get(), WispRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.VESSEL.get(), VesselRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.ECTO_SLAB.get(), EctoSlabRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.STAMPEDE.get(), StampedeRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.SOUL_BULLET.get(), SoulBulletRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.BLOOD_DROP.get(), BloodDropRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.PHASMO_ARROW.get(), PhasmoArrowRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.MIST_CHARGE.get(), MistChargeRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.GRAVE_CLOUD.get(), NoopRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.ANTIDOTE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.GRENADE_EFFECT_CLOUD.get(), NoopRenderer::new);
        BlockEntityRendererRegistry.register(JNEBlockEntityType.BRUSHABLE_BLOCK.get(), JNEBrushableBlockRenderer::new);

        // LAYERS
        EntityModelLayerRegistry.registerModelLayer(JNEModelLayers.APPARITION_LAYER, ApparitionModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(JNEModelLayers.WISP_LAYER, WispModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(JNEModelLayers.VESSEL_LAYER, VesselModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(JNEModelLayers.ECTO_SLAB_LAYER, EctoSlabModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(JNEModelLayers.STAMPEDE_LAYER, StampedeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(JNEModelLayers.MIST_CHARGE_LAYER, MistChargeModel::createBodyLayer);
    }
}
