package net.jadenxgamer.netherexp.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jadenxgamer.netherexp.client.rendering.extensions.JNEFluidExtensions;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.spongepowered.asm.mixin.Mixin;

import static net.jadenxgamer.netherexp.config.JNEConfigs.LAVA_PERLIN_NOISE_GRADIENT;

@Mixin(IClientFluidTypeExtensions.class)
public interface IClientFluidTypeExtensionsMixin {

    @WrapMethod(
            method = "getTintColor(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I"
    )
    private int netherexp$getLavaColor(FluidState state, BlockAndTintGetter getter, BlockPos pos, Operation<Integer> original) {
        if (JNEConfigImpl.COMMON.isLoaded() && LAVA_PERLIN_NOISE_GRADIENT.get() && state.getType().isSame(Fluids.LAVA)) {
            return JNEFluidExtensions.lavaExt.getTintColor(state, getter, pos);
        }
        else return original.call(state, getter, pos);
    }
}
