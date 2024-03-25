package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.JNEBlocks;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SoulTorchflowerBlock extends TorchflowerCropBlock {
    public SoulTorchflowerBlock(Properties settings) {
        super(settings);
    }

    //TODO: Add Soul Torchflower
    @Override
    public @NotNull BlockState getStateForAge(int age) {
        return age == 2 ? JNEBlocks.SOUL_SLATE.get().defaultBlockState() : super.getStateForAge(age);
    }
}
