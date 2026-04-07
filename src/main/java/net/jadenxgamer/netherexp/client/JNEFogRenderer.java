package net.jadenxgamer.netherexp.client;

import net.jadenxgamer.netherexp.client.shader.SoulGlassPostProcessor;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class JNEFogRenderer {

    public static void fogRender(ViewportEvent.RenderFog event) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        if (event.isCanceled() || event.getType() != FogType.NONE || player == null) return;
        BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
        SoulGlassPostProcessor.INSTANCE.setActive(state.is(JNEBlocks.SOUL_GLASS.get()));
        if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            event.setCanceled(true);
            event.setNearPlaneDistance(-8.0F);
            event.setFarPlaneDistance(event.getFarPlaneDistance() * 0.77F);
        }
    }

    public static void fogColor(ViewportEvent.ComputeFogColor event) {
        Entity player = Minecraft.getInstance().player;
        if (player == null) return;
        BlockState state = player.level().getBlockState(event.getCamera().getBlockPosition());
        if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            event.setRed(0.406f);
            event.setGreen(0.578f);
            event.setBlue(0.571f);
        }
    }

    public static void skyColor(Vec3 pos, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        var client = Minecraft.getInstance();
        Entity player = client.player;
        if (player == null) return;
        BlockState state = player.level().getBlockState(client.gameRenderer.getMainCamera().getBlockPosition());
        if (state.is(JNEBlocks.SOUL_GLASS.get())) {
            cir.setReturnValue(new Vec3(0.0, 0.0, 0.0));
        }
    }
}
