package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.misc_registry.JNESoundType;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockSoundsMixin extends BlockBehaviour {

    public BlockSoundsMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "getSoundType",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$changeSoundGroup(BlockState blockState, CallbackInfoReturnable<SoundType> cir) {
        if (blockState.is(JNETags.Blocks.SOUNDS_BLACKSTONE)) {
            cir.setReturnValue(JNESoundType.BLACKSTONE);
        }
        else if (blockState.is(JNETags.Blocks.SOUNDS_POLISHED_BLACKSTONE)) {
            cir.setReturnValue(JNESoundType.POLISHED_BLACKSTONE_BRICKS);
        }
        else if (blockState.is(JNETags.Blocks.SOUNDS_POLISHED_BLACKSTONE_BRICKS)) {
            cir.setReturnValue(JNESoundType.POLISHED_BLACKSTONE_BRICKS);
        }
        else if (blockState.is(JNETags.Blocks.SOUNDS_MAGMA_BLOCK)) {
            cir.setReturnValue(JNESoundType.MAGMA_BLOCK);
        }
        else if (blockState.is(JNETags.Blocks.SOUNDS_GLOWSTONE)) {
            cir.setReturnValue(JNESoundType.GLOWSTONE);
        }
        else if (blockState.is(JNETags.Blocks.SOUNDS_QUARTZ_BLOCK)) {
            cir.setReturnValue(JNESoundType.QUARTZ_BLOCK);
        }
    }
}
