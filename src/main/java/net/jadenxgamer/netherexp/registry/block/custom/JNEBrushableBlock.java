package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrushableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class JNEBrushableBlock extends BrushableBlock {
    public JNEBrushableBlock(Block baseBlock, Settings settings, SoundEvent brushingSound, SoundEvent brushingCompleteSound) {
        super(baseBlock, settings, brushingSound, brushingCompleteSound);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new JNEBrushableBlockEntity(pos, state);
    }
}
