package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchflowerBlock;

public class SoulTorchflowerBlock extends TorchflowerBlock {
    public SoulTorchflowerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState withAge(int age) {
        return age == 2 ? ModBlocks.SOUL_TORCHFLOWER.getDefaultState() : super.withAge(age);
    }
}
