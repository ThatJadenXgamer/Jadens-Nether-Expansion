package net.jadenxgamer.netherexp.mixin.renderer;

import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {

    @Shadow
    protected ClientLevel level;

    @Inject(
            method = "crack",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$crack(BlockPos pos, Direction direction, CallbackInfo cir) {
        // for some reason the game does not like it when a model render block without a collision is broken
        // because it tries to crack it for some reason??? even though frogmist is insta-mineable
        BlockState state = level.getBlockState(pos);
        if (state.is(JNETags.Blocks.FROGMIST)) {
            cir.cancel();
        }
    }
}
