package net.jadenxgamer.netherexp.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.fluids.FluidType;

public class JNEFogRenderer {

    public static void fogRender(ViewportEvent.RenderFog event) {
        Entity camera = event.getCamera().getEntity();
        if (event.getType() != FogType.NONE || camera == null) return;
        BlockState state = camera.level().getBlockState(event.getCamera().getBlockPosition());
        FluidType fluid = camera.getEyeInFluidType();

        if (fluid == JNEFluids.ECTOPLASM) {
            event.setCanceled(true);
            event.setFogShape(FogShape.SPHERE);
            event.setNearPlaneDistance(-0.8f);
            event.setFarPlaneDistance(8.0f);
            return;
        }
    }

    public static void fogColor(ViewportEvent.ComputeFogColor event) {
        Entity camera = event.getCamera().getEntity();
        if (camera == null) return;
        BlockState state = camera.level().getBlockState(event.getCamera().getBlockPosition());
        FluidType fluid = camera.getEyeInFluidType();
        if (fluid == JNEFluids.ECTOPLASM) {
            event.setRed(0.02f);
            event.setGreen(0.333f);
            event.setBlue(0.357f);
        }
    }
}
