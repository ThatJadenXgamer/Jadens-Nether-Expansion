package net.jadenxgamer.netherexp.client;

import net.jadenxgamer.netherexp.core.keys.JNETags;
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
        if (state.is(JNETags.Blocks.SOUL_GLASSES)) {
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
        else if (state.is(JNEBlocks.WAXEN_SOUL_GLASS.get())) {
            event.setRed(0.45098f);
            event.setGreen(0.05490f);
            event.setBlue(0.05490f);
        }
    }

    // TODO: make this into an event with Elysium later, it's kind of insane how there isn't one already LMFAO
    public static void skyColor(Minecraft client, Vec3 ignoredPos, float ignoredPartialTick, CallbackInfoReturnable<Vec3> cir) {
        Entity player = client.player;
        if (player == null) return;
        BlockState state = player.level().getBlockState(client.gameRenderer.getMainCamera().getBlockPosition());

        if (state.is(JNETags.Blocks.SOUL_GLASSES)) cir.setReturnValue(new Vec3(0.0, 0.0, 0.0));
    }
}