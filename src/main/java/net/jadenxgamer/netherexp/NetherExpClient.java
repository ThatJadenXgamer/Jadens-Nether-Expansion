package net.jadenxgamer.netherexp;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.entity.JNEEntityType;
import net.jadenxgamer.netherexp.registry.entity.client.*;
import net.jadenxgamer.netherexp.registry.fluid.JNEFluids;
import net.jadenxgamer.netherexp.registry.item.JNEItems;
import net.jadenxgamer.netherexp.registry.item.custom.AntidoteItem;
import net.jadenxgamer.netherexp.registry.particle.JNEParticles;
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
    public static boolean INSIDE_MAGMA_CREAM_BLOCK = false;

    @Override
    public void onInitializeClient() {
        // BLOCK OPACITY
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.NETHERITE_GRATE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.RUSTY_NETHERITE_GRATE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WARPED_WART, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.MAGMA_CREAM_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SHALE_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SORROWSQUASH_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SORROWSQUASH_STEM_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CHAINWIRE_FENCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CRIMSON_SPROUTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WHITE_ASH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.PYRITE_CHAIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BONE_FENCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CLARET_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CLARET_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.NETHER_WART_BEARD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WARPED_WART_BEARD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.UMBRAL_WART_BEARD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.QUARTZ_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SPIKETRAP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.ENIGMA_CROWN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.ENIGMA_SHELF, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WEEPING_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWISTING_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWILIGHT_IVY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWILIGHT_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.TWILIGHT_VINES_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BOOMSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.RED_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BLUE_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.VIOLET_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.IGNEOUS_REEDS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.IGNEOUS_VINES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.IGNEOUS_VINES_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SMOKESTALK_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SKELETON_SKULL_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_SKELETON_SKULL_CANDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.CRIMSON_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.WARPED_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.UMBRAL_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOULBLIGHT_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BASALTIC_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOULED_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.BLACKSTONIC_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.ASHEN_GEYSER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_TORCHFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_TORCHFLOWER_CROP, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SOUL_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SHALE_SWIRLS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_ENIGMA_CROWN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SMOKESTALK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_RED_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_BLUE_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_SOUL_TORCHFLOWER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_VIOLET_SCALE_FUNGUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_CRIMSON_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_WARPED_SPORESHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.POTTED_UMBRAL_SPORESHROOM, RenderLayer.getCutout());

        // FLUIDS
        FluidRenderHandlerRegistry.INSTANCE.register(JNEFluids.ECTOPLASM, JNEFluids.FLOWING_ECTOPLASM,
                new SimpleFluidRenderHandler(
                        new Identifier("netherexp:block/ectoplasm_still"),
                        new Identifier("netherexp:block/ectoplasm_flow"),
                        new Identifier("netherexp:block/ectoplasm_overlay")
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                JNEFluids.ECTOPLASM, JNEFluids.FLOWING_ECTOPLASM);

        // COLOR PROVIDERS
        ColorProviderRegistry.ITEM.register((stack, tintindex) -> tintindex > 0 ? -1 : AntidoteItem.getColor(stack), JNEItems.ANTIDOTE);

        // PARTICLES
        ParticleFactoryRegistry.getInstance().register(JNEParticles.ENIGMA_PARTICLE, EnigmaSporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.FIRE_SPARK, FireSparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.SMALL_SOUL_FLAME, FlameParticle.SmallFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.GOLD_GLIMMER, GlimmerParticle.NormalFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.REDSTONE_SPARK, GlimmerParticle.NormalFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.REDSTONE_EXPLOSION, ExplosionLargeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.REDSTONE_EXPLOSION_EMITTER, (new RedstoneExplosionEmitterParticle.Factory()));
        ParticleFactoryRegistry.getInstance().register(JNEParticles.FALLING_NETHER_WART, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.FALLING_WARPED_WART, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.FALLING_SHROOMLIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.FALLING_SHROOMNIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.RISING_SHROOMNIGHT, RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.FALLING_SHROOMBLIGHT, FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.CRIMSON_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.WARPED_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.BLACK_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.WHITE_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.RED_SMOKE, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.SOUL_EMBER, SmallRisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.ECTORAYS, EctoraysParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.ECTOPLASMA, EctoplasmaParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.BLACK_AEROSOL, AerosolParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.SWIRL_POP, RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.SHALE_SWIRL_POP, RisingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.GRASP_MIST, GraspMistParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.WISP, GlimmerParticle.LongFactory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.MAGMA_CREAM, RisingParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(JNEParticles.UMBRAL_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.SOULBLIGHT_SMOG, SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticles.SOULBLIGHT_SPORE, SoulblightSporeFactory::new);

        // ENTITY
        EntityRendererRegistry.register(JNEEntityType.WARPHOPPER, WarphopperRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.STAMPEDE, StampedeRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.APPARITION, ApparitionRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.WISP, WispRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.GRASP, GraspRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.MIST_CHARGE, MistChargeRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.MIST_CHARGE_CLOUD, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(JNEEntityType.SOUL_BULLET, SoulBulletEntityRenderer::new);
    }
}
