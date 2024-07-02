package net.jadenxgamer.netherexp.registry.block.entity;

import net.jadenxgamer.netherexp.registry.block.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.block.custom.BrazierChestBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrazierChestBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    @Nullable
    protected ResourceLocation refillLootTable;

    public BrazierChestBlockEntity(BlockPos pos, BlockState state) {
        super(JNEBlockEntityType.BRAZIER_CHEST.get(), pos, state);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level arg, BlockPos arg2, BlockState arg3) {
                BrazierChestBlockEntity.this.playSound(arg3, SoundEvents.CHEST_OPEN);
                BrazierChestBlockEntity.this.updateBlockState(arg3, true);
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                BrazierChestBlockEntity.this.playSound(state, SoundEvents.CHEST_CLOSE);
                BrazierChestBlockEntity.this.updateBlockState(state, false);
            }

            protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int i, int j) {
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)player.containerMenu).getContainer();
                    return container == BrazierChestBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    public void refillLoot() {
        if (this.level != null) {
            if (this.lootTable != null && refillLootTable == null) {
                this.refillLootTable = lootTable;
            }
            if (this.refillLootTable != null) {
                this.clearContent();
                setLootTable(this.level, this.level.random, this.getBlockPos(), this.refillLootTable);
            }
        }
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (!this.trySaveLootTable(nbt)) {
            ContainerHelper.saveAllItems(nbt, this.items);
        }

    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) {
            ContainerHelper.loadAllItems(nbt, this.items);}

    }

    public int getContainerSize() {
        return 27;
    }

    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> stack) {
        this.items = stack;
    }

    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.brazier_chest");
    }

    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return ChestMenu.threeRows(i, inventory, this);
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean bl) {
        assert this.level != null;
        this.level.setBlock(this.getBlockPos(), state.setValue(BrazierChestBlock.OPEN, bl), 3);
    }

    void playSound(BlockState state, SoundEvent sound) {
        Vec3i vec3i = state.getValue(BrazierChestBlock.FACING).getNormal();
        double d = (double)this.worldPosition.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.worldPosition.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.worldPosition.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        assert this.level != null;
        this.level.playSound(null, d, e, f, sound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}
