package net.jadenxgamer.netherexp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.entity.ModEntities;
import net.jadenxgamer.netherexp.registry.entity.client.*;
import net.jadenxgamer.netherexp.registry.fluid.ModFluids;
import net.jadenxgamer.netherexp.registry.particle.ModParticles;
import net.jadenxgamer.netherexp.registry.particle.custom.*;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.util.Identifier;

public class NetherExpClient implements ClientModInitializer {

    // FOG RENDERERS
    public static boolean INSIDE_SOUL_GLASS = false;
    public static boolean INSIDE_ECTOPLASM = false;

    @Override
    public void onInitializeClient() {
        // BLOCK OPACITY
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WARPED_WART, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGMA_CREAM_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHALE_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SORROWSQUASH_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SORROWSQUASH_STEM_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHAINWIRE_FENCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRIMSON_SPROUTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_ASH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PYRITE_CHAIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BONE_FENCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLARET_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLARET_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NETHER_WART_BEARD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WARPED_WART_BEARD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.UMBRAL_WART_BEARD, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.QUARTZ_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPIKETRAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENIGMA_CROWN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENIGMA_SHELF, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEEPING_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TWISTING_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TWILIGHT_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TWILIGHT_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TWILIGHT_VINES_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOOMSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VIOLET_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.IGNEOUS_REEDS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.IGNEOUS_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.IGNEOUS_VINES_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMOKESTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMOKESTALK_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMOKESTALK_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMOKESTALK_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SKELETON_SKULL_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_SKELETON_SKULL_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRIMSON_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WARPED_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.UMBRAL_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BASALTIC_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOULED_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACKSTONIC_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ASHEN_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_TORCHFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_TORCHFLOWER_CROP, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SOUL_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SHALE_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_ENIGMA_CROWN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SMOKESTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_RED_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BLUE_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SOUL_TORCHFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_VIOLET_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_CRIMSON_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_WARPED_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_UMBRAL_SPORESHROOM, RenderLayer.getCutout());

        // FLUIDS
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.ECTOPLASM, ModFluids.FLOWING_ECTOPLASM,
                new SimpleFluidRenderHandler(
                        new Identifier("netherexp:block/ectoplasm_still"),
                        new Identifier("netherexp:block/ectoplasm_flow"),
                        new Identifier("netherexp:block/ectoplasm_overlay")
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.ECTOPLASM, ModFluids.FLOWING_ECTOPLASM);

        // PARTICLES
        ParticleFactoryRegistry.getInstance().register(ModParticles.ENIGMA_PARTICLE, EnigmaSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FIRE_SPARK, FireSparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SMALL_SOUL_FLAME, FlameParticle.SmallFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GOLD_GLIMMER, GlimmerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.REDSTONE_SPARK, GlimmerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.REDSTONE_EXPLOSION, ExplosionLargeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.REDSTONE_EXPLOSION_EMITTER, (new RedstoneExplosionEmitterParticle.Factory()));
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_NETHER_WART, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_WARPED_WART, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_SHROOMLIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_SHROOMNIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.RISING_SHROOMNIGHT, RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_SHROOMBLIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.CRIMSON_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.WARPED_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.UMBRAL_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLACK_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.WHITE_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.RED_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOUL_EMBER, SmallRisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ECTORAYS, EctoraysParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ECTOPLASMA, EctoplasmaParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLACK_AEROSOL, AerosolParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SWIRL_POP, RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHALE_SWIRL_POP, RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GRASP_MIST, GraspMistParticle.Factory::new);

        // ENTITY
        EntityRendererRegistry.register(ModEntities.WARPHOPPER, WarphopperRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAMPEDE, StampedeRenderer::new);
        EntityRendererRegistry.register(ModEntities.APPARITION, ApparitionRenderer::new);
        EntityRendererRegistry.register(ModEntities.WISP, WispRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRASP, GraspRenderer::new);
        EntityRendererRegistry.register(ModEntities.MIST_CHARGE, MistChargeRenderer::new);
        EntityRendererRegistry.register(ModEntities.MIST_CHARGE_CLOUD, EmptyEntityRenderer::new);
    }
}
