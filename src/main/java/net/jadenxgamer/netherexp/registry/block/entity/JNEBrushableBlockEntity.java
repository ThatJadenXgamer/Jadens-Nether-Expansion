package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.util.math.BlockPos;

public class JNEBrushableBlockEntity extends BrushableBlockEntity {
    public JNEBrushableBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return JNEBlockEntityTypes.BRUSHABLE_BLOCK;
    }
}
