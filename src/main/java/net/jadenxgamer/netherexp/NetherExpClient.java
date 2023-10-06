package net.jadenxgamer.netherexp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.jadenxgamer.netherexp.block.ModBlocks;
import net.jadenxgamer.netherexp.entity.ModEntities;
import net.jadenxgamer.netherexp.entity.client.WarphopperRenderer;
import net.jadenxgamer.netherexp.particle.ModParticles;
import net.jadenxgamer.netherexp.particle.custom.*;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;

public class NetherExpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // BLOCK OPACITY
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_SWIRLS, RenderLayer.getCutout());
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.QUARTZ_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPIKETRAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENIGMA_CROWN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENIGMA_SHELF, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEEPING_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TWISTING_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOOMSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_SCALE_FUNGUS, RenderLayer.getCutout());
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BASALTIC_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOULED_GEYSER, RenderLayer.getCutout());

        // PARTICLES
        ParticleFactoryRegistry.getInstance().register(ModParticles.ENIGMA_PARTICLE, EnigmaSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SMALL_SOUL_FLAME, FlameParticle.SmallFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GOLD_GLIMMER, GlimmerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.REDSTONE_SPARK, GlimmerParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_NETHER_WART, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_WARPED_WART, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_SHROOMLIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_SHROOMNIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.RISING_SHROOMNIGHT, RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.CRIMSON_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.WARPED_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLACK_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.WHITE_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.RED_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOUL_EMBER, SmallRisingParticle.Factory::new);

        // ENTITY
        EntityRendererRegistry.register(ModEntities.WARPHOPPER, WarphopperRenderer::new);
    }
}
