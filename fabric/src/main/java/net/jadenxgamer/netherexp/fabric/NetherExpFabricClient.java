package net.jadenxgamer.netherexp.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.particle.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.particle.custom.*;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.renderer.RenderType;

public class NetherExpFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetherExpClient.init();

        // RENDER LAYERS
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_CANDLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_GLASS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_SWIRLS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SHALE_SWIRLS.get(), RenderType.cutout());

        // PARTICLES
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.ENIGMA_KERNEL.get(), EnigmaKernelParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FIRE_SPARK.get(), FireSparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.SMALL_SOUL_FIRE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
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
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.MAGMA_CREAM.get(), RisingParticle.Factory::new);

        // MOD COMPAT
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.FALLING_SHROOMBLIGHT.get(), FallingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.UMBRAL_SMOG.get(), SmogParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(JNEParticleTypes.SHALE_SWIRL_POP.get(), RisingParticle.Factory::new);
    }
}
