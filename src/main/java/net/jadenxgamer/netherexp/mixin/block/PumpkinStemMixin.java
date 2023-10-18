package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StemBlock.class)
public abstract class PumpkinStemMixin extends PlantBlock {
    public PumpkinStemMixin(Settings settings) {
        super(settings);
    }

    @Inject(
            method = "canPlantOnTop",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void netherexp$canPlantOnSoul(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (floor.isIn(ModTags.Blocks.SORROWSQUASH_VINE_PLANTABLE_ON)) {
            cir.setReturnValue(true);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        BlockState floor = world.getBlockState(pos.down());
        if (floor.isIn(ModTags.Blocks.SORROWSQUASH_VINE_PLANTABLE_ON) && state.isOf(Blocks.PUMPKIN_STEM)) {
            world.setBlockState(pos, ModBlocks.SORROWSQUASH_STEM.getDefaultState(), NOTIFY_LISTENERS);
        }
    }
}
