package net.jadenxgamer.netherexp.mixin.block;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.jadenxgamer.netherexp.registry.misc_registry.JNETags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.TorchflowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TorchflowerBlock.class)
public abstract class TorchflowerBlockMixin extends CropBlock {
    public TorchflowerBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(JNETags.Blocks.SOUL_SAND_BLOCKS) || super.canPlantOnTop(floor, world, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        BlockState floor = world.getBlockState(pos.down());
        if (floor.isIn(JNETags.Blocks.SOUL_SAND_BLOCKS) && state.isOf(Blocks.TORCHFLOWER_CROP)) {
            world.setBlockState(pos, JNEBlocks.SOUL_TORCHFLOWER_CROP.getDefaultState(), NOTIFY_LISTENERS);
        }
    }
}
