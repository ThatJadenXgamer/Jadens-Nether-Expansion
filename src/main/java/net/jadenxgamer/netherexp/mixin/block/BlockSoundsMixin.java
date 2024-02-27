package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.registry.config.other.sounds.BlockSoundsConfigs;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.jadenxgamer.netherexp.registry.sound.JNESoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockSoundsMixin extends AbstractBlock {

    public BlockSoundsMixin(Settings settings) {
        super(settings);
    }

    @Unique
    private static final BlockSoundsConfigs soundsConfigs = NetherExp.getConfig().sounds.blockSoundsConfigs;

    @Inject(
            method = "getSoundGroup",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$changeSoundGroup(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
        if (soundsConfigs.blackstone_sounds && state.isIn(JNETags.Blocks.SOUNDS_BLACKSTONE)) {
            cir.setReturnValue(JNESoundEvents.BLACKSTONE);
        }
        else if (soundsConfigs.polished_blackstone_sounds && state.isIn(JNETags.Blocks.SOUNDS_POLISHED_BLACKSTONE)) {
            cir.setReturnValue(JNESoundEvents.POLISHED_BLACKSTONE_BRICKS);
        }
        else if (soundsConfigs.polished_blackstone_brick_sounds && state.isIn(JNETags.Blocks.SOUNDS_POLISHED_BLACKSTONE_BRICKS)) {
            cir.setReturnValue(JNESoundEvents.POLISHED_BLACKSTONE_BRICKS);
        }
        else if (soundsConfigs.magma_block_sounds && state.isIn(JNETags.Blocks.SOUNDS_MAGMA_BLOCK)) {
            cir.setReturnValue(JNESoundEvents.MAGMA_BLOCK);
        }
        else if (soundsConfigs.glowstone_sounds && state.isIn(JNETags.Blocks.SOUNDS_GLOWSTONE)) {
            cir.setReturnValue(JNESoundEvents.GLOWSTONE);
        }
        else if (soundsConfigs.quartz_block_sounds && state.isIn(JNETags.Blocks.SOUNDS_QUARTZ_BLOCK)) {
            cir.setReturnValue(JNESoundEvents.QUARTZ_BLOCK);
        }
    }
}
