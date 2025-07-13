package net.jadenxgamer.netherexp.core.block.entity;

import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

public class SuspiciousSoulSandBlockEntity extends BrushableBlockEntity {

    private int decayCounter = 0;

    public SuspiciousSoulSandBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    public static void setLootTable(BlockGetter level, RandomSource random, BlockPos pos, ResourceKey<LootTable> lootTable) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BrushableBlockEntity) {
            ((BrushableBlockEntity) blockEntity).setLootTable(lootTable, random.nextLong());
        }
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return JNEBlockEntityType.SUSPICIOUS_SOUL_SAND.get();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putInt("decay", getDecayCounter());
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        setDecayCounter(nbt.getInt("decay"));
    }

    public void setDecayCounter(int decayCounter) {
        this.decayCounter = decayCounter;
    }

    public int getDecayCounter() {
        return decayCounter;
    }
}
