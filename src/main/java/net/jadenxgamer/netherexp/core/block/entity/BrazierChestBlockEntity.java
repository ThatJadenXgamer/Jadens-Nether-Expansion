package net.jadenxgamer.netherexp.core.block.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.BrazierChestBlock;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BrazierChestBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;
    private int lockTimer;

    @Nullable
    protected ResourceLocation refillLootTable;

    public BrazierChestBlockEntity(BlockPos pos, BlockState state) {
        super(JNEBlockEntityType.BRAZIER_CHEST.get(), pos, state);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.lockTimer = JNEConfigs.BRAZIER_CHEST_REFILL_COOLDOWN.get() * 20;
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level arg, BlockPos arg2, BlockState arg3) {
                BrazierChestBlockEntity.this.playSound(arg3, JNESoundEvents.BRAZIER_CHEST_OPEN.get());
                BrazierChestBlockEntity.this.updateBlockState(arg3, true);
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                BrazierChestBlockEntity.this.playSound(state, JNESoundEvents.BRAZIER_CHEST_CLOSE.get());
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
        if (this.level == null || this.refillLootTable == null) return;
        this.clearContent();
        ResourceKey<LootTable> refillLootTable = ResourceKey.create(Registries.LOOT_TABLE, this.refillLootTable);
        unpackRefillLootTable(refillLootTable);
    }

    public void unpackRefillLootTable(ResourceKey<LootTable> refillLootTable) {
        Level level = this.getLevel();
        BlockPos pos = this.getBlockPos();
        if (refillLootTable != null && level != null && level.getServer() != null) {
            LootTable loottable = level.getServer().reloadableRegistries().getLootTable(refillLootTable);
            this.setLootTable(null);
            LootParams.Builder lootBuilder = new LootParams.Builder((ServerLevel)level).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos));

            loottable.fill(this, lootBuilder.create(LootContextParamSets.CHEST), this.getLootTableSeed());
        }
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
        if (!this.remove && this.level != null && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!this.remove && this.level != null && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove && this.level != null) {
            this.openersCounter.recheckOpeners(this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        nbt.putInt("LockTimer", this.lockTimer);
        if (this.refillLootTable != null) nbt.putString("RefillLootTable", this.refillLootTable.toString());
        if (!this.trySaveLootTable(nbt)) ContainerHelper.saveAllItems(nbt, this.items, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        this.lockTimer = nbt.getInt("LockTimer");
        if (nbt.contains("RefillLootTable", 8))
            this.refillLootTable = ResourceLocation.parse(nbt.getString("RefillLootTable"));
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) ContainerHelper.loadAllItems(nbt, this.items, registries);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        boolean locked = state.getValue(BrazierChestBlock.LOCKED);
        if (level != null) {
            if (this.lootTable != null) {
                this.refillLootTable = lootTable.location();
                setLootTable(null);
                this.clearContent();
            }
            if (!locked) {
                --this.lockTimer;
                if (this.lockTimer <= 0) {
                    this.clearContent();
                    level.setBlock(pos, state.setValue(BrazierChestBlock.LOCKED, true), 2);
                    level.playSound(null, this.getBlockPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                    this.lockTimer = JNEConfigs.BRAZIER_CHEST_REFILL_COOLDOWN.get() * 20;
                }
            }
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
        this.level.playSound(null, d, e, f, sound, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public void unpackLootTable(@org.jetbrains.annotations.Nullable Player player) {
    }
}
