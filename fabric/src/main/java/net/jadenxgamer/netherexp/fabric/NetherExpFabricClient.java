package net.jadenxgamer.netherexp.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.client.renderer.RenderType;

public class NetherExpFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetherExpClient.init();
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_CANDLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_GLASS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SOUL_SWIRLS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(JNEBlocks.SHALE_SWIRLS.get(), RenderType.cutout());
    }
}
