package net.jadenxgamer.netherexp.registry.block.custom;

import net.jadenxgamer.netherexp.registry.block.entity.JNEBrushableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class JNEBrushableBlock extends BrushableBlock {
    public JNEBrushableBlock(Block block, Properties properties, SoundEvent soundEvent, SoundEvent soundEvent2) {
        super(block, properties, soundEvent, soundEvent2);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JNEBrushableBlockEntity(pos, state);
    }
}
