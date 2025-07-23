package net.jadenxgamer.netherexp.core.block.entity;

import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DiscernmentGlassBlockEntity extends BlockEntity {
    private static final String FILTER_ITEM_TAG = "filter_item";
    private ItemStack filterItem = ItemStack.EMPTY;

    public DiscernmentGlassBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(JNEBlockEntityType.DISCERNMENT_GLASS.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        if (!getFilterItem().isEmpty()) {
            nbt.put(FILTER_ITEM_TAG, this.getFilterItem().save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.setFilterItem(ItemStack.parse(registries, nbt.getCompound(FILTER_ITEM_TAG)).orElse(ItemStack.EMPTY));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbt = super.getUpdateTag(registries);
        if (!this.filterItem.isEmpty()) {
            nbt.put("item", this.filterItem.save(registries));
        }
        return nbt;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return JNEBlockEntityType.DISCERNMENT_GLASS.get();
    }

    public ItemStack getFilterItem() {
        return this.filterItem;
    }

    public void setFilterItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            stack = stack.copyWithCount(1);
        }
        this.filterItem = stack;
    }

    public void removeFilterItem() {
        this.filterItem = ItemStack.EMPTY;
    }
}
