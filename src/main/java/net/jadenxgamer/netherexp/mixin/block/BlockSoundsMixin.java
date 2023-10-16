package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.jadenxgamer.netherexp.registry.sound.ModSoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockSoundsMixin extends AbstractBlock {
    public BlockSoundsMixin(Settings settings) {
        super(settings);
    }

    @Inject(
            method = "getSoundGroup",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void changeSoundGroup(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
        if (state.isIn(ModTags.Blocks.SOUNDS_BLACKSTONE)) {
            cir.setReturnValue(ModSoundEvents.BLACKSTONE);
        }
        if (state.isIn(ModTags.Blocks.SOUNDS_POLISHED_BLACKSTONE)) {
            cir.setReturnValue(ModSoundEvents.POLISHED_BLACKSTONE_BRICKS);
        }
        if (state.isIn(ModTags.Blocks.SOUNDS_POLISHED_BLACKSTONE_BRICKS)) {
            cir.setReturnValue(ModSoundEvents.POLISHED_BLACKSTONE_BRICKS);
        }
        if (state.isIn(ModTags.Blocks.SOUNDS_MAGMA_BLOCK)) {
            cir.setReturnValue(ModSoundEvents.MAGMA_BLOCK);
        }
    }
}
