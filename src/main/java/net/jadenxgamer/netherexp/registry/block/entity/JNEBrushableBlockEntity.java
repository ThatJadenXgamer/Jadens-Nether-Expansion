package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;

public class JNEBrushableBlockEntity extends BrushableBlockEntity {
    public JNEBrushableBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public static void setLootTable(BlockView world, Random random, BlockPos pos, Identifier id) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BrushableBlockEntity) {
            ((BrushableBlockEntity)blockEntity).setLootTable(id, random.nextLong());
        }
    }

    @Override
    public BlockEntityType<?> getType() {
        return JNEBlockEntityTypes.BRUSHABLE_BLOCK;
    }
}
