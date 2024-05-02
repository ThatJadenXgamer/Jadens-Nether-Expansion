package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class JNEBrushableBlockEntity extends BrushableBlockEntity {
    public JNEBrushableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    public static void setLootTable(BlockGetter level, RandomSource random, BlockPos pos, ResourceLocation id) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BrushableBlockEntity) {
            ((BrushableBlockEntity)blockEntity).setLootTable(id, random.nextLong());
        }
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return JNEBlockEntityType.BRUSHABLE_BLOCK.get();
    }
}
