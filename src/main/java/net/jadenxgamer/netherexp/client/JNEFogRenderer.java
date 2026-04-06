package net.jadenxgamer.netherexp.client;

import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.event.ViewportEvent;

public class JNEFogRenderer {

    public static void fogRender(ViewportEvent.RenderFog event) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        if (event.isCanceled() || event.getType() != FogType.NONE || player == null) return;
        BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
        if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            event.setCanceled(true);
            event.setNearPlaneDistance(2.0F);
            event.setFarPlaneDistance(6.0F);
            return;
        }
    }

    public static void fogColor(ViewportEvent.ComputeFogColor event) {
        Entity player = Minecraft.getInstance().player;
        if (player == null) return;
        BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
        if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            event.setRed(0.106f);
            event.setGreen(0.278f);
            event.setBlue(0.271f);
        }
    }
}
